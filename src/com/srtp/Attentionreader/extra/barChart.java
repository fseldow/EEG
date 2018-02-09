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
	
	//��״ͼ
    private BarChart mBarChart;  
    private BarData mBarData;
    
    
    
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
 
        View bar = inflater  
                .inflate(R.layout.help, container, false);
        
        //��״ͼ
        mBarChart = (BarChart)bar.findViewById(R.id.spread_bar_chart);
        mBarData = getBarData(30, 100);  
        showBarChart(mBarChart, mBarData);
        //mBarChart.setVisibility(View.INVISIBLE);
        
		return bar;
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
