package com.example.a57617.elevator_mobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a57617.elevator_mobile.R;
import com.example.a57617.elevator_mobile.TimeSettingActivity;
import com.example.a57617.elevator_mobile.widget.MyProgressBar;

public class TabMineFragment extends Fragment {

    protected View mView;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        TextView tv_title = mView.findViewById(R.id.tv_title);
        tv_title.setText("我 的");
        ImageButton btn_return = mView.findViewById(R.id.btn_return);
        btn_return.setVisibility(View.GONE);

        //设置资料完善度进度条
        MyProgressBar myProgressBar = mView.findViewById(R.id.mpb_information);
        myProgressBar.setProgress(34);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.ll_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TimeSettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
