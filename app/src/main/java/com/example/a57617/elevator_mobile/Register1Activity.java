package com.example.a57617.elevator_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a57617.elevator_mobile.bean.User;
import com.example.a57617.elevator_mobile.utils.DBUtil;
import com.mob.MobSDK;

import java.io.IOException;
import java.sql.SQLException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register1Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_return;
    private Button btn_send_code, btn_register;
    private EditText et_phone_number, et_password, et_code;

    private String phoneNum = "";
    private String pwd = "";

    private AlertDialog.Builder builder1;
    private AlertDialog alertDialog;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        //初始化MobSDK
        MobSDK.init(this);
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);

        btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);
        btn_send_code = findViewById(R.id.btn_send_code);
        btn_send_code.setOnClickListener(this);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_password = findViewById(R.id.et_password);
        et_code = findViewById(R.id.et_code);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_return) {
            Register1Activity.this.finish();
        } else if (v.getId() == R.id.btn_send_code) {
            phoneNum = et_phone_number.getText().toString().trim();
            if (phoneNum.length() < 11) { // 判断是否为11位手机号
                showToast("请输入11位手机号！！！");
                return;
            } else {
                //发送验证码
                SMSSDK.getVerificationCode("86",phoneNum);
            }
        } else if (v.getId() == R.id.btn_register) { // 判断是否不少于6位密码
            pwd = et_password.getText().toString().trim();
            if (pwd.length() < 6) {
                showToast("请输入不少于6位密码！！！");
                return;
            }
            String code = et_code.getText().toString().trim();
            if (code.length() < 4) { // 判断是否为4位验证码
                showToast("请输入4位验证码！！！");
                return;
            }
            SMSSDK.submitVerificationCode("86", phoneNum, code);// 提交国家、手机号、验证码进行验证
        }
    }

    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(final Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Toast.makeText(Register1Activity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                            //30s倒计时
                            CountDownTimer timer = new CountDownTimer(30000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    btn_send_code.setEnabled(false);
                                    btn_send_code.setText("已发送(" + millisUntilFinished / 1000 + ")");
                                }

                                @Override
                                public void onFinish() {
                                    btn_send_code.setEnabled(true);
                                    btn_send_code.setText("重新获取");
                                }
                            };
                            timer.start();
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        } else {
                            Toast.makeText(Register1Activity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            System.out.println("验证成功！！！");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Message message = handler.obtainMessage();
                                    User user = new User(phoneNum, pwd);
                                    try {
                                        DBUtil.insertUser(user);
                                        message.what = 1;
                                    } catch (SQLException e) {
                                        message.what = 2;
                                        e.printStackTrace();
                                    }
                                    handler.sendMessage(message);
                                }
                            }).start();
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();

                                    String result = null;
                                    try{
                                        String url = "http://meipig.320.io:11260/loginRegister/loginPassword";
                                        result = post(url,"");
                                        System.out.println("访问okhttp注册结果为：" + result);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                            builder1 = new AlertDialog.Builder(Register1Activity.this);
                            builder1.setCancelable(false);
                            builder1.setTitle("提示");
                            builder1.setMessage("正在响应中...");
                            builder1.setIcon(R.drawable.icon_loading);
                            alertDialog = builder1.show();
                            // TODO 处理验证码验证通过的结果
                        } else {
                            Toast.makeText(Register1Activity.this, "验证失败", Toast.LENGTH_SHORT).show();
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    alertDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register1Activity.this);
                    builder.setTitle("提示");
                    builder.setMessage("注册成功！");
                    builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(Register1Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            // 设置退出程序的标志位 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(intent); } });
                        }
                    });
                    builder.create().show();
                    break;
                case 2:
                    alertDialog.dismiss();
                    showToast("网络连接失败！！！");
                    break;
            }
        }
    };

    private String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
