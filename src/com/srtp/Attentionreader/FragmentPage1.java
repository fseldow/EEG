package com.srtp.Attentionreader;
import java.io.File;
import java.util.ArrayList;

import com.neurosky.thinkgear.TGDevice;
import com.srtp.Attentionreader.R.id;

import android.R.integer;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.*;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
public class FragmentPage1 extends Fragment{
	private int Clock;//控制顺逆时针,1为顺、-1为逆时针
	private boolean OnOff;//是否连接成功
	private boolean Data;//sql是否在运行
	private int hour;
	private int minute;
	private int second;
	private SQLiteDatabase db;
	private LineChart chart1;
	public Button ButtonStart;
	public Button ButtonForward;
	public Button ButtonReverse;
	public Button ButtonConnect;
	public MyTimePicker timeP;
	 public TextView tv;
	 public TextView tvt;//显示时间
	 public TextView ttv;
	 public TextView TextStart;
	 private int flagatt;
	 private int flagmed;
	 private int timenum;
	 private  LineDataSet lineDataSet;
	 
	  
	 
	 BluetoothAdapter mbluetoothadapter;
	 private ArrayList<String> xValueStrings = new ArrayList<String>();
	 private ArrayList<LineDataSet> lineDataSets ;
	 private ArrayList<Entry> yValueStrings = new ArrayList<Entry>();
	 private ArrayList<Entry> yValueStrings2 = new ArrayList<Entry>();
 	public TGDevice tgDevice;
	final static boolean rawEnabled = false;
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    mbluetoothadapter=((MainActivity) getActivity()).getbluetoothAdaoter();
    Log.e("exp", "bluthok");   
    OnOff=false;
    
        if(mbluetoothadapter == null) {
        	// Alert user that Bluetooth is not available
        	Toast.makeText(getActivity(), "Bluetooth not available", Toast.LENGTH_LONG).show();
        	
        	//finish();
        	return;
        }else {
        	/* create the TGDevice */
        	
        	tgDevice = new TGDevice(mbluetoothadapter, handler);
        	if(OnOff==false){
	   			if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
	   	       	 tgDevice.connect(rawEnabled);}
	   			
        }  
        
        
//*************************************************        
    	String fileName = "mind";
		String path = Environment.getExternalStorageDirectory().getAbsolutePath();
		path += "/" + "HelloEEG";
		File destDir = new File(path);
		  if (!destDir.exists()) {
		   destDir.mkdirs();//创建文件夹
		  }
		  path+="/" +fileName;
		if (!path.endsWith(".sqlite"))
				path += ".sqlite";
//****************************************************
		
		db =SQLiteDatabase.openOrCreateDatabase(path, null);//创建sqlite
		db.execSQL("create table if not exists mindwave(foc int, med int)");//sqlite语句创建表名为mindwave
		 Log.e("exp", "sqliteok");  	
  
 
		}
    public void onDestroyView(){
    	if(Data)Toast.makeText(getActivity(), "Interrupt", Toast.LENGTH_LONG).show();
    	Data=false;
    	super.onDestroyView();
    }

	
	public void onDestroy() {
		if(OnOff){
			OnOff=false;
    	    tgDevice.close();
		}
		Data=false;
        super.onDestroy();
    }
	
	
	
	
  private final Handler handler = new Handler() {
        @Override
        
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        	case 10000:
        		
        		
            		RotateAnimation animation =new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        	   		animation.setDuration(750);
            		ButtonStart.startAnimation(animation);
            		tvt.setText(hour+":"+minute+":"+second);
            		
            		
        		 
        		break;
        	case 20000:
        		TextStart.setText("Restart");
        		break;
        	case 30000:
        		
        		ButtonStart.setBackgroundResource(R.drawable.start3);
        		break;

        	
            case TGDevice.MSG_STATE_CHANGE:

                switch (msg.arg1) {
	                case TGDevice.STATE_IDLE:
	                    break;
	                case TGDevice.STATE_CONNECTING:		                	
	                	tv.setText("Connecting...\n");
	                	break;		                    
	                case TGDevice.STATE_CONNECTED:
	                	tv.setText("Connected.\n");
	                	tgDevice.start();
	                	OnOff=true;
	                	//new Thread(new IfConnected ()).start();
	                	
	                	
	                	ButtonConnect.setBackgroundResource(R.drawable.connected3);
	                    break;
	                case TGDevice.STATE_NOT_FOUND:
	                	tv.setText("Can't find\n");
	                	break;
	                case TGDevice.STATE_NOT_PAIRED:
	                	tv.setText("not paired\n");
	                	break;
	                case TGDevice.STATE_DISCONNECTED:
	                	Data=false;
	                	OnOff=false;
	                	ButtonConnect.setBackgroundResource(R.drawable.connections);
	                	tv.setText("Disconnected mang\n");
                }

                break;
            case TGDevice.MSG_POOR_SIGNAL:
            		//signal = msg.arg1;
            		//tv.setText("PoorSignal: " + msg.arg1 + "\n");
                break;
            case TGDevice.MSG_RAW_DATA:	  
            		//raw1 = msg.arg1;
            		
            		tv.setText("Got raw: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_HEART_RATE:
        		tv.setText("Heart rate: " + msg.arg1 + "\n");
                break;
            case TGDevice.MSG_ATTENTION:
            	int att = msg.arg1;
        	    tv.setText("Attention: " + msg.arg1 + "\n");
        	    if(Data){
        		
        	    ContentValues values = new ContentValues();
        	    values.put("foc", att);
        	    db.insert("mindwave", null, values);
        	    getLineData(att, 1, timenum);
        	    timenum++;
        	    /*flagatt = 1;
        	    if(flagatt==1&&flagmed==1)
        	    {
        	    	timenum++;
        	    	flagatt=0;
        	    	flagmed=0;
        	    }*/
        	}
        		//Log.v("HelloA", "Attention: " + att + "\n");
        	break;
            case TGDevice.MSG_MEDITATION:
            	int med = msg.arg1;
           	 //tv.append("meditation: " + msg.arg1 + "\n");
           	if(Data){
           	
           	ContentValues values = new ContentValues();
       	    
       		values.put("med", med);
           	
               db.insert("mindwave", null, values);
              
               /*getLineData(med, 2, timenum);
               flagmed = 1;
       	    if(flagatt==1&&flagmed==1)
       	    {
       	    	timenum++;
       	    	flagatt=0;
       	    	flagmed=0;
       	    }*/
           	}
               
           	break;
            case TGDevice.MSG_BLINK:
            		//tv.setText("Blink: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_RAW_COUNT:
            		//tv.setText("Raw Count: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_LOW_BATTERY:
            	Toast.makeText(getActivity().getApplicationContext(), "Low battery!", Toast.LENGTH_SHORT).show();
            	break;
            case TGDevice.MSG_RAW_MULTI:
            	//TGRawMulti rawM = (TGRawMulti)msg.obj;
            	//tv.setText("Raw1: " + rawM.ch1 + "\nRaw2: " + rawM.ch2);
            default:
            	break;
        }

        	
                
        }
    };
    

    
	
    
    
        	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v =  inflater.inflate(R.layout.fragment_1, null);		
	      if (v == null) {
	             Log.i("FragmentA", "onCreateView:view is null");
	          } 
	  else {
	              Log.i("FragmentA", "onCreateView:view is NOOOOOOOOOOOOOOOOT null");
	          }
//*********************************************************************************************	      
	      timenum=0;
	      lineDataSet = new LineDataSet(null, "attention eeg data"); 
	      chart1 = (LineChart) v.findViewById(R.id.REALTIMEchart1);
	      chart1.setDrawBorders(false);
	      chart1.setDescription("eeg");
	      chart1.setNoDataTextDescription("there is no data");
	      chart1.setDrawGridBackground(false);
	      chart1.setTouchEnabled(true);
	      chart1.setDragEnabled(true);
	      chart1.setScaleEnabled(true);
	      chart1.setHighlightEnabled(true);
	      chart1.setBackgroundColor(Color.LTGRAY);
	     
	      lineDataSet.setColor(ColorTemplate.getHoloBlue());
	      lineDataSet.setCircleColor(ColorTemplate.getHoloBlue());
	      lineDataSet.setLineWidth(2f);
	      lineDataSet.setCircleSize(4f);
	      lineDataSet.setFillAlpha(65);
	      lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
	      lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
	      lineDataSet.setValueTextColor(Color.WHITE);
	      lineDataSet.setValueTextSize(10f);
	      // create a data object with the datasets    
	      LineData lineData = new LineData();  
	      chart1.setData(lineData);
	      lineData.addDataSet(lineDataSet);
	      //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

	        // get the legend (only possible after setting data)
	        Legend l = chart1.getLegend();

	        // modify the legend ...
	        // l.setPosition(LegendPosition.LEFT_OF_CHART);
	        l.setForm(LegendForm.LINE);
	        //l.setTypeface(tf);
	        l.setTextColor(Color.WHITE);

	        XAxis xl = chart1.getXAxis();
	        //xl.setTypeface(tf);
	        xl.setTextColor(Color.WHITE);
	        xl.setDrawGridLines(false);
	        xl.setAvoidFirstLastClipping(true);

	        YAxis leftAxis = chart1.getAxisLeft();
	        //leftAxis.setTypeface(tf);
	        leftAxis.setTextColor(Color.WHITE);
	        leftAxis.setAxisMaxValue(120f);
	        leftAxis.setDrawGridLines(true);

	        YAxis rightAxis = chart1.getAxisRight();
	        rightAxis.setEnabled(false);
//*****************************************************************************************************	      
	      Clock=1;
	      Data=false;
	      hour=0;
	      minute=0;
	      second=0;
	      ButtonStart=(Button)v.findViewById(R.id.buttonStart);
	      ButtonForward=(Button)v.findViewById(R.id.buttonForward);
	      ButtonReverse=(Button)v.findViewById(R.id.buttonReverse);
	      ButtonConnect=(Button)v.findViewById(R.id.buttonConnect);
	      timeP=(MyTimePicker)v.findViewById(R.id.IB1);
	      tv=(TextView)v.findViewById(R.id.textView11);
	      tvt=(TextView)v.findViewById(R.id.textTime);
	      ttv=(TextView)v.findViewById(R.id.textView1);
	      TextStart=(TextView)v.findViewById(R.id.textStart);
	      
	      setStyle();
	      
	      
	      Log.e("tvt", tvt.getText()+"");
	       if(ButtonStart ==null||ButtonForward==null||ButtonReverse==null)
	        {
	        	Log.e("exp", "hehe");
	        }
	    	try {
				ButtonStart.setOnClickListener(new StartOnClickListener());
				ButtonConnect.setOnClickListener(new ConnectOnClickListener());
				ButtonReverse.setOnClickListener(new ReverseOnClickListener());
				ButtonForward.setOnClickListener(new ForwardOnClickListener());
				ButtonStart.setOnTouchListener(new TouchButton());
			} catch (RuntimeException e) {
				Log.e("exp", e.toString());
			}
	    	Log.e("exp", "buttonok");
	    	return v;}
	
	
	class timeForward implements Runnable{
        
        @Override
        public void run() { 
                // TODO Auto-generated method stub
        	while(Data){
				try {
					Thread.sleep(1000);
					if(second!=59){second++;}
					else {second=0;minute++;}
					if(minute==59){hour++;minute=0;}
					handler.sendEmptyMessage(10000);
					} catch (Exception ef) {
					ef.printStackTrace();
					}
				
               
        }
        	handler.sendEmptyMessage(30000);
        }
	}
class timeReverse implements Runnable{
        
        @Override
        public void run() {                     
                // TODO Auto-generated method stub
        	while(Data){
				try {
					
					Thread.sleep(1000);
					
					if(second==0){second=59;if(minute==0){minute=59;hour--;}else minute--;}
					else second--;
					if(hour==0&&minute==0&&second==0){Data=false;}
					
					handler.sendEmptyMessage(10000);
					} catch (Exception ef) {
					ef.printStackTrace();
					}
			}
        	if(hour==0&&minute==0&&second==0)
        		handler.sendEmptyMessage(20000);
        	handler.sendEmptyMessage(30000);
               
        }
}




	

private void getLineData(int data,int eegindex,int num)
{
	
	LineData ldata = chart1.getData();
	ldata.addXValue(""+num);
	if (eegindex==1)
		{{
			
			if(data>0)
			{ldata.addEntry(new Entry(data,num ),0);
			
			}
		}
		
	}
	/*if (eegindex==2)
	{{
		
		if(data>0)
		{lineDataSets.get(1).addEntry(new Entry(data, num));
		
		}
	}*/
	chart1.notifyDataSetChanged();
	chart1.setVisibleXRange(6);
	chart1.moveViewToX(ldata.getXValCount()-7);
	//chart1.invalidate();
}


	
	

	   	  private class StartOnClickListener implements View.OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				
				
				if(OnOff){
				
				if(Data==false){
					Data=true;
					
						if(Clock==1){
							hour=0;minute=0;second=0;
							tvt.setVisibility(View.VISIBLE);
							timeP.setVisibility(View.GONE);
							chart1.setVisibility(View.VISIBLE);
							tv.setTextColor(0xFF000000);
						    TextStart.setText("Stop");
						    new Thread(new timeForward ()).start();
						    
						}
						else{
						    TextStart.setText("Stop");
						    hour=timeP.getHour();
						    minute=timeP.getMinute()/2;
						    Log.e("tp",timeP.getMinute()+"");
						    second=0;
						    tv.setText(hour+":"+minute+""+second);
						    tvt.setVisibility(View.VISIBLE);
							timeP.setVisibility(View.GONE);
							chart1.setVisibility(View.VISIBLE);
						    new Thread(new timeReverse ()).start();
						    
						}
					
				}
				else {
					
					Data=false;
					TextStart.setText("Start");
					
				}
				}
				else Toast.makeText(getActivity().getApplicationContext(), "Unconnected", Toast.LENGTH_SHORT).show();
				
				
				
		        	 }}
	   	private class ForwardOnClickListener implements View.OnClickListener{

	   		@Override
	   		public void onClick(View v) {
	   			if(Data==false){
	   				timeP.setVisibility(View.INVISIBLE);
	   				ButtonReverse.setBackgroundResource(R.drawable.daojishi2);
	   				ButtonForward.setBackgroundResource(R.drawable.zhengjishi2);
	   			Clock=1;
	   			hour=0;
	   			minute=0;
	   			second=0;
	   			tvt.setText(hour+":"+minute+":"+second);
	   			TextStart.setText("Start");}
	   			// TODO Auto-generated method stub
	   			
	   		}
	   	}
	   	
	   	private class ConnectOnClickListener implements View.OnClickListener{

	   		@Override
	   		public void onClick(View v) {
	   			
	   			
	   			if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
	   	       	 tgDevice.connect(rawEnabled);
	   			
	   			
	   			
	   			
	   		}
	   			// TODO Auto-generated method stub
	   			
	   		
	   	}
	   	
	   	
	   	private class ReverseOnClickListener implements View.OnClickListener{

	   		@Override
	   		public void onClick(View v) {
	   			if(Data==false){
	   				ButtonReverse.setBackgroundResource(R.drawable.daojishi);
	   				ButtonForward.setBackgroundResource(R.drawable.zhengjishi);
	   				
	   				TextStart.setText("Start");
	   				chart1.setVisibility(View.GONE);
	   				tvt.setVisibility(View.INVISIBLE);
	   				timeP.setVisibility(View.VISIBLE);
	   				
	   				
	   			Clock=-1;
	   			}
	   			//else Toast.makeText(getActivity(), "Bluetooth is out of connection", )
	   			// TODO Auto-generated method stub
	   			
	   		}
	   	}
	   	
	    public class TouchButton implements View.OnTouchListener {     
	    	  @Override    
	    	  public boolean onTouch(View v, MotionEvent event) {     
	    	   if(event.getAction() == MotionEvent.ACTION_DOWN){     
	    	         v.setBackgroundResource(R.drawable.ontouch2);
	    	    }     
	    	    else if(event.getAction() == MotionEvent.ACTION_UP){
	    	    		v.setBackgroundResource(R.drawable.start3);
	    	    }     
	    	   return false;     
	    	  }     
	    	 }
	   	
	   	public void setStyle(){
	   		//ButtonStart.setBackgroundColor(0x00000000);
	   		TextStart.setTextColor(0xFFFFFFFF);
	   		TextStart.setTextSize(20);
	   		TextStart.setText("Start");
	   		ButtonConnect.setTextSize(10);
	   		if(OnOff){
        	    ButtonConnect.setBackgroundResource(R.drawable.connected3);}
		    else {
		    	ButtonConnect.setBackgroundResource(R.drawable.connections);
		    }
		    timeP.setVisibility(View.GONE);
		    tv.setText(null);
		    tv.setTextColor(0xFFFFFFFF);
		    tvt.setText(hour+":"+minute+":"+second);
	   		ttv.setTextSize(20);
	   		ttv.setTextColor(0xFFFFFFFF);
	   		tvt.setTextColor(0xFFFFFFFF);
	   		tvt.setTextSize(25);
	   		
	   		

	   		//ButtonReverse.setBackgroundResource(R.drawable.a1);
	   		//ButtonReverse.setHeight(48);
	   		
	   		ButtonReverse.setBackgroundResource(R.drawable.daojishi2);
	   		//ButtonForward.setBackgroundColor(0xFFFFFF00);
	   		ButtonForward.setBackgroundResource(R.drawable.zhengjishi2);
	   	}
	    


}
