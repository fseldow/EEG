package com.games;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.srtp.Attentionreader.FragmentPage1;
import com.srtp.Attentionreader.R;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class Test extends Activity {
	private boolean experiment;
	private Button bt[];
	private DisplayMetrics dm;
	private int W;
	private int target;
	private int target2;
	public int minute;
	public int second;
	public int level;
	static public boolean ifEnd;
	int trueCount;
	int falseCount;
	int cols;
	int rows;
	String filePath ;
    String fileName1;
    String fileName2;
	private TextView instruction;
	private ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dm = new DisplayMetrics(); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindowManager().getDefaultDisplay().getMetrics(dm); 
		W=dm.widthPixels;
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test);

		init();
		new Thread(new Operation()).start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private TableLayout tableLayout(int a,int b){
		bt=new Button[a*b];
		TableLayout tab = new TableLayout (this);
        tab.setStretchAllColumns(true);

        for(int i =0 ; i< a ;i++){
         TableRow row = new TableRow(this);
         //row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.FILL_PARENT));
         row.setPadding(0, 0, 0, 0);
          for(int x =0 ; x<b ;x++){
           bt[x+i*b]=new Button(this);
           row.addView(bt[x+i*b]);
          }
          
         tab.addView(row);
        }
        initButton();
        
        return tab;
	}
	
	private String buttonString(int target){
		int ran=(int) (Math.random() *45);
		String num;
		if(ran>=40){num=target+"";}
		else num=ran%10+"";
		return num;	
	}
	
	private class ButtonClick implements Button.OnClickListener{

   		@Override
   		public void onClick(View v) {
   			Button b = (Button)v;
   			int i=Integer.parseInt(String.valueOf(b.getTag()));
   			String buttonText = b.getText().toString();
   			Log.i("buttonText",buttonText);
   			if(judge(bt,i,level,target,target2)){
   				b.setVisibility(View.INVISIBLE);
   				if(experiment)writeTxtToFile('L'+level+"T"+FragmentPage1.count+'X'+i%cols+'Y'+(i-i%cols)/cols,filePath,fileName1);
   				trueCount++;
   			}
   			else{
   				if(experiment)writeTxtToFile('L'+level+"F"+FragmentPage1.count+'X'+i%cols+'Y'+(i-i%cols)/cols,filePath,fileName1);
   				falseCount++;
   			}
   		}	
   	}
	
	private boolean judge(Button bt[],int i,int chapter,int target, int target2){
		boolean judgement=false;
		switch(chapter){
		case 1:
			if(bt[i].getText().equals(target+""))
				judgement=true;
			else judgement=false;
			break;
		case 2:
			if(i<cols*rows-1){
			if(bt[i+1].getText().equals(target+"")&&!bt[i].getText().equals(target+"")&&i<cols*rows-1)
				judgement=true;
			else judgement=false;
			}
			else judgement=false;
			break;
		case 3:
			if(bt[i].getText().equals(target2+"")){
				for(int j=i+1;j<cols*rows;j++){
					if(bt[j].getText().equals(target+"")){judgement=true;break;}
					if(bt[j].getText().equals(target2+"")){judgement=false;break;}
				}
			}
			break;
		}
	    return judgement;
	}
	
	private void init(){
		experiment=FragmentPage1.experiment;
		FragmentPage1.instance=this;
		cols=10;
		rows=200;
		minute=2;
		second=0;
		level=1;
		ifEnd=false;
		instruction=(TextView)findViewById(R.id.instructionView);
		scrollView=(ScrollView)findViewById(R.id.scrollView);
		target=(int)(Math.random()*10);
		instruction.setText("目标数字"+target+"      "+"剩余时间："+minute+":"+second+'\n');
		instruction.setTextSize(15);
		TableLayout tab=tableLayout(rows,cols);
		LinearLayout l;
		l=(LinearLayout)findViewById(R.id.linearLayout);
		l.addView(tab);
		
		long ti=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();  
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm");  
        Date d1=new Date(ti);  
        String t1=format.format(d1);  
		fileName1= FragmentPage1.userName+"_testFlag"+t1+".txt";
		fileName2= FragmentPage1.userName+"_testScore"+t1+".txt";
		filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
		filePath+="/AttReaderTest/";
		if(experiment)writeTxtToFile("TESTFLAGS", filePath, fileName1);
	}
	
    class Operation implements Runnable{
	    
	    @Override
	    public void run() {
	    	ifEnd=false;
	    	
	    	minute=2;
	    	second=0;
	    	try {
	    		Thread.sleep(1000);
				
	    		
	    	}catch (Exception ef) {
				ef.printStackTrace();
				}
	    	
	    	while(second>0||minute>0){
	    	try {
	    		Thread.sleep(1000);
				if(second==0){second=59; minute--;}
				else second--;
				handler.sendEmptyMessage(0x00);
	    		
	    	}catch (Exception ef) {
				ef.printStackTrace();
				}
	    	}
            
	    	handler.sendEmptyMessage(0x01);
	    	
	    	 
	    }
	}
    
    private void initButton(){
    	trueCount=0;
		falseCount=0;
    	for(int i=0;i<cols*rows;i++){
    		bt[i].setText(buttonString(target));
            bt[i].setLayoutParams(new TableRow.LayoutParams(W/cols,W/cols));
            bt[i].setPadding(0, 0, 0, 0);
            bt[i].setOnClickListener(new ButtonClick());
            //bt[i].setBackgroundResource(R.drawable.button_background);
            bt[i].setBackgroundColor(0xFFFFFFFF);
            bt[i].setVisibility(View.VISIBLE);
            bt[i].setTag(i);
    	}
    }
    
    private void setInstruction(){
    	switch(level){
		case 1:
			instruction.setText("划去目标数字"+target+"      "+"剩余时间："+minute+":"+second+'\n');
			break;
		case 2:
			instruction.setText("划去"+target+"前一个数字                  "+"剩余时间："+minute+":"+second+'\n');
			break;
		case 3:
			instruction.setText("划去"+target+"之前的"+target2+"      "+"剩余时间："+minute+":"+second+'\n');
			break;
		
		}
    }
    
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
	    //生成文件夹之后，再生成文件，不然会出错
	    makeFilePath(filePath, fileName);
	    
	    String strFilePath = filePath+fileName;
	    // 每次写入时，都换行写
	    String strContent = strcontent ;
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
	int gainCountTarget(Button bt[]){
		int countTarget=0;
		for(int i=0;i<cols*rows;i++){
			if(judge(bt,i,level,target,target2)){
				countTarget++;
			}
		}
		return countTarget;
	}
	
	double gainScore(Button bt[]){
		int countTarget=0;
		countTarget=gainCountTarget(bt);
		double score=trueCount-0.5*(countTarget-trueCount);
		return score;
	}
	
	double gainMistakeRate(Button bt[]){
		int countTarget=0;
		countTarget=gainCountTarget(bt);
		double score=(falseCount+0.5*(countTarget-trueCount))/gainScore(bt);
		return score;
	}
    
    private final  Handler handler = new Handler() {
        @Override
        
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        	case 0x00:
        		setInstruction();
        		break;
        	case 0x01:
        		java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");
				writeTxtToFile("L"+level+'C'+gainCountTarget(bt)+'R'+trueCount+'W'+falseCount+"S"+gainScore(bt)+'M'+df.format(gainMistakeRate(bt)),filePath,fileName2);
				if(level==3){
					ifEnd=true;
				}
        		if(level<3){
        			level++;
        			target=(int)(Math.random()*10);
        			if(level==3){
        				target2=target;
        				while(target2==target)
        				target2=(int)(Math.random()*10);
        			}
        			setInstruction();
        		    initButton();
        		    scrollView.scrollTo(10, 10);
        		    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);  
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);  
                    r.play();
                    Toast.makeText(getApplicationContext(), "Next Chapter", Toast.LENGTH_SHORT).show();          
        		    new Thread(new Operation()).start();
        		}      		
        		break;
        	}
        }
    };
    

	
	
	
	
}
