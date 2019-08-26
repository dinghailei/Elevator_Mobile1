package com.example.a57617.elevator_mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.a57617.elevator_mobile.fragment.BtnFirstPageFragment;
import com.example.a57617.elevator_mobile.fragment.BtnSecondPageFragment;
import com.example.a57617.elevator_mobile.fragment.RobotChattingFragment;

import java.util.ArrayList;

public class RobotPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<String> mTitleArray;

	public RobotPagerAdapter(FragmentManager fm, ArrayList<String> titleArray) {
		super(fm);
		mTitleArray = titleArray;
	}

	//不同ViewPage的页面演示
	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return new BtnFirstPageFragment();
		} else if (position == 1) {
			return new BtnSecondPageFragment();
		}
		return new BtnFirstPageFragment();
	}

	@Override
	public int getCount() {
		return mTitleArray.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitleArray.get(position);
	}
}
