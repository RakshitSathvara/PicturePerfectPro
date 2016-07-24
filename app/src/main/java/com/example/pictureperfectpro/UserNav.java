package com.example.pictureperfectpro;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

public class UserNav extends Fragment implements ActionBar.TabListener  {
	
	ActionBar actionBar;
	
	ViewPager viewPager2;
	
	String data;
	
	FragmentPageAdapter2 ft;
	
	
	@Override
	
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
		//setContentView(R.layout.user_nav_layout);
		
		View userNav=inflater.inflate(R.layout.user_nav_layout, container,false);
		
		viewPager2=(ViewPager) userNav.findViewById(R.id.pager2);
		
		ft=new FragmentPageAdapter2(getChildFragmentManager());
		
		viewPager2.setAdapter(ft);
		
		viewPager2.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.addTab(actionBar.newTab().setText("NOTIFICATIONS").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("COLLECTIONS").setTabListener(this));
		
		
		return userNav;
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager2.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
