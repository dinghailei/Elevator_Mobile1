package com.example.a57617.elevator_mobile;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a57617.elevator_mobile.adapter.RobotPagerAdapter;

import java.util.ArrayList;

public class Robot1Activity extends AppCompatActivity implements View.OnClickListener
{

    private ViewPager vp_content;
    private Button btn_input_voice, btn_send;
    private ImageButton btn_input;
    private EditText et_input_text;

    private ArrayList<String> mTitleArray = new ArrayList<String>();
    //代表现在输入方式，0为语音输入，1为文本输入
    private int input_status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot1);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("机 器 人 小 程 序");
        findViewById(R.id.btn_return).setOnClickListener(this);

        btn_input = findViewById(R.id.btn_input);
        btn_input.setOnClickListener(this);
        btn_input_voice = findViewById(R.id.btn_input_voice);
        btn_input_voice.setOnClickListener(this);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        et_input_text = findViewById(R.id.et_input_text);

        vp_content = (ViewPager) findViewById(R.id.vp_content);
        mTitleArray.add("领用");
        mTitleArray.add("拿快递");
        mTitleArray.add("巡检");
        mTitleArray.add("访客");
        mTitleArray.add("丢垃圾");
        initTabViewPager();
    }

    private void initTabViewPager() {
        RobotPagerAdapter adapter = new RobotPagerAdapter(
                getSupportFragmentManager(), mTitleArray);
        vp_content.setAdapter(adapter);
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send) {
            String text = et_input_text.getText().toString();
            if (text.length() <= 0) {
                showToast("请至少输入一个字符！！！");
                return;
            }
        } else if (v.getId() == R.id.btn_input) {
            if (input_status == 1) {
                btn_input.setBackground(getResources().getDrawable(R.drawable.icon_input_voice));
                et_input_text.setVisibility(View.GONE);
                btn_input_voice.setVisibility(View.VISIBLE);
                btn_send.setEnabled(false);
                input_status = 0;
            } else if (input_status == 0) {
                btn_input.setBackground(getResources().getDrawable(R.drawable.icon_keyboard));
                et_input_text.setVisibility(View.VISIBLE);
                btn_input_voice.setVisibility(View.GONE);
                btn_send.setEnabled(true);
                input_status = 1;
            }
        } else if (v.getId() == R.id.btn_return) {
            finish();
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
