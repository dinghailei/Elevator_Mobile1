package com.example.a57617.elevator_mobile;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a57617.elevator_mobile.adapter.RobotPagerAdapter;
import com.example.a57617.elevator_mobile.fragment.RobotChattingFragment;

import java.util.ArrayList;

public class RobotActivity extends AppCompatActivity {
    private final static String TAG = "TabCustomActivity";
    private Toolbar tl_head;
    private ViewPager vp_content;
    private TabLayout tab_title;
    private TextView tv_toolbar1, tv_toolbar2, tv_toolbar3, tv_toolbar4, tv_toolbar5, tv_toolbar6;
    private ArrayList<String> mTitleArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("机 器 人 小 程 序");

        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tab_title = (TabLayout) findViewById(R.id.tab_title);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        setSupportActionBar(tl_head);
        mTitleArray.add("聊天");
        mTitleArray.add("拿快递");
        mTitleArray.add("用品");
        mTitleArray.add("巡检");
        mTitleArray.add("访客");
        mTitleArray.add("丢垃圾");
        initTabLayout();
        initTabViewPager();
    }

    private void initTabLayout() {
        tab_title.addTab(tab_title.newTab().setCustomView(R.layout.item_toolbar_chatting));
        tv_toolbar1 = (TextView) findViewById(R.id.tv_toolbar1);
        tv_toolbar1.setText(mTitleArray.get(0));
        tab_title.addTab(tab_title.newTab().setCustomView(R.layout.item_toolbar_package));
        tv_toolbar2 = (TextView) findViewById(R.id.tv_toolbar2);
        tv_toolbar2.setText(mTitleArray.get(1));
        tab_title.addTab(tab_title.newTab().setCustomView(R.layout.item_toolbar_appliance));
        tv_toolbar3 = (TextView) findViewById(R.id.tv_toolbar3);
        tv_toolbar3.setText(mTitleArray.get(2));
        tab_title.addTab(tab_title.newTab().setCustomView(R.layout.item_toolbar_patrol));
        tv_toolbar4 = (TextView) findViewById(R.id.tv_toolbar4);
        tv_toolbar4.setText(mTitleArray.get(3));
        tab_title.addTab(tab_title.newTab().setCustomView(R.layout.item_toolbar_guest));
        tv_toolbar5 = (TextView) findViewById(R.id.tv_toolbar5);
        tv_toolbar5.setText(mTitleArray.get(4));
        tab_title.addTab(tab_title.newTab().setCustomView(R.layout.item_toolbar_rubbish));
        tv_toolbar6 = (TextView) findViewById(R.id.tv_toolbar6);
        tv_toolbar6.setText(mTitleArray.get(5));
        tab_title.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp_content));
    }

    private void initTabViewPager() {
        RobotPagerAdapter adapter = new RobotPagerAdapter(
                getSupportFragmentManager(), mTitleArray);
        vp_content.setAdapter(adapter);
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tab_title.getTabAt(position).select();
            }
        });
    }

//
}
