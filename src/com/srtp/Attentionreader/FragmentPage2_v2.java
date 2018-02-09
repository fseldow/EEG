package com.srtp.Attentionreader;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class FragmentPage2_v2 extends Fragment {
	LinearLayout l;
	ScrollView sV;
	BarChart barChart;
	PieChart pieChart;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
    }
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v =  inflater.inflate(R.layout.a, null);	
		init(v);
		setBarChart();
		return v;
	}

	
	
	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	    
	    inflater.inflate(R.menu.menu, menu);    	
	    super.onCreateOptionsMenu(menu,inflater);
	}
	
	private int [] loadData(){
		int a[]={0};
		return a;
	}
	private void init(View v){
		l=(LinearLayout)v.findViewById(R.id.linearLayout2);
		sV=(ScrollView)v.findViewById(R.id.scrollView1);
		barChart=(BarChart)v.findViewById(R.id.spread_bar_chart);
	}
	private void setBarChart(){
        BarData mBarData = getBarData(30, 100);  
        showBarChart(barChart, mBarData);
	}
	
	private long getRank(){
		return 0;
	}
	
	private void setScrollViewPorperties(){
	}
	
	private void showBarChart(BarChart barChart, BarData barData) {  
        barChart.setDrawBorders(false);  ////�Ƿ�������ͼ����ӱ߿�           
        barChart.setDescription("");// ��������              
        // ���û�����ݵ�ʱ�򣬻���ʾ���������ListView��EmptyView      
        barChart.setNoDataTextDescription("You need to provide data for the chart.");                  
        barChart.setDrawGridBackground(false); // �Ƿ���ʾ�����ɫ      
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // ���ĵ���ɫ�����������Ǹ���ɫ����һ��͸����           
        barChart.setTouchEnabled(true); // �����Ƿ���Դ���           
        barChart.setDragEnabled(true);// �Ƿ������ק      
        barChart.setScaleEnabled(true);// �Ƿ��������      
        barChart.setPinchZoom(false);//       
//      barChart.setBackgroundColor();// ���ñ���      
        barChart.setDrawBarShadow(true);  
        barChart.setData(barData); // ��������      
        Legend mLegend = barChart.getLegend(); // ���ñ���ͼ��ʾ  
        mLegend.setForm(LegendForm.CIRCLE);// ��ʽ      
        mLegend.setFormSize(6f);// ����      
        mLegend.setTextColor(Color.BLACK);// ��ɫ         
//      X���趨  
//      XAxis xAxis = barChart.getXAxis();  
//      xAxis.setPosition(XAxisPosition.BOTTOM);  
        barChart.animateX(2500); // ����ִ�еĶ���,x��    
    }  
	
	 private BarData getBarData(int count, float range) {  
	        ArrayList<String> xValues = new ArrayList<String>();  
	        for (int i = 0; i < count; i++) {  
	            xValues.add("1��" + (i + 1) + "��");  
	        }  
	          
	        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();  
	          
	        for (int i = 0; i < count; i++) {      
	            float value = (float) (Math.random() * range/*100���ڵ������*/) + 3;  
	            yValues.add(new BarEntry(value, i));      
	        }  
	          
	        // y������ݼ���      
	        BarDataSet barDataSet = new BarDataSet(yValues, "������״ͼ");   
	          
	        barDataSet.setColor(Color.rgb(114, 188, 223));  
	      
	        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();      
	        barDataSets.add(barDataSet); // add the datasets      
	      
	        BarData barData = new BarData(xValues, barDataSets);  
	          
	        return barData;
	    }
	
}
