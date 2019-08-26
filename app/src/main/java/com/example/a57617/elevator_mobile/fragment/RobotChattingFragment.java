package com.example.a57617.elevator_mobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a57617.elevator_mobile.R;

public class RobotChattingFragment extends Fragment {
	private static final String TAG = "RobotChattingFragment";
	protected View mView;
	protected Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		mView = inflater.inflate(R.layout.fragment_robot_chatting, container, false);
		
		return mView;
	}
	
}
