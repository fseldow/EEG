package com.srtp.Attentionreader.extra;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.srtp.Attentionreader.R;

import android.R.integer;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class showPieData extends Activity {
	private PieChart mChart;
	private int num3;
	private int num6;
	private int num8;
	private int num10;
	private String attdata[];
	private String BackColor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        BackColor  = sharedPreferences.getString("bgcolor", "#f0f8ff");

        int c=Color.parseColor(BackColor);
		Bundle myBundle = this.getIntent().getExtras();
		attdata = myBundle.getStringArray("attdata");
		setContentView(R.layout.showpiedata);
		mChart = (PieChart)findViewById(R.id.pchart1);
		num3=0;
		num6=0;
		num8=0;
		num10=0;
		mChart.animateXY(1500, 1500);
		mChart.setRotationEnabled(false);
		mChart.setCenterText("注意力时间分布");
		mChart.setCenterTextSize(22);
		mChart.setData(getLineData(attdata.length));
		mChart.setBackgroundColor(c);
	}


private PieData getLineData(int count)
{
	ArrayList<Entry> pvalues = new ArrayList<Entry>();
	int j=0;
	float a;
	for(int i=0;i<count;i++)
	{
		
		if ((attdata[i]!=null)&&(attdata[i]!="0"))
		{
			a = Float.parseFloat(attdata[i]);
			if(a>0&&a<30)
			{
				num3++;
			}
			if(a>29&&a<60)
			{
				num6++;
			}
			if(a>59&&a<80)
			{
				num8++;
			}
			if(a>79&&a<=100)
			{
				num10++;
			}
		}
		
	}
	pvalues.add(new Entry(num3, 1));
	pvalues.add(new Entry(num6, 2));
	pvalues.add(new Entry(num8, 3));
	pvalues.add(new Entry(num10,4));
	PieDataSet dataSet  = new PieDataSet(pvalues,"");
	dataSet.setSliceSpace(2f);
	dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
	PieData cdData = new PieData(getQuarters(),dataSet);
	cdData.setValueTextSize(15);
	return cdData;
}
private ArrayList<String> getQuarters() {

    ArrayList<String> q = new ArrayList<String>();
    q.add("很不专心");
    q.add("正常");
    q.add("专心");
    q.add("很专心");

    return q;
}
}
		