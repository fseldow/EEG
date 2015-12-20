package com.games;







import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.srtp.Attentionreader.FragmentPage1;
import com.srtp.Attentionreader.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
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
import android.view.WindowManager;
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

public class GameNumber extends Activity implements OnInitListener{
	private boolean experiment;
	ImageView direction;
	ProgressBar timeBar;
	TextView tvN;
	TextView tvC;
	TextView tvS;
	EditText etN;
	ImageView ListeningImage;
	SpannableStringBuilder builder ;
	ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);  
	ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);  
	ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);  
	ForegroundColorSpan greenSpan = new ForegroundColorSpan(Color.GREEN);  
	ForegroundColorSpan yellowSpan = new ForegroundColorSpan(Color.YELLOW);  
	Button ButtonChoice;
	Button ButtonExit;
	Boolean FLAG;
	Boolean Counter;
	int Chapter;
	int Length;
	int TimeLimit;
	int BarStatus;
	long Score;
	int startT;
	int endT;
	InputMethodManager imm ;
	long Nums;
	long Got;
	long ArtificialRandom;
	Thread thread;
	String CounterString;
	String filePath ;
    String fileName;
    String strScore;
	private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;	
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.game_number);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		FragmentPage1.instance=this;
	    experiment=FragmentPage1.experiment;
		Init ();
		
		
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
		
		//thread= new Thread(new Ran ()).start();
		
		Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

	}

	

	
	private final Handler handler = new Handler() {
        @Override
        
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        	case 0x00:
 
        		timeBar.setVisibility(View.VISIBLE);
        		timeBar.setProgress(0);
        		tvN.setText(Nums+"");
        		speakWords(Long.toString(Nums));
        		if(experiment==true)startT=FragmentPage1.count;
                Log.e("vv", "hi");
        		tvN.setTextSize(30);
        		if(Score==0)
        		{
        			tvS.setText("Your score is 0");
        		}
        		else
        		{
        			tvS.setText("Your score is"+" "+strScore);
        		}
        		builder = new SpannableStringBuilder(tvN.getText().toString());        		
        		builder.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
        		builder.setSpan(yellowSpan, 1, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);  
        		builder.setSpan(blueSpan, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
        		builder.setSpan(greenSpan, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
        		builder.setSpan(yellowSpan, 4,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        		tvN.setText(builder);
        		tvN.setVisibility(View.INVISIBLE);
        		tvC.setVisibility(View.VISIBLE);
        		tvC.setText("Chapter"+" "+Chapter);
        		ButtonChoice.setVisibility(View.GONE);
        		ButtonExit.setVisibility(View.GONE);
        		etN.setVisibility(View.INVISIBLE);
        		etN.setText("");
        		etN.setWidth(1000);
        		etN.setHeight(200);
        		etN.setBackgroundResource(R.drawable.edit);
        		etN.setEnabled(true);
        		ListeningImage.setVisibility(View.VISIBLE);
        		break;
        	case 0x01:
        		timeBar.setProgress(BarStatus);
        		if(BarStatus==999){
        			tvN.setText("TIME OVER");
        		}
        		break;
        	case 0x02:
        		
        		timeBar.setVisibility(View.GONE);
        		tvN.setVisibility(View.INVISIBLE);
        		tvN.setVisibility(View.VISIBLE);
        		if(Counter==false)tvN.setText("Please restate the numbers.");
        		else tvN.setText("Please restate in backward order!");
        		tvN.setTextSize(20);
        		etN.setVisibility(View.VISIBLE);
        		ListeningImage.setVisibility(View.INVISIBLE);
        		break;
        	case 0x03:
        		tvN.setText("CONGRATULATIONS!");
        		tvN.setTextSize(30);
        		etN.setBackgroundColor(0xFF7F7F7F);
        		etN.setEnabled(false);
        		ButtonChoice.setVisibility(View.VISIBLE);
        		ButtonExit.setVisibility(View.VISIBLE);
        		ButtonChoice.setText("CONTINUE");
        		ButtonExit.setText("Exit");
        		break;
        	case 0x04:
        		tvN.setText("YOU LOSE");
        		tvN.setTextSize(30);
        		etN.setBackgroundColor(0xFF7F7F7F);
        		etN.setEnabled(false);
        		ButtonChoice.setVisibility(View.VISIBLE);
        		ButtonExit.setVisibility(View.VISIBLE);
        		ButtonChoice.setText("AGAIN");
        		ButtonExit.setText("Exit");
        		break;
        	case 0x05:
        		thread.interrupt();
        		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        		
        		thread=new Thread(new Operation ());
        		thread.start();
        		break;
        	
        	}
        }
	};
	private void speakWords(String speech) {
		 
        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
            myTTS = new TextToSpeech(this, this);
            }
            else {
                    //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }
 
        //setup TTS
    public void onInit(int initStatus) {
     
            //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
        	Log.e("a", "hi");
            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
            {  myTTS.setLanguage(Locale.US);
            Log.e("b", "hi");
        }else{Log.e("kk", "hi");
    	myTTS.setLanguage(Locale.getDefault());
    	myTTS.setSpeechRate((float) 0.5);
            }
        
    	
    }
         if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
            Log.e("c", "hi");
        }
        
    }
    
 // 将字符串写入到文本文件中
 	public void writeTxtToFile(String strcontent, String filePath, String fileName) {
 	    //生成文件夹之后，再生成文件，不然会出错
 	    makeFilePath(filePath, fileName);
 	    
 	    String strFilePath = filePath+fileName;
 	    // 每次写入时，都换行写
 	    String strContent = strcontent + "\r\n";
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
 	
	public void Init(){
		Log.e("test","1");
		Score=0;
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); 
		direction= (ImageView)findViewById(R.id.imageDirection);
		timeBar= (ProgressBar)findViewById(R.id.progressBar1);
		tvN= (TextView)findViewById(R.id.textViewNum);
		tvC= (TextView)findViewById(R.id.textViewChapter);
		tvS= (TextView)findViewById(R.id.textViewScore);
		etN= (EditText)findViewById(R.id.editText1);
		etN.setBackgroundResource(R.drawable.edit);
		ButtonChoice= (Button)findViewById(R.id.buttonChoice);
		ListeningImage= (ImageView)findViewById(R.id.imageViewListening);
		ListeningImage.setVisibility(View.INVISIBLE);
		try {
			
    		ButtonChoice.setOnClickListener(new Choice());
		} catch (RuntimeException e) {
			Log.e("exp", e.toString());
		}
		ButtonExit= (Button)findViewById(R.id.buttonExit);
		FLAG=false;
		Counter=false;
		Chapter=1;
		Length=7;
		ArtificialRandom=0;
		Log.e("test","2");
		if(experiment==true){
		long ti=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();  
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm");  
        Date d1=new Date(ti);  
        String t1=format.format(d1);  
		fileName= "Num "+t1+".txt";
		filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
		filePath+="/AttReader/";
		writeTxtToFile("score:", filePath, fileName);
		}
	}
	
	public void Properties(){
		Length=7+Chapter/4;
		//Length=Chapter+8;
		if(Chapter%3==0)Counter=true;
		else Counter=false;
		//Counter=true;
		TimeLimit=10;
		if(TimeLimit<7)TimeLimit=7;
		BarStatus=0;
		etN.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Length)});
	}
	
	public void Return(View view){
    	finish();
    }
	
	public class Touch implements View.OnTouchListener {     
	  	  @Override    
	  	  public boolean onTouch(View v, MotionEvent event) {     
	  	   if(event.getAction() == MotionEvent.ACTION_UP && FLAG==false){
	  		 AlphaAnimation animation =new AlphaAnimation(1,0);
	 		animation.setDuration(1000);
	 		direction.startAnimation(animation);
	  	    	direction.setVisibility(View.GONE);
	  	    	FLAG=true;
	  	    	tvS.setText("Your score is 0");
	  	    	tvS.setVisibility(View.VISIBLE);
	  	    	thread=new Thread(new Operation ());
	  	    	thread.start();
	  	    }     
	  	   return true;     
	  	       
	  	 }
	}
	
	private class Choice implements View.OnClickListener{

   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   			thread=new Thread(new Operation ());
   			thread.start();
   			
   		}
   	}
	
	

class Operation implements Runnable{
        
        @Override
        public void run() {  
        	Properties();
        	Log.e("a","b");
        	long a=(long)Math.pow(10,Length-1);
        	Nums=(long)(Math.random()*(9*a));
        	Nums=Nums+a-1;
                // TODO Auto-generated method stub
        	try {
				if(Chapter==1)Thread.sleep(2000);
				handler.sendEmptyMessage(0x00);
				} catch (Exception ef) {
				ef.printStackTrace();
				}
        	Log.e("zdsb",""+Nums);

    		int b=TimeLimit*1000;
    		int present=0;
    		while(present<b){
    			BarStatus=1000*present/b;
    			
    			handler.sendEmptyMessage(0x01);
    			present++;
    		}
    		if(present==b){
    			try {
    				Thread.sleep(3000);
    				handler.sendEmptyMessage(0x02);
    				} catch (Exception ef) {
    				ef.printStackTrace();
    				}
    			etN.setOnKeyListener(new OnKeyListener() {
    				@Override
    				public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
    				if(keyCode == KeyEvent.KEYCODE_ENTER&&arg2.getAction()==KeyEvent.ACTION_UP ){
    					
    					if(!etN.getText().toString().equals("")){
    						endT=FragmentPage1.count;
    					if(Counter==false){
    						
    						Got=Long.parseLong(etN.getText().toString());
    					}
    					else{
    						CounterString="";
    						char[] charArray = etN.getText().toString().toCharArray();
    						for (int i=charArray.length-1; i>=0; i--){
    							CounterString += charArray[i];
    							
    						} 
    						Got=Long.parseLong(CounterString);	
    					}
    					
    					if(Got==Nums){
    						Score+=Math.pow(2,Chapter);
    						strScore = Long.toString(Score);
    						if(experiment==true)writeTxtToFile(Score+" "+startT+" "+endT,filePath,fileName);
    						//handler.sendEmptyMessage(0x03);
    						Chapter++;
    					}
    					else{
    						//handler.sendEmptyMessage(0x04);
    						Chapter=1;
    						if(experiment==true)writeTxtToFile(Score+" "+startT+" "+endT,filePath,fileName);
    					}
    					handler.sendEmptyMessage(0x05);
    					}
    					return true;
    				}
    				return false;
    				}
    			});
    		}
    	
               
        }
}


class Ran implements Runnable{
    
    @Override
    public void run() {
    	while(FLAG==false){
    		ArtificialRandom++;
    	}
    }
}



	
	
};
