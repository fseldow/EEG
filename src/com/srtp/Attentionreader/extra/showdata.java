package com.srtp.Attentionreader.extra;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.srtp.Attentionreader.R;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class showdata extends Activity implements OnSeekBarChangeListener{
	
	private LineChart mychart;
	private String attdata[];
	private SeekBar seekBar1;
	private TextView textView1;
	public int blackgroudcolor = Color.rgb(114, 188, 223);
	public int linecolor = Color.WHITE;
	int xprogress;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle myBundle = this.getIntent().getExtras();
		attdata = myBundle.getStringArray("attdata");
	//	meddata = myBundle.getStringArray("meddata");
		Log.e("exp", ""+attdata.length);
		setContentView(R.layout.showdata);
		mychart = (LineChart)findViewById(R.id.chart1);
		
	//*******************************************************	
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		int color2 =Color.rgb(114, 188, 223); 
		String a  = sharedPreferences.getString("bgcolor", "#f0f8ff");
		if (a.equals("-9257761"))
		{
			a=Integer.toString(Color.rgb(114, 188, 223));
		}
		Log.e("color", a);
		blackgroudcolor = Color.parseColor(a);
	//*****************************************************************************************	
		seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
		seekBar1.setMax(attdata.length);
		seekBar1.setProgress(7);
		seekBar1.setOnSeekBarChangeListener(this);
		LineData mlineData = getLineData(attdata.length);		
		showChart(mychart,mlineData,blackgroudcolor,linecolor);
		textView1 = (TextView)findViewById(R.id.tvXMax);
		textView1.setTextColor(0xFFFFFFFF);
		String str = String.valueOf(attdata.length); 
		textView1.setText(str);
		Log.e("exp", "he22");
	}
	private void showChart(LineChart lineChart, LineData linedata, int color,int color2)
	{
		lineChart.setDrawBorders(false);
		lineChart.setDescription("eeg");
		lineChart.setNoDataTextDescription("there is no data");
		lineChart.setDrawGridBackground(false);
		lineChart.setTouchEnabled(true);
		lineChart.setDragEnabled(true);
		lineChart.setScaleXEnabled(true);
		lineChart.setScaleYEnabled(false);
		lineChart.setPinchZoom(true);
		lineChart.setBackgroundColor(color);		
		Legend mLegend = lineChart.getLegend();
		mLegend.setForm(LegendForm.CIRCLE);
		mLegend.setTextColor(color2);
		mLegend.setFormSize(2f);
	
		lineChart.animateX(2500);
	}
	private LineData getLineData(int count)
	{
		ArrayList<String> xValueStrings = new ArrayList<String>();
	
		ArrayList<Entry> yValueStrings = new ArrayList<Entry>();
		//ArrayList<Entry> yValueStrings2 = new ArrayList<Entry>();
		
		//int minsize = Math.min(attdata.length, meddata.length);
		int j=0;
		float a;
		for(int i=0;i<count;i++)
		{
			
			if ((attdata[i]!=null)&&(attdata[i]!="0"))
			{
				a = Float.parseFloat(attdata[i]);
				if(a>0)
				{yValueStrings.add(new Entry(a, j));
				xValueStrings.add(""+j);
				j++;}
			}
			
		}
		/*for(int i=0;i<attdata.length;i++)
		{
			
			if ((attdata[i]!=null)&&(attdata[i]!="0"))
			{
				a = Float.parseFloat(attdata[i]);
				if(a>0)
				{yValueStrings.add(new Entry(a, j));
				xValueStrings.add(""+j);
				j++;}
			}
			
		}*/
		j=0;
	/*	float b;
		for(int i=0;i<minsize;i++)
		{
			
			if ((meddata[i]!=null)&&(meddata[i]!="0 "))
			{
				
				b = Float.parseFloat(meddata[i]);
				if(b>0)
				{yValueStrings2.add(new Entry(b, j));				
				j++;}
			}
		}*/
		
		LineDataSet lineDataSet = new LineDataSet(yValueStrings, "att eeg data"); 
		lineDataSet.setValueTextSize(2f);
		lineDataSet.setLineWidth(1.75f); // 线宽    
        lineDataSet.setCircleSize(3f);// 显示的圆形大小    
        lineDataSet.setColor(Color.RED);// 显示颜色    
        lineDataSet.setCircleColor(Color.RED);// 圆形的颜色    
        lineDataSet.setHighLightColor(Color.RED); // 高亮的线的颜色    
        /*LineDataSet lineDataSet2 = new LineDataSet(yValueStrings2, "med eeg data"); 
		lineDataSet2.setValueTextSize(2f);
		lineDataSet2.setLineWidth(1.75f); // 线宽    
        lineDataSet2.setCircleSize(3f);// 显示的圆形大小    
        lineDataSet2.setColor(Color.WHITE);// 显示颜色    
        lineDataSet2.setCircleColor(Color.WHITE);// 圆形的颜色    
        lineDataSet2.setHighLightColor(Color.WHITE); // 高亮的线的颜色    
    */
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();    
        lineDataSets.add(lineDataSet); // add the datasets    
        //lineDataSets.add(lineDataSet2);
        // create a data object with the datasets    
        LineData lineData = new LineData(xValueStrings, lineDataSets);    
        mychart.setData(lineData);
        return lineData;    
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
		xprogress = seekBar1.getProgress();
		if(xprogress!=seekBar1.getMax())
		{xprogress+=1;}
		textView1.setText(""+xprogress);// TODO Auto-generated method stub
		Log.e("exp", ""+xprogress);
		getLineData(xprogress);
		mychart.invalidate();
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

}
