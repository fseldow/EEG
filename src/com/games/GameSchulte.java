package com.games;


import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;





import java.util.Date;

import org.apache.http.util.EncodingUtils;

import com.srtp.Attentionreader.FragmentPage1;
import com.srtp.Attentionreader.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameSchulte extends Activity {
	private boolean experiment;
	int k=R.id.buttonSchulte1;
	int Button_capacity=25;
	int Finish=25;
	long ct0;
	long ct1;
	long ct;
	int startT;
	int endT;
	
	long ms;
	long s;
	
	String fileScore;
	
	String fileName;
	String filePath;
	
	ImageView reverse;
	Button ButtonBegin;
	TextView tvr;
	
	ArrayList<Integer> list = new ArrayList<Integer>();
	Button B[] = new Button[Button_capacity];


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schulte);
		

		Init ();
	}
	
	/////////////////////////////////////////////////////////////////////////////

	
	
	public void Init (){
		
		reverse =(ImageView)findViewById(R.id.imageTime);
		ButtonBegin=(Button)findViewById(R.id.buttonBeginGame);
		tvr=(TextView)findViewById(R.id.recordView);
		ButtonBegin.setOnTouchListener(new TouchBegin());
		experiment=FragmentPage1.experiment;
		FragmentPage1.instance=this;
		
		
		for(int i=0;i<Button_capacity;i++){
			B[i]=(Button)findViewById(k);
			B[i].setVisibility(View.GONE);
			k++;
			if(i%5==4)k++;		
		}
		
		filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
		filePath+="/AttReader/";
		if(experiment==true){
		long ti=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();  
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm");  
        Date d1=new Date(ti);  
        String t1=format.format(d1);  
		fileName= "Schulte "+t1+".txt";
		
		
		writeTxtToFile("score:", filePath, fileName);
		
		
		}
		
		fileScore="ScoreRank.txt";
		Log.e("a",readFile(filePath,fileScore)[0]+"");
		
	}
	
	public void writeTxtToFile(String strcontent, String filePath, String fileName) {
	    //生成文件夹之后，再生成文件，不然会出错
	    makeFilePath(filePath, fileName);
	    
	    String strFilePath = filePath+fileName;
	    // 每次写入时，都换行写
	    String strContent = strcontent + " "+startT+" "+endT+"\r\n";
	    try {
	        File file = new File(strFilePath);
	        if (!file.exists()) {
	            Log.d("TestFile", "Create the file:" + strFilePath);
	            file.getParentFile().mkdirs();
	            file.createNewFile();
	        }
	        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
	        raf.seek(file.length());
	        raf.write(strContent.getBytes());
	        raf.close();
	    } catch (Exception e) {
	        Log.e("TestFile", "Error on write File:" + e);
	    }
	}
	
	
	 
	// 生成文件
	public File makeFilePath(String filePath, String fileName) {
	    File file = null;
	    makeRootDirectory(filePath);
	    try {
	        file = new File(filePath + fileName);
	        if (!file.exists()) {
	            file.createNewFile();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return file;
	}
	 
	// 生成文件夹
	public static void makeRootDirectory(String filePath) {
	    File file = null;
	    try {
	        file = new File(filePath);
	        if (!file.exists()) {
	            file.mkdir();
	        }
	    } catch (Exception e) {
	        Log.i("error:", e+"");
	    }
	}
	
	public double[]  readFile(String filePath, String fileName) {
	    //生成文件夹之后，再生成文件，不然会出错
	    double ss[]=new double[4];
	    makeFilePath(filePath, fileName);
	    String strFilePath = filePath+fileName;
	    // 每次写入时，都换行写
	    try {
	        File file = new File(strFilePath);
	        if (!file.exists()) {
	            Log.d("TestFile", "Create the file:" + strFilePath);
	            file.getParentFile().mkdirs();
	            file.createNewFile();
	            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
	            raf.writeDouble(-1);
	            raf.writeDouble(-1);
	            raf.writeDouble(-1);
	            raf.writeDouble(-1);
	            raf.close();
	        }
	        
	        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
	        raf.seek(file.length());
	        ss[0] =raf.readDouble();
	        ss[1] =raf.readDouble();
	        ss[2] =raf.readDouble();
	        ss[3] =raf.readDouble();
	        raf.close();
	    } catch (Exception e) {
	        Log.e("TestFile", "Error on write File:" + e);
	    }
	    return ss;
	}
	
	public void GameInit (View view){
		Button_capacity=25;
		Finish=1;
		new Thread(new GameStart ()).start();		 		
	}
	
	public void GameInits (){
		Button_capacity=25;
		Finish=1;
		new Thread(new GameStart ()).start();		 		
	}
	
    public void Number (View view){	
    	Button b = (Button)view;
    	String buttonText = b.getText().toString();
    	int buttonNo = Integer.parseInt(buttonText);
    	if(buttonNo==Finish){
    		b.setVisibility(View.INVISIBLE);
    		Finish++;
    	}
    	
    	if(Finish==26){
    		if(experiment==true)endT=FragmentPage1.count;
    		ct1 = System.currentTimeMillis();
    		ct=ct1-ct0;
    		ms=ct%1000;
    		s=ct/1000;
    		tvr.setVisibility(View.VISIBLE);
    		tvr.setText("U finish in "+s+"."+ms+"s");
    		
    		
    		tvr.setTextColor(0xFFFFFFFF);
    		tvr.setTextSize(20);
    		if(experiment==true){
    			endT=FragmentPage1.count;
    			writeTxtToFile(s+"."+ms+"s", filePath, fileName);
    		new Thread(new Runnable(){   
    		    public void run(){   
    		    	try {
    	   
    					Thread.sleep(2000);
    					handler.sendEmptyMessage(5);

    					} catch (Exception ef) {
    					ef.printStackTrace();
    					}    
    		    }   

    		}).start(); 
    		}
    		
    		else{
    			ButtonBegin.setVisibility(View.VISIBLE);
      		    ButtonBegin.setText("RESTART");
    		}
    		
    	}
    	
		 
		 
		
	}
    
    public void Return(View view){
    	finish();
    }
    
    /////////////////////////////////////////////////////////////////////////////
    
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	
        	switch (msg.what) {
        	case 0:
        		tvr.setVisibility(View.INVISIBLE);
        		ButtonBegin.setVisibility(View.INVISIBLE);
        		reverse.setVisibility(View.VISIBLE);
        		reverse.setImageResource(R.drawable.begin2);
        		break;
        	case 1:
        		reverse.setImageResource(R.drawable.begin1);
        		break;
        	case 2:
        		reverse.setImageResource(R.drawable.begin_go);
        		break;
        	case 3:
        		reverse.setVisibility(View.INVISIBLE);
        		for(int i=0;i<Button_capacity;i++){
					list.add(i+1);
				}
				for(int i=0;i<25;i++){
					k=(int) (Math.random() * Button_capacity);
					B[i].setVisibility(View.VISIBLE);
					B[i].setText(list.get(k)+"");
					B[i].setTextSize(20);
					B[i].setTextColor(0xFF007FFF);
					B[i].setBackgroundResource(R.drawable.bbnd);
					B[i].setOnTouchListener(new TouchSchulte());
					//B[i].setBackgroundColor(0xFFFFFFFF);
					list.remove(k);
					Button_capacity--;
					
				}
				ct0 = System.currentTimeMillis();
        		break;
        	case 5:
        		GameInits();
        		break;
        	}
        }
    };
    
    class GameStart implements Runnable{
        
        @Override
        public void run() {
        	try {
        		handler.sendEmptyMessage(0);
				//Thread.sleep(1000);
				//handler.sendEmptyMessage(1);
				//Thread.sleep(1000);
				handler.sendEmptyMessage(2);
				Thread.sleep(1000);
				if(experiment==true)startT=FragmentPage1.count;
				handler.sendEmptyMessage(3);
				} catch (Exception ef) {
				ef.printStackTrace();
				}
        }
	 }
    
    public class TouchBegin implements View.OnTouchListener {     
  	  @Override    
  	  public boolean onTouch(View v, MotionEvent event) {     
  	   if(event.getAction() == MotionEvent.ACTION_DOWN){     
  	         v.setBackgroundColor(0xFFFFFF00);
  	    }     
  	    else if(event.getAction() == MotionEvent.ACTION_UP){     
  	    	v.setBackgroundColor(0x00000000);
  	    }     
  	   return false;     
  	  }     
  	 }
    
    public class TouchSchulte implements View.OnTouchListener {     
    	  @Override    
    	  public boolean onTouch(View v, MotionEvent event) {     
    	   if(event.getAction() == MotionEvent.ACTION_DOWN){     
    	         v.setBackgroundColor(0xFFFFFF00);
    	    }     
    	    else if(event.getAction() == MotionEvent.ACTION_UP){     
    	    	v.setBackgroundResource(R.drawable.bbnd);
    	    }     
    	   return false;     
    	  }     
    	 }
    

	

}

