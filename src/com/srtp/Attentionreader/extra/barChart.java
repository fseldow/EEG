package com.srtp.Attentionreader.extra;

import java.util.ArrayList;



import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.srtp.Attentionreader.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class barChart extends Fragment {
	
	//柱状图
    private BarChart mBarChart;  
    private BarData mBarData;
    
    
    
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
 
        View bar = inflater  
                .inflate(R.layout.help, container, false);
        
        //柱状图
        mBarChart = (BarChart)bar.findViewById(R.id.spread_bar_chart);
        mBarData = getBarData(30, 100);  
        showBarChart(mBarChart, mBarData);
        //mBarChart.setVisibility(View.INVISIBLE);
        
		return bar;
	}
	
	private void showBarChart(BarChart barChart, BarData barData) {  
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框   
            
        barChart.setDescription("");// 数据描述      
          
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView      
        barChart.setNoDataTextDescription("You need to provide data for the chart.");      
                 
        barChart.setDrawGridBackground(false); // 是否显示表格颜色      
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度      
        
        barChart.setTouchEnabled(true); // 设置是否可以触摸      
       
        barChart.setDragEnabled(true);// 是否可以拖拽      
        barChart.setScaleEnabled(true);// 是否可以缩放      
      
        barChart.setPinchZoom(false);//       
      
//      barChart.setBackgroundColor();// 设置背景      
          
        barChart.setDrawBarShadow(true);  
         
        barChart.setData(barData); // 设置数据      
  
        Legend mLegend = barChart.getLegend(); // 设置比例图标示  
      
        mLegend.setForm(LegendForm.CIRCLE);// 样式      
        mLegend.setFormSize(6f);// 字体      
        mLegend.setTextColor(Color.BLACK);// 颜色      
          
//      X轴设定  
//      XAxis xAxis = barChart.getXAxis();  
//      xAxis.setPosition(XAxisPosition.BOTTOM);  
      
        barChart.animateX(2500); // 立即执行的动画,x轴    
    }  
  
    private BarData getBarData(int count, float range) {  
        ArrayList<String> xValues = new ArrayList<String>();  
        for (int i = 0; i < count; i++) {  
            xValues.add("1月" + (i + 1) + "日");  
        }  
          
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();  
          
        for (int i = 0; i < count; i++) {      
            float value = (float) (Math.random() * range/*100以内的随机数*/) + 3;  
            yValues.add(new BarEntry(value, i));      
        }  
          
        // y轴的数据集合      
        BarDataSet barDataSet = new BarDataSet(yValues, "测试柱状图");   
          
        barDataSet.setColor(Color.rgb(114, 188, 223));  
      
        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();      
        barDataSets.add(barDataSet); // add the datasets      
      
        BarData barData = new BarData(xValues, barDataSets);  
          
        return barData;
    }

}
