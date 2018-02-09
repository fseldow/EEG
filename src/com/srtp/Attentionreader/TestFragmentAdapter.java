package com.srtp.Attentionreader;

import com.srtp.Attentionreader.extra.barChart;
import com.srtp.Attentionreader.extra.pieChart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class TestFragmentAdapter  extends FragmentPagerAdapter {

	public TestFragmentAdapter(FragmentManager fm) {  
	    super(fm);  
	}  

	@Override  
	public Fragment getItem(int position) {  
		
		barChart bar = new barChart();
		pieChart pie = new pieChart();
	    
	    if (position == 1)    
	    	return bar;
	    else
	    	return pie;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}
