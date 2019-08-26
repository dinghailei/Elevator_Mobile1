package com.example.a57617.elevator_mobile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a57617.elevator_mobile.adapter.NewsAdapter;
import com.example.a57617.elevator_mobile.bean.News;
import com.example.a57617.elevator_mobile.utils.DBUtil;
import com.example.a57617.elevator_mobile.utils.NewsApiUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register, btn_forget;//注册、忘记密码按钮
    private ImageButton btn_login;//登录按钮
    private EditText et_userId, et_password;//账户密码输入框

    //正在加载的弹出框
    private AlertDialog.Builder builder1;
    private AlertDialog alertDialog;

    private static final int GET_NEWS = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_forget = findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        et_userId = findViewById(R.id.et_userId);
        et_password = findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register) {
            Intent intent = new Intent(this,Register1Activity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_forget) {
            Intent intent = new Intent(this,Forget1Activity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_login) {
            final String userId = et_userId.getText().toString().trim();
            if (userId.length() < 11) { // 判断手机号是否为11位
                showToast("请输入11位手机号！！！");
                return;
            }
            final String password = et_password.getText().toString().trim();
            if (password.length() < 6) { // 判断密码是否不少于6位
                showToast("请输入不少于6位密码！！！");
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = handler.obtainMessage();
                    try {
                        //账号密码的匹配判定，1为成功、2为账号密码不匹配、3为手机号不存在、4为网络连接异常
                        int result = DBUtil.login(userId,password);
                        switch (result) {
                            case 1:
                                message.what = 1;
                                break;
                            case 2:
                                message.what = 2;
                                break;
                            case 3:
                                message.what = 3;
                                break;
                        }
                    } catch (SQLException e) {
                        message.what = 4;
                        e.printStackTrace();
                    }
                    handler.sendMessage(message);
                }
            }).start();
            //正在响应弹窗
            builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setCancelable(false);
            builder1.setTitle("提示");
            builder1.setMessage("正在响应中...");
            builder1.setIcon(R.drawable.icon_loading);
            alertDialog = builder1.show();
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    NewsApiUtil newsApiUtil = new NewsApiUtil();
                    newsApiUtil.getNews(handler);
                    break;
                case 2:
                    alertDialog.dismiss();
                    showToast("手机号或密码错误！！！");
                    break;
                case 3:
                    alertDialog.dismiss();
                    showToast("手机号不存在！！！");
                    break;
                case 4:
                    alertDialog.dismiss();
                    showToast("网络连接失败！！！");
                    break;
                case GET_NEWS:
                    alertDialog.dismiss();
                    ArrayList<News> newsArrayList = (ArrayList<News>) msg.obj;
                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news",newsArrayList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
