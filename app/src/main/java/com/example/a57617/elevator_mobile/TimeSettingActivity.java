package com.example.a57617.elevator_mobile;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a57617.elevator_mobile.adapter.StoreySittingAdapter;
import com.example.a57617.elevator_mobile.bean.StoreySitting;

import java.util.ArrayList;

public class TimeSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_storey_setting;
    private Button btn_edit, btn_cancel;

    private ArrayList<StoreySitting> storeySittingArrayList = new ArrayList<StoreySitting>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_setting);
        lv_storey_setting = findViewById(R.id.lv_storey_setting);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setVisibility(View.VISIBLE);
        btn_edit.setOnClickListener(this);
        btn_cancel = findViewById(R.id.btn_cancel);
        ImageButton btn_return = findViewById(R.id.btn_return);
        btn_return.setVisibility(View.GONE);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("楼 层 设 置");

        //demo
        String times[] = {"08:00", "09:00", "10:00", "11:00", "12:00"};
        String destinations[] = {"5楼", "7楼", "1楼", "5楼", "-1楼"};
        String dates[] = {"每天", "周一", "周三", "工作日", "周末"};
        Boolean available[] = {true, false, true, false, true};

        for (int i = 0; i <= 4; i++) {
            StoreySitting storeySitting = new StoreySitting();
            storeySitting.setTime(times[i]);
            storeySitting.setDestination(destinations[i]);
            storeySitting.setDate(dates[i]);
            storeySitting.setAvailable(available[i]);
            storeySitting.setEditing(false);
            storeySittingArrayList.add(storeySitting);
        }

        StoreySittingAdapter storeySittingAdapter = new StoreySittingAdapter(TimeSettingActivity.this, R.layout.item_storey_sitting, storeySittingArrayList, Color.argb(100,243,243,241));
        lv_storey_setting.setAdapter(storeySittingAdapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_edit) {
            for (int i = 0; i <= 4; i++){
                storeySittingArrayList.get(i).setEditing(true);
            }
            StoreySittingAdapter storeySittingAdapter = new StoreySittingAdapter(TimeSettingActivity.this, R.layout.item_storey_sitting, storeySittingArrayList, Color.argb(100,243,243,241));
            lv_storey_setting.setAdapter(storeySittingAdapter);
            btn_edit.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.VISIBLE);
            btn_cancel.setOnClickListener(this);
        } else if (v.getId() == R.id.btn_cancel) {
            for (int i = 0; i <= 4; i++){
                storeySittingArrayList.get(i).setEditing(false);
            }
            StoreySittingAdapter storeySittingAdapter = new StoreySittingAdapter(TimeSettingActivity.this, R.layout.item_storey_sitting, storeySittingArrayList, Color.argb(100,243,243,241));
            lv_storey_setting.setAdapter(storeySittingAdapter);
            btn_edit.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.GONE);
        }
    }
}
