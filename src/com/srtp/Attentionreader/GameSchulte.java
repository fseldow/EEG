package com.srtp.Attentionreader;


import java.util.ArrayList;





import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameSchulte extends Activity {
	
	int k=R.id.buttonSchulte1;
	int Button_capacity=25;
	int Finish=25;
	long ct0;
	long ct1;
	long ct;
	
	long ms;
	long s;
	
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
		
		
		for(int i=0;i<Button_capacity;i++){
			B[i]=(Button)findViewById(k);
			B[i].setVisibility(View.GONE);
			k++;
			if(i%5==4)k++;
			
		}
		
	}
	
	public void GameInit (View view){
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
    		ct1 = System.currentTimeMillis();
    		ct=ct1-ct0;
    		ms=ct%1000;
    		s=ct/1000;
    		ButtonBegin.setVisibility(View.VISIBLE);
    		ButtonBegin.setText("RESTART");
    		tvr.setVisibility(View.VISIBLE);
    		tvr.setText("U finish in "+s+"."+ms+"s");
    		tvr.setTextColor(0xFFFFFFFF);
    		tvr.setTextSize(20);
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
        	}
        }
    };
    
    class GameStart implements Runnable{
        
        @Override
        public void run() {
        	try {
        		handler.sendEmptyMessage(0);
				Thread.sleep(1000);
				handler.sendEmptyMessage(1);
				Thread.sleep(1000);
				handler.sendEmptyMessage(2);
				Thread.sleep(1000);
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
