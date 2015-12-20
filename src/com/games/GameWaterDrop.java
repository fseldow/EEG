package com.games;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.srtp.Attentionreader.FragmentPage1;
import com.srtp.Attentionreader.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GameWaterDrop extends Activity {

	ImageView direction;
	TextView tvN;
	TextView tvC;
	TextView tvS;
	EditText etN;
	Boolean FLAG;
	int Chapter;
	int Length=3;
	int BarStatus;
	float Speed;
	float Voice1;
	float Voice2;
	long Nums;
	long Got;
	long ArtificialRandom;
	String CounterString;
	boolean experiment;
	private SoundPool soundPool;
	long Time;
	int Score;
	String filename;
	String strScore;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_water_drop);
		
		Init ();
		
		FragmentPage1.instance=this;

		Animation mScaleAnimation = new ScaleAnimation(0.0f, 1f, 0.0f, 1f, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation.setDuration(1000);
        mScaleAnimation.setFillAfter(true);

        Animation mAlphaAnimation = new AlphaAnimation(0,1);
        mAlphaAnimation.setDuration(2000);
        
        AnimationSet mAnimationSet=new AnimationSet(false);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mAlphaAnimation);
        direction.startAnimation(mAnimationSet);
		
		direction.setOnTouchListener(new Touch());
		
		long date=System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		filename = format.format(new Date(date));
		

		
		new Thread(new Ran ()).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private final Handler handler = new Handler()
	{
        @Override
        
        public void handleMessage(Message msg) 
        {
        	switch (msg.what) 
        	{
        	case 0x00:
        		
        		tvN.setText("Please listen");
        		tvN.setTextSize(30);
        		tvN.setVisibility(View.VISIBLE);
        		tvC.setVisibility(View.VISIBLE);
        		tvC.setText("Chapter"+" "+Chapter);
        		if(Score==0)
        		{
        			tvS.setText("Your score is 0");
        		}
        		else
        		{
        			tvS.setText("Your score is"+" "+strScore);
        		}
        		tvS.setVisibility(View.VISIBLE);
        		etN.setVisibility(View.INVISIBLE);
        		etN.setText("");
        		etN.setBackgroundColor(0x00000000);
        		etN.setEnabled(true);
        		int nums = (int)Nums;
        		
        		for(int i=0;i<nums;i++)
    			{
    				int delaytime1 = (int)((Math.random()*5+5)*100);
    				int delaytime2 = (int)((Math.random()*4+3)*100);
    				Speed = (float) (Math.random()*1.5+0.5);
    				soundPool.play(1,1, 1, 3, 1, Speed);
    				if(Chapter>2 && Chapter<7)
            		{
    					soundPool.play(2,Voice1,Voice1, 2, (int) (nums*0.5/3), 1);
            		}
    				else if(Chapter>6)
    				{
    					soundPool.play(2,Voice1,Voice1, 2, (int) (nums*0.5/3), 1);
    					soundPool.play(3, Voice2, Voice2, 1, (int) (nums*0.5/4), 1);
    				}
    			    try {
    			    	if(Chapter<10)
    			    	{
    			    		Thread.sleep(delaytime1);
    			    	}
    			    	else
    			    	{
    			    		Thread.sleep(delaytime2);
    			    	}
					    
				    } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    }
    			    
    			    if(Chapter<10)
			    	{
    			    	Time=Time+delaytime1;
			    	}
			    	else
			    	{
			    		Time=Time+delaytime2;
			    	}
    		    }
     
        		break;
        	case 0x02:
        		soundPool.autoPause();
        		Toast.makeText(GameWaterDrop.this, "TIME OVER", Toast.LENGTH_LONG).show();
        		//tvN.setVisibility(View.INVISIBLE);
        		tvN.setVisibility(View.VISIBLE);
        		tvN.setText("Please count the number of the drops of water and show it on the screen.");
        		tvN.setTextSize(20);
        		etN.setBackgroundColor(Color.WHITE);
        		etN.setVisibility(View.VISIBLE);
        		break;
        	}
        }
	};
	
	@SuppressWarnings("deprecation")
	public void Init()
	{
		experiment=FragmentPage1.experiment;
		direction= (ImageView)findViewById(R.id.imageDirection);
		//timeBar= (ProgressBar)findViewById(R.id.progressBar1);
		tvN= (TextView)findViewById(R.id.textViewNum);
		tvC= (TextView)findViewById(R.id.textViewChapter);
		tvS= (TextView)findViewById(R.id.textViewScore);
		etN= (EditText)findViewById(R.id.editText1);
		FLAG=false;
		Chapter=1;
		Speed=(float) 0.5;
		Voice1=(float) 0.1;
		Voice2=(float) 0.4;
		ArtificialRandom=0;
		soundPool= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
		soundPool.load(this,R.raw.water_drop,3);
		soundPool.load(this,R.raw.noise_first,2);
		soundPool.load(this,R.raw.thunder,1);
		Time = 0;
		Score=0;
	}
	
	public void Properties()
	{
		BarStatus=0;
		etN.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Length)});
	}
	
	public void Return(View view)
	{
    	finish();
    }
	
	public class Touch implements View.OnTouchListener
	{     
	  	  @Override    
	  	  public boolean onTouch(View v, MotionEvent event) {     
	  	   if(event.getAction() == MotionEvent.ACTION_UP && FLAG==false)
	  	   {
	  		 AlphaAnimation animation =new AlphaAnimation(1,0);
	 		animation.setDuration(1000);
	 		direction.startAnimation(animation);
	  	    	direction.setVisibility(View.GONE);
	  	    	FLAG=true;
	  	    	tvN.setText("Please listen");
        		tvN.setTextSize(30);
        		tvN.setVisibility(View.VISIBLE);
        		tvC.setVisibility(View.VISIBLE);
        		tvC.setText("Chapter"+" "+Chapter);
        		tvS.setText("Your score is 0");
        		tvS.setVisibility(View.VISIBLE);
        		etN.setVisibility(View.INVISIBLE);
        		etN.setText("");
        		etN.setBackgroundColor(0x00000000);
        		etN.setEnabled(true);
	  	    	new Thread(new Operation ()).start();
	  	    }     
	  	   return true;       
	  	 }
	}
	
    class Operation implements Runnable
    {
        @Override
        public void run() 
        {  
        	Properties();
        	Log.e("a","b");
        	Nums=(int)(Math.random()*20)+1;
        	Log.e("Nums", Nums+"");
                // TODO Auto-generated method stub
        	
        	try {
				Thread.sleep(2000);
				handler.sendEmptyMessage(0x00);          //正常开始游戏
				} catch (Exception ef) {
				ef.printStackTrace();
				}

        	int b=(int) (Time);
        	//int b=3000;
    		int present=0;
    		while(present<b)
    		{
    			BarStatus=1000*present/b;
    			Log.e("adf",BarStatus+"");
    			present++;
    		}
    		if(present==b)
    		{
    			try {
    				Thread.sleep(Time);
    				handler.sendEmptyMessage(0x02);        //时间到
    				}
    			catch (Exception ef) {
    				ef.printStackTrace();
    				}

    			etN.setOnKeyListener(new OnKeyListener()
    			{
    				@Override
    				public boolean onKey(View arg0, int keyCode, KeyEvent arg2)
    				{
    					if(keyCode == KeyEvent.KEYCODE_ENTER&&arg2.getAction()==KeyEvent.ACTION_UP )
    					{
    						String etn = etN.getText().toString();
    						Log.e("aa", etn);
    						if(etn.equals(""))
    						{
    						    Toast t =Toast.makeText(GameWaterDrop.this, "Please input the number", 5000);
    						    t.show();   
    						}
    						else
        					{
        						Got=Integer.parseInt(etN.getText().toString());
        						if(Got==Nums)
            					{
            						                                    //答对
            						Score+=Math.pow(2, Chapter);
            						strScore = Integer.toString(Score);
            						if(experiment){
            						try {
    									saveToSDCard("Water "+filename+".txt",strScore);
    								} catch (Exception e) {
    									// TODO Auto-generated catch block
    									e.printStackTrace();
    								}
            						}
            						Chapter++;
            						Time=0;
            						if(Chapter>3 && Chapter<7)
            						{
            							Voice1+=0.3;
            						}
            						else if(Chapter==7)
            						{
            							Voice1=(float) 0.4;
            						}
            						else if(Chapter>7 && Chapter<10)
            						{
            							Voice2+=0.3;
            							Voice1+=0.3;
            						}
            						else if(Chapter==10)
            						{
            							Voice1=Voice2=1;
            						}
            						tvN.setText("Please listen");
            		        		tvN.setTextSize(30);
            		        		tvN.setVisibility(View.VISIBLE);
            		        		tvC.setVisibility(View.VISIBLE);
            		        		tvC.setText("Chapter"+" "+Chapter);
            		        		if(Score==0)
            		        		{
            		        			tvS.setText("Your score is 0");
            		        		}
            		        		else
            		        		{
            		        			tvS.setText("Your score is"+" "+strScore);
            		        		}
            		        		etN.setVisibility(View.INVISIBLE);
            		        		etN.setText("");
            		        		etN.setBackgroundColor(0x00000000);
            		        		etN.setEnabled(true);
            		        		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); 
            		        		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            						new Thread(new Operation ()).start();
            					}
            					else
            					{
            						                                         //答错
            						Chapter=1;
            						Time=0;
            						Voice1=0;
            						Voice2=0;
            						//handler.sendEmptyMessage(0x00);
            						tvN.setText("Please listen");
            		        		tvN.setTextSize(30);
            		        		tvN.setVisibility(View.VISIBLE);
            		        		tvC.setVisibility(View.VISIBLE);
            		        		tvC.setText("Chapter"+" "+Chapter);
            		        		if(Score==0)
            		        		{
            		        			tvS.setText("Your score is 0");
            		        		}
            		        		else
            		        		{
            		        			tvS.setText("Your score is"+" "+strScore);
            		        		}
            		        		etN.setVisibility(View.INVISIBLE);
            		        		etN.setText("");
            		        		etN.setBackgroundColor(0x00000000);
            		        		etN.setEnabled(true);
            		        		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); 
            		        		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            						new Thread(new Operation ()).start();
            					}
        					}	
        					return true;
        				}
        				return false;
    				}
    			});
    		}   
        }
    }

    class Ran implements Runnable
    {
        
        @Override
        public void run()
        {
        	while(FLAG==false)
        	{
        		ArtificialRandom++;
        	}
        }
    }
    
    public void saveToSDCard(String filename,String content) throws Exception
    {
    	String strContent = content + "\r\n";
    	File file = new File(Environment.getExternalStorageDirectory()+"/AttReader/",filename);
    	
    	RandomAccessFile raf = new RandomAccessFile(file,"rwd");
    	raf.seek(file.length());
    	raf.write(strContent.getBytes());
    	raf.close();

    }
    
    public void onDestory(){
    	soundPool.autoPause();
        super.onDestroy();
    }
}
