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
import com.example.a57617.elevator_mobile.Robot1Activity;
import com.example.a57617.elevator_mobile.RobotActivity;

public class TabProcedureFragment extends Fragment {
    protected View mView;
    protected Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_procedure, container, false);
        TextView tv_title = mView.findViewById(R.id.tv_title);
        tv_title.setText("小 程 序");
        ImageButton btn_return = mView.findViewById(R.id.btn_return);
        btn_return.setVisibility(View.GONE);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.btn_robot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Robot1Activity.class);
                startActivity(intent);
            }
        });
    }
}
