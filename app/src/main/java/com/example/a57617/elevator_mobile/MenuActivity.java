package com.example.a57617.elevator_mobile;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.a57617.elevator_mobile.fragment.TabMainFragment;
import com.example.a57617.elevator_mobile.fragment.TabMineFragment;
import com.example.a57617.elevator_mobile.fragment.TabProcedureFragment;

public class MenuActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(getTabView(R.string.main, R.drawable.icon_main),
                TabMainFragment.class, bundle);
        mTabHost.addTab(getTabView(R.string.procedure, R.drawable.icon_procedure),
                TabProcedureFragment.class, bundle);
        mTabHost.addTab(getTabView(R.string.mine, R.drawable.icon_mine),
                TabMineFragment.class, bundle);

        //设置tabs之间的分隔线不显示
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }

    private TabHost.TabSpec getTabView(int textId, int imgId) {
        String text = getResources().getString(textId);
        Drawable drawable = ContextCompat.getDrawable(this,imgId);
        //必须设置图片大小，否则不显示
        drawable.setBounds(0, 0, 100, 100);
        View item_tabbar = getLayoutInflater().inflate(R.layout.item_tabbar, null);
        TextView tv_item = (TextView) item_tabbar.findViewById(R.id.tv_item_tabbar);
        tv_item.setText(text);
        tv_item.setCompoundDrawables(null, drawable, null, null);
        TabHost.TabSpec spec = mTabHost.newTabSpec(text).setIndicator(item_tabbar);
        return spec;
    }
}
