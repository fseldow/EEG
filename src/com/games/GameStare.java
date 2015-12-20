package com.games;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.srtp.Attentionreader.FragmentPage1;
import com.srtp.Attentionreader.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class GameStare extends Activity {

	ImageView myView;
	//Button again;
	private Handler handler;
	boolean experiment;
	String fileName;
	String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.game_stare);
        
        myView = (ImageView)findViewById(R.id.imageView);
        myView.setScaleType(ScaleType.CENTER);
        myView.setBackgroundColor(Color.WHITE);
        experiment=FragmentPage1.experiment;
        if(experiment==true){
        long ti=System.currentTimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm");  
        Date d1=new Date(ti);  
        String t1=format.format(d1);  
		fileName= "Stare "+t1+".txt";
		filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
		filePath+="/AttReader/";
		writeTxtToFile("changeTime:", filePath, fileName);
        }
        FragmentPage1.instance=this;
        
        handler = new Handler();
        
        /*
        again = (Button)findViewById(R.id.button_again);
        try {
    		again.setOnClickListener(new Choice());
		} catch (RuntimeException e) {
			Log.e("exp", e.toString());
		}		
        again.setVisibility(View.INVISIBLE);
        again.setText("again");
        */
        
        LayoutInflater layoutInflater = this.getLayoutInflater();
		View layoutView = layoutInflater.inflate(R.layout.dialog, null);
		TextView textView = (TextView)layoutView.findViewById(R.id.instruction);
		textView.setText("请保持放松，凝视圆点，过程中暗示自己圆点变大，清晰入目，在眼睛不酸涩的情况下尽量延长眨眼间隔时间。");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(GameStare.this);
		builder.setView(layoutView);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int whichButton) 
			{
				// TODO Auto-generated method stub
				dialog.dismiss();
				new Thread()
				{
					public void run()
					{
						handler.post(Start);
					}
				}.start();
			}
			
		});

	
		AlertDialog dialog = builder.create();
		dialog.show();
		
    }
    
    /*
    class submitOnClieckListener implements OnClickListener
    {  
        public void onClick(DialogInterface dialog, int which)
        {  
            new Thread()
            {  
                public void run()
                {          
                    handler.post(Start);   
                }                     
            }.start();                        
        }
    }
    */
    /*
    private class Choice implements View.OnClickListener{

   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   			handler.post(Start);
   			again.setVisibility(View.INVISIBLE);
   			
   		}
   	}
   	*/

    Runnable Start = new Runnable()
    {

		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			//Bitmap bmp1=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			
            final Bitmap bmp2=BitmapFactory.decodeResource(getResources(), R.drawable.dot_1);
            final Bitmap bmp3=BitmapFactory.decodeResource(getResources(), R.drawable.dot_2);
            final Bitmap bmp4=BitmapFactory.decodeResource(getResources(), R.drawable.dot_3);
            final Bitmap bmp5=BitmapFactory.decodeResource(getResources(), R.drawable.dot_4);
            final Bitmap bmp6=BitmapFactory.decodeResource(getResources(), R.drawable.dot_5);   
            final Bitmap bmp7=BitmapFactory.decodeResource(getResources(), R.drawable.dot_6);
            final Bitmap bmp8=BitmapFactory.decodeResource(getResources(), R.drawable.dot_7);
            final Bitmap bmp9=BitmapFactory.decodeResource(getResources(), R.drawable.dot_8);
            final Bitmap bmp10=BitmapFactory.decodeResource(getResources(), R.drawable.dot_9);
            final Bitmap bmp11=BitmapFactory.decodeResource(getResources(), R.drawable.dot_10);
            final Bitmap bmp12=BitmapFactory.decodeResource(getResources(), R.drawable.dot_11);
            final Bitmap bmp13=BitmapFactory.decodeResource(getResources(), R.drawable.dot_12);
            final Bitmap bmp14=BitmapFactory.decodeResource(getResources(), R.drawable.dot_13);
            final Bitmap bmp15=BitmapFactory.decodeResource(getResources(), R.drawable.dot_14);
            final Bitmap bmp16=BitmapFactory.decodeResource(getResources(), R.drawable.dot_15);
        
             
            myView.setImageBitmap(bmp2);
           
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp3);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 10000); 
        
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp4);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 20000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp5);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 30000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp6);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 40000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp7);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 50000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp8);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 60000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp9);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 70000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp10);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 80000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp11);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 90000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp12);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 100000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp13);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 110000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp14);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 120000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp15);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 130000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp16);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}
            }, 140000);	
            
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp15);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 150000); 
        
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp14);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 160000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp13);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 170000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp12);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 180000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp11);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 190000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp10);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 200000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp9);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 210000); 
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp8);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 220000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp7);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 230000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp6);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 240000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp5);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 250000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp4);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 260000);
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		myView.setImageBitmap(bmp3);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}  
            }, 270000);
            
            new Handler().postDelayed(new Runnable(){  
            	public void run() {  
            		//execute the task  
            		//again.setVisibility(View.VISIBLE);
            		handler.post(Start);
            		if(experiment==true)writeTxtToFile(FragmentPage1.count+"", filePath, fileName);
            	}
            }, 280000);	
		}   	
    };
    
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
	    //生成文件夹之后，再生成文件，不然会出错
	    makeFilePath(filePath, fileName);
	    
	    String strFilePath = filePath+fileName;
	    // 每次写入时，都换行写
	    String strContent = strcontent +"\r\n";
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

}
