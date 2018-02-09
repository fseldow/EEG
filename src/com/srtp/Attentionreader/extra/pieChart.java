package com.srtp.Attentionreader.extra;

import java.util.ArrayList;



import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.srtp.Attentionreader.R;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class pieChart extends Fragment {
	
	//��ͼ
    private PieChart mChart;
    
    //private int mContent;  
    //private boolean mIsLastPic;  
	
    /*
    public static pieChart newInstance(int content, boolean isLastPic) {  
        pieChart pie = new pieChart();  
   
        pie.mContent = content;  
        pie.mIsLastPic = isLastPic;  
        return pie;  
    } 
    */ 

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  

        View pie = inflater  
                .inflate(R.layout.help2, container, false);

        //��ͼ
        mChart = (PieChart)pie.findViewById(R.id.spread_pie_chart); 
        PieData mPieData = getPieData(4, 100);   
        showPieChart(mChart, mPieData); 
        //mChart.setVisibility(View.INVISIBLE); 
        
        return pie;
	}
	
	public void showPieChart(PieChart pieChart, PieData pieData) {   
        pieChart.setHoleColorTransparent(true);   
     
        pieChart.setHoleRadius(60f);  //�뾶   
        pieChart.setTransparentCircleRadius(64f); // ��͸��Ȧ   
        //pieChart.setHoleRadius(0)  //ʵ��Բ   
     
        pieChart.setDescription("���Ա�״ͼ");   
     
        // mChart.setDrawYValues(true);   
        pieChart.setDrawCenterText(true);  //��״ͼ�м�����������   
     
        pieChart.setDrawHoleEnabled(true);   
     
        pieChart.setRotationAngle(90); // ��ʼ��ת�Ƕ�   
     
        // draws the corresponding description value into the slice   
        // mChart.setDrawXValues(true);   
     
        // enable rotation of the chart by touch   
        pieChart.setRotationEnabled(true); // �����ֶ���ת   
     
        // display percentage values   
        pieChart.setUsePercentValues(true);  //��ʾ�ɰٷֱ�   
        // mChart.setUnit(" ��");   
        // mChart.setDrawUnitsInChart(true);   
     
        // add a selection listener   
//      mChart.setOnChartValueSelectedListener(this);   
        // mChart.setTouchEnabled(false);   
     
//      mChart.setOnAnimationListener(this);   
     
        pieChart.setCenterText("Quarterly Revenue");  //��״ͼ�м������   
     
        //��������   
        pieChart.setData(pieData);    
             
        // undo all highlights   
//      pieChart.highlightValues(null);   
//      pieChart.invalidate();   
     
        Legend mLegend = pieChart.getLegend();  //���ñ���ͼ   
        mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);  //���ұ���ʾ   
//      mLegend.setForm(LegendForm.LINE);  //���ñ���ͼ����״��Ĭ���Ƿ���   
        mLegend.setXEntrySpace(7f);   
        mLegend.setYEntrySpace(5f);   
             
        pieChart.animateXY(1000, 1000);  //���ö���   
        // mChart.spin(2000, 0, 360);   
    }   
     
    /** 
     *  
     * @param count �ֳɼ����� 
     * @param range 
     */   
    
    public PieData getPieData(int count, float range) {   
             
        ArrayList<String> xValues = new ArrayList<String>();  //xVals������ʾÿ�������ϵ�����   
     
        for (int i = 0; i < count; i++) {   
            xValues.add("Quarterly" + (i + 1));  //��������ʾ��Quarterly1, Quarterly2, Quarterly3, Quarterly4   
        }   
     
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals������ʾ��װÿ�������ʵ������   
     
        // ��ͼ����   
        /** 
         * ��һ������ͼ�ֳ��Ĳ��֣� �Ĳ��ֵ���ֵ����Ϊ14:14:34:38 
         * ���� 14����İٷֱȾ���14%  
         */   
        float quarterly1 = 14;   
        float quarterly2 = 14;   
        float quarterly3 = 34;   
        float quarterly4 = 38;   
     
        yValues.add(new Entry(quarterly1, 0));   
        yValues.add(new Entry(quarterly2, 1));   
        yValues.add(new Entry(quarterly3, 2));   
        yValues.add(new Entry(quarterly4, 3));   
     
        //y��ļ���   
        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"/*��ʾ�ڱ���ͼ��*/);   
        pieDataSet.setSliceSpace(0f); //���ø���״ͼ֮��ľ���   
     
        ArrayList<Integer> colors = new ArrayList<Integer>();   
     
        // ��ͼ��ɫ   
        colors.add(Color.rgb(205, 205, 205));   
        colors.add(Color.rgb(114, 188, 223));   
        colors.add(Color.rgb(255, 123, 124));   
        colors.add(Color.rgb(57, 135, 200));   
     
        pieDataSet.setColors(colors);   
     
        DisplayMetrics metrics = getResources().getDisplayMetrics();   
        float px = 5 * (metrics.densityDpi / 160f);   
        pieDataSet.setSelectionShift(px); // ѡ��̬����ĳ���   
     
        PieData pieData = new PieData(xValues, pieDataSet);   
             
        return pieData; 
    }
}