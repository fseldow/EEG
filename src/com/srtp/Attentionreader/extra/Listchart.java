//package com.srtp.Attentionreader.extra;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.data.ChartData;
//import com.srtp.Attentionreader.R;
//import com.xxmassdeveloper.mpchartexample.listviewitems.ChartItem;
//import com.xxmassdeveloper.mpchartexample.listviewitems.LineChartItem;
//import com.xxmassdeveloper.mpchartexample.listviewitems.PieChartItem;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//public class Listchart extends Activity{
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.showlist);
//		 ListView lv = (ListView) findViewById(R.id.listView1);
//
//	     ArrayList<ChartItem> list = new ArrayList<ChartItem>();
//	     list.add(new LineChartItem(generateDataLine(), getApplicationContext()));
//	     list.add(new PieChartItem(generateDataBar(), getApplicationContext()));
//	     ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
//	     lv.setAdapter(cda);
//	     
//	}
//	private class ChartDataAdapter extends ArrayAdapter<ChartItem> {
//
//        public ChartDataAdapter(Context context, List<ChartItem> objects) {
//            super(context, 0, objects);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            return getItem(position).getView(position, convertView, getContext());
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            // return the views type
//            return getItem(position).getItemType();
//        }
//
//        @Override
//        public int getViewTypeCount() {
//            return 3; // we have 3 different item-types
//        }
//    }
//
//	private ChartData<?> generateDataBar() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private ChartData<?> generateDataLine() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
