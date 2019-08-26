package com.example.a57617.elevator_mobile;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

public class PackageActivity extends AppCompatActivity implements View.OnClickListener, CityPickerListener {

    private EditText et_city;
    private CityPicker cityPicker;//省份/城市/区域选择器
    private ImageButton btn_edit_sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        TextView textView = findViewById(R.id.tv_title);
        textView.setText("寄 快 递");
        findViewById(R.id.btn_return).setOnClickListener(this);

        //初始化cityPicker
        cityPicker = new CityPicker(PackageActivity.this,this);

        et_city = findViewById(R.id.et_city);
        et_city.setOnClickListener(this);
        btn_edit_sender = findViewById(R.id.btn_edit_sender);
        btn_edit_sender.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_return) {
            finish();
        } else if (v.getId() == R.id.et_city) {
            cityPicker.show();
        } else if (v.getId() == R.id.btn_edit_sender) {
            LayoutInflater inflater = LayoutInflater.from(getApplication());
            View view = inflater.inflate(R.layout.item_edit_sender, null);
            AlertDialog.Builder builder=new AlertDialog.Builder(PackageActivity.this);
            builder.setView(view);
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }

    @Override
    public void getCity(String s) {
        et_city.setText(s);
    }

    @Override
    public void onBackPressed() {
        if (cityPicker.isShow()) {
            cityPicker.close();
            return;
        }
        super.onBackPressed();
    }
}
