package com.srtp.Attentionreader.extra;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class TestFragmentAdapter extends FragmentPagerAdapter {
	

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