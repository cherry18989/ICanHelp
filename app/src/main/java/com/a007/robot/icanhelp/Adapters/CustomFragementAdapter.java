package com.a007.robot.icanhelp.Adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class CustomFragementAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	private List<String> titleList;
	public CustomFragementAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}
	public CustomFragementAdapter(FragmentManager fm, List<Fragment> fragments,List<String> titleList) {
		super(fm);
		this.fragments = fragments;
		this.titleList = titleList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titleList.get(position);
	}

}
