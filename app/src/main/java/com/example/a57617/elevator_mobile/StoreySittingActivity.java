package com.example.a57617.elevator_mobile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.a57617.elevator_mobile.widget.WheelView;

import java.util.ArrayList;

public class StoreySittingActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener{

    private EditText et_starting_time, et_ending_time;
    private LinearLayout ll_storey_choose;
    private TextView tv_storey;

    private String time_status;//区分当前的时间选择器是开始时间还是结束时间
    private ArrayList<String> storeys = new ArrayList<>();
    private String selectText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storey_sitting);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("楼 层 设 置");
        findViewById(R.id.btn_return).setOnClickListener(this);
        et_starting_time = findViewById(R.id.et_starting_time);
        et_starting_time.setOnClickListener(this);
        et_ending_time = findViewById(R.id.et_ending_time);
        et_ending_time.setOnClickListener(this);
        ll_storey_choose = findViewById(R.id.ll_storey_choose);
        ll_storey_choose.setOnClickListener(this);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setVisibility(View.VISIBLE);
        btn_confirm.setOnClickListener(this);
        tv_storey = findViewById(R.id.tv_storey);
        for (int i = -20; i < 0; i++ ){
            storeys.add(String.format("%d楼",i));
        }
        for (int i = 1; i < 100; i++) {
            storeys.add(String.format("%d楼",i));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.et_starting_time) {
            time_status = "starting";
            // 构建一个时间对话框，该对话框已经集成了时间选择器
            // TimePickerDialog的第二个构造参数指定了时间选择器
            TimePickerDialog dialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,this,
                    0,0,true);// true表示24小时制，false表示12小时制
            // 把时间对话框显示在界面上
            dialog.show();
        } else if (v.getId() == R.id.et_ending_time) {
            time_status = "ending";
            // 构建一个时间对话框，该对话框已经集成了时间选择器
            // TimePickerDialog的第二个构造参数指定了时间选择器
            TimePickerDialog dialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,this,
                    0,0,true);// true表示24小时制，false表示12小时制
            // 把时间对话框显示在界面上
            dialog.show();
        } else if (v.getId() == R.id.btn_return) {
            finish();
        } else if (v.getId() == R.id.ll_storey_choose) {
            showDialog(tv_storey, storeys, 20);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int hminute) {
        String time = "";
        if (hourOfDay < 10 && hminute < 10) {
            time = "0" + String.valueOf(hourOfDay) + ":0" + String.valueOf(hminute);
        } else if (hourOfDay >= 10 && hminute < 10) {
            time = String.valueOf(hourOfDay) + ":0" + String.valueOf(hminute);
        } else if (hourOfDay < 10 && hminute >= 10) {
            time = "0" + String.valueOf(hourOfDay) + ":" + String.valueOf(hminute);
        } else if (hourOfDay >= 10 && hminute >= 0) {
            time = String.valueOf(hourOfDay) + ":" + String.valueOf(hminute);
        }
        if (time_status.equals("starting")) {
            et_starting_time.setText(time);
        } else if (time_status.equals("ending")) {
            et_ending_time.setText(time);
        }
    }

    private void showDialog(TextView textView, ArrayList<String> list, int selected){
        showChoiceDialog(list, textView, selected,
                new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        selectText = item;
                    }
                });
    }

    @SuppressLint("ResourceAsColor")
    private void showChoiceDialog(ArrayList<String> dataList, final TextView textView, int selected,
                                  WheelView.OnWheelViewListener listener) {
        selectText = "";
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_wheelview, null);
        final WheelView wheelView = outerView.findViewById(R.id.wheel_view);
        wheelView.setOffset(2);// 对话框中当前项上面和下面的项数
        wheelView.setItems(dataList);// 设置数据源
        wheelView.setSeletion(selected);// 默认选中第三项
        wheelView.setOnWheelViewListener(listener);

        // 显示对话框，点击确认后将所选项的值显示到Button上
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(outerView)
                .setPositiveButton("确认",
                        (dialogInterface, i) -> {
                            textView.setText(selectText);
                            textView.setTextColor(this.getResources().getColor(R.color.color_333333));
                        })
                .setNegativeButton("取消", null).create();
        alertDialog.show();
        int green = this.getResources().getColor(R.color.green);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.title);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.title);
    }
}
