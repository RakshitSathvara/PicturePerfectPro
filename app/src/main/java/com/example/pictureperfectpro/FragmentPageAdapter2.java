package com.example.pictureperfectpro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter2 extends FragmentPagerAdapter {

	public FragmentPageAdapter2(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		
		switch (arg0)
		{
			case 0:	
					return new Notifications();
					
			case 1:
					//return new Collections();
					
			default:
					break;
		}
		
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
