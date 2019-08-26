package com.example.a57617.elevator_mobile.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a57617.elevator_mobile.PhotographActivity;
import com.example.a57617.elevator_mobile.R;
import com.example.a57617.elevator_mobile.TakeShootingActivity;
import com.example.a57617.elevator_mobile.adapter.ContentsAdapter;
import com.example.a57617.elevator_mobile.adapter.NewsAdapter;
import com.example.a57617.elevator_mobile.bean.Content;
import com.example.a57617.elevator_mobile.bean.News;
import com.example.a57617.elevator_mobile.utils.NewsApiUtil;

import java.util.ArrayList;
import java.util.List;

public class TabMainFragment extends Fragment implements View.OnClickListener {

    protected View mView;
    protected Context mContext;
    private ListView lv_news, lv_content;

    private ArrayList<News> newsArrayList = new ArrayList<News>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<Content> contentArrayList = new ArrayList<Content>();
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView tv_title = mView.findViewById(R.id.tv_title);
        tv_title.setText("首 页");
        lv_news = mView.findViewById(R.id.lv_news);
        lv_content = mView.findViewById(R.id.lv_content);

        ImageButton btn_return = mView.findViewById(R.id.btn_return);
        btn_return.setVisibility(View.GONE);
        ImageButton btn_add = mView.findViewById(R.id.btn_add);
        btn_add.setVisibility(View.VISIBLE);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        newsArrayList = (ArrayList<News>) bundle.getSerializable("news");

        //demo
        String[] strings = new String[4];
        strings[0] = "茅台集团原副总高守洪被双开：一再拒绝接受组织挽救";
        strings[1] = "驻香港部队官宣：我们是维护“一国两制”的重要力量";
        strings[2] = "8月1日起47城大陆居民赴台个人游试点暂停";
        strings[3] = "新中国成立70周年之际一批特赦对象被依法释放";
        for (int i = 0; i <= 3; i++) {
            Content content = new Content();
            content.setContent(strings[i]);
            contentArrayList.add(content);
        }
        ContentsAdapter contentsAdapter = new ContentsAdapter(getActivity(), R.layout.item_contents_list, contentArrayList, Color.argb(100,243,243,241));
        lv_content.setAdapter(contentsAdapter);
        NewsAdapter newsAdapter = new NewsAdapter(getActivity(), R.layout.item_news_list, newsArrayList, Color.argb(100,243,243,241));
        lv_news.setAdapter(newsAdapter);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog();
            }
        });
    }

    private void setDialog() {
        Dialog mCameraDialog = new Dialog(getActivity(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.item_moment, null);
        //初始化视图
        root.findViewById(R.id.ll_photo).setOnClickListener(this);
        root.findViewById(R.id.ll_album).setOnClickListener(this);
        root.findViewById(R.id.ll_cancel).setOnClickListener(this);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_photo) {
            Intent intent = new Intent(getActivity(), PhotographActivity.class);
            startActivity(intent);
        }
    }
}
