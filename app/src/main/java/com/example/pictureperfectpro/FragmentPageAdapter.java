package com.example.pictureperfectpro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter
{

	String data;
	
	public FragmentPageAdapter(FragmentManager fm, String data)
	{		
		super(fm);
		this.data=data;
	}

	@Override
	public Fragment getItem(int arg0)
	{
		
		switch (arg0)
		{
			case 0:	
					return new ME(data);
					
			case 1:
					return new FEEDS();
					
			case 2:
					return new Collections(data);
					
			case 3:
					return new EXIFSearch(data);
					
			default:
					break;
		}
		
		return null;
		
	}

	@Override
	public int getCount()
	{
		return 4;
	}

}
