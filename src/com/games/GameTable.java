package com.games;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.srtp.Attentionreader.FragmentPage1;
import com.srtp.Attentionreader.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameTable extends Activity {
	private int ON;
	private int VN;
	private int Chapter=1;
	private int TimeLimit;
	private int Type;
	private DisplayMetrics dm;
	private int W;
	private int H;
	int BH;
	int TH;
	int TW;
	int Pick;
	
	int startT;
	int endT;
	String CT;
	int GameType;
	long ct0;
	long ct1;
	long ct;
	long score=0;
	String filePath ;
    String fileName;
    
    
	Boolean Game=false;
	TextView tvD;
	TextView tvT;
	TextView tvC;
	RelativeLayout layout;
	Button B[];
	Button Continue;
	Button Exit;
	Thread time;
	int BT[];
	int BP[];
	List<String> Nums = Arrays.asList("0","1","2","3","4","5","6","7","8","9");
	List<String> Alpha= Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
	List<String> Alphabets= Arrays.asList("A","B","C","D","E","F","G","H","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
	List<String> Character= Arrays.asList( "¼Î","Çí","¹ð","æ·","Ò¶","èµ","è´","æ«","çù","¾§","åû","Üç","Çï","Éº","É¯","½õ","÷ì","Çà","Ù»","æÃ","æ¯","Íñ","æµ","èª","Ó±","Â¶","Ñþ","âù","æ¿","Ñã","Ýí","æý","ÒÇ","ºÉ","µ¤","ÈØ","Ã¼","¾ý","ÇÙ","Èï","Þ±","Ý¼","ÃÎ","á°","Ô·","æ¼","Ü°","è¥","çü","ÔÏ","ÈÚ","Ô°","ÒÕ","Ó½","Çä","´Ï","À½","´¿","Ø¹","ÔÃ","ÕÑ","±ù","Ë¬","çþ","Üø","Óð","Ï£","Äþ","ÐÀ","Æ®","Óý","äÞ","ð¥","óÞ","Èá","Öñ","ö°","Äý","Ïþ","»¶","Ïö","·ã","Ü¿","·Æ","º®","ÒÁ","ÑÇ","ÒË","¿É","¼§","Êæ","Ó°","Àó","Ö¦","Ë¼","Àö","Ðã","¾ê","Ó¢","»ª","»Û","ÇÉ","ÃÀ","ÄÈ","¾²","Êç","»Ý","Öé","´ä","ÑÅ","Ö¥","Óñ","Æ¼","ºì","¶ð","Áá","·Ò","·¼","Ñà","²Ê","´º","¾Õ","ÇÚ","Õä","Õê","Àò","À¼","·ï","½à","Ã·","ÁÕ","ËØ","ÔÆ","Á«","Õæ","»·","Ñ©","ÈÙ","°®","ÃÃ","Ï¼","Ïã","ÔÂ","Ýº","æÂ","ÑÞ","Èð","·²","¼Ñ");
	List<String> Roma= Arrays.asList("¦Á","¦Â","¦Ã","¦Ä","¦Å","¦Æ","¦Ç","¦È");
	List<String> ChoiceType= Arrays.asList("Êý×Ö","Ð¡Ð´×ÖÄ¸","´óÐ´×ÖÄ¸","ºº×Ö","Ï£À°×ÖÄ¸");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dm = new DisplayMetrics(); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		FragmentPage1.instance=this;
		getWindowManager().getDefaultDisplay().getMetrics(dm); 
		W=dm.widthPixels;
		H=dm.heightPixels;
		TH=(int)(H*0.8);
		TW=(int)(W*0.6);
		long ti=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();  
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm");  
        Date d1=new Date(ti);  
        String t1=format.format(d1);  
		fileName= "Table "+t1+".txt";
		filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
		filePath+="/AttReader/";
		writeTxtToFile("score:", filePath, fileName);
		Porperties();
		CreateGameView();
		
	}






	
	private final Handler handler = new Handler() {
        @Override
        
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        	case 0x01:
        		Porperties();
        		CreateGameView();
        		endT=FragmentPage1.count;
        		break;
        	case 0x02:
        		for(int i=0;i<VN*ON;i++){
                	B[i].setVisibility(View.INVISIBLE);
                }
        		tvT.setVisibility(View.INVISIBLE);
        		tvD.setVisibility(View.INVISIBLE);
        		RelativeLayout.LayoutParams paramTV= new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        		paramTV.setMargins(W/2-TW/4, H/2-TH/2, W/2-TW/4, H/2-TH/4);
        		tvC.setVisibility(View.VISIBLE);
        		tvC.setLayoutParams(paramTV);
        		tvC.setText("Ö¹²½µÚ"+Chapter+"¹Ø");
        		tvC.setTextColor(0xffff0000);
        		tvC.setGravity(Gravity.CENTER);
        		tvC.setBackgroundColor(0x00000000);
        		tvC.setTextSize(30);
        		Continue.setText("ÖØÐÂÀ´¹ý");
        		Continue.setVisibility(View.VISIBLE);
        		Exit.setText("ÍË³ö");
        		Exit.setVisibility(View.VISIBLE); 
        		break;
        	
        	}
        }
	};
	

	 
	// ½«×Ö·û´®Ð´Èëµ½ÎÄ±¾ÎÄ¼þÖÐ
	public void writeTxtToFile(String strcontent, String filePath, String fileName) {
	    //Éú³ÉÎÄ¼þ¼ÐÖ®ºó£¬ÔÙÉú³ÉÎÄ¼þ£¬²»È»»á³ö´í
	    makeFilePath(filePath, fileName);
	    
	    String strFilePath = filePath+fileName;
	    // Ã¿´ÎÐ´ÈëÊ±£¬¶¼»»ÐÐÐ´
	    String strContent = strcontent +" "+startT+" "+endT+ "\r\n";
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
	 
	// Éú³ÉÎÄ¼þ
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
	 
	// Éú³ÉÎÄ¼þ¼Ð
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
	
	private void Porperties(){
		ON=Chapter/3+4;
		if(ON>6)ON=6;
		VN=(Chapter+1)/3+4;
		if(VN>7)VN=7;
		TimeLimit=20-Chapter/2;
		if(TimeLimit<7)TimeLimit=7;
		
	}
	
	private int Distance(int a,int b){
		int d;
		int ol1=a/VN;
		int vl1=a%VN;
		int ol2=b/VN;
		int vl2=b%VN;
		d=Math.abs(ol1-ol2)+Math.abs(vl1-vl2);
		return d;
	}
	
	private int GetMax(int [] a){
		int d=a[0];
		for(int i=0;i<a.length;i++){
			if(a[i]>d)d=a[i];
		}
		return d;
	}
	
	private int GetMin(int [] a){
		int d=100;
		for(int i=0;i<a.length;i++){
			if(a[i]<d&&a[i]!=0)d=a[i];
		}
		return d;
	}
	
	private void CreateGameView(){
		
		if(FragmentPage1.experiment==true){
			endT=FragmentPage1.count;
			writeTxtToFile(score+" ", filePath, fileName);
		}
		Game=false;
		int Flag []=new int [5];
		for(int i=0;i<5;i++){
			Flag[i]=1;
		}
		
		ArrayList<String> Num1 = new ArrayList<String>();
		Num1.addAll(Nums);
		ArrayList<String> Alpha1 = new ArrayList<String>();
		Alpha1.addAll(Alpha);
		ArrayList<String> Alphabets1 = new ArrayList<String>();
		Alphabets1.addAll(Alphabets);
		ArrayList<String> Character1 = new ArrayList<String>();
		Character1.addAll(Character);
		ArrayList<String> Roman1 = new ArrayList<String>();
		Roman1.addAll(Roma);
		ArrayList<String> ChoiceType1 = new ArrayList<String>();
		ChoiceType1.addAll(ChoiceType);
		ArrayList<Integer> Emp = new ArrayList<Integer>();
		
		Emp.add(1);
		Emp.add(2);
		Emp.add(3);
		Emp.add(4);
		Emp.add(5);
		
		///////////////////////////////////////////////////////////////
		layout = new RelativeLayout(this);
		layout.setBackgroundResource(R.drawable.gb);
        layout.setPadding(0, 0, 0, 0);
		layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		Continue=new Button(this);
		Exit=new Button(this);
		Continue.setVisibility(View.GONE);
		Exit.setVisibility(View.GONE);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params1.setMargins(W/2-W/4, H/2, 0, 0);
		params2.setMargins(W/2+W/5, H/2, 0, 0);
		Continue.setLayoutParams(params1);
		Exit.setLayoutParams(params2);
		try {
    		Continue.setOnClickListener(new ContinueClick());
    		Exit.setOnClickListener(new ExitClick());
		} catch (RuntimeException e) {
			Log.e("exp", e.toString());
		}
		layout.addView(Continue);
		layout.addView(Exit);
		
		int OL=(int)(0.382*W);
		BH=H/(ON+2);
		RelativeLayout.LayoutParams params[] = new RelativeLayout.LayoutParams[ON*VN];
		B = new Button[ON*VN];
		BT = new int[ON*VN];
		BP = new int[ON*VN];
		for(int i=0;i<ON;i++){
			for(int j=0;j<VN;j++){
				B[i*VN+j]=new Button(this);
				params[i*VN+j]=new RelativeLayout.LayoutParams(BH,BH);
				params[i*VN+j].setMargins(OL-BH*VN/2+BH*j, BH*i+BH/3, 0, 0);
				B[i*VN+j].setLayoutParams(params[i*VN+j]);
				B[i*VN+j].setBackgroundResource(R.drawable.gamef_bb1);
				layout.addView(B[i*VN+j]);
				int Ord []=new int [4];
				for(int k=0;k<4;k++){
					Ord[k]=0;
					for(int m=0;m<k+1;m++){
						Ord[k]=Ord[k]+Flag[m];
					}
				}
				
				if(Num1.size()==0&&Flag[0]==1){Emp.remove(0);Flag[0]=0;}
				if(Alpha1.size()==0&&Flag[1]==1){Emp.remove(Ord[0]);Flag[1]=0;}
				if(Alphabets1.size()==0&&Flag[2]==1){Emp.remove(Ord[1]);Flag[2]=0;}
				if(Character1.size()==0&&Flag[3]==1){Emp.remove(Ord[2]);Flag[3]=0;}
				if(Roman1.size()==0&&Flag[4]==1){Emp.remove(Ord[3]);Flag[4]=0;}
				
				
				switch(Emp.get((int) (Math.random() * Emp.size()))){
				case 1:
					if(Num1.size()!=0){
					Type=(int) (Math.random() * Num1.size());
					B[i*VN+j].setText(Num1.get(Type));
					BT[i*VN+j]=0;
					Num1.remove(Type);
					}
					break;
				case 2:
					if(Alpha1.size()!=0){
					Type=(int) (Math.random() * Alpha1.size());
					B[i*VN+j].setText(Alpha1.get(Type));
					BT[i*VN+j]=1;
					Alpha1.remove(Type);
					}
					break;		
				case 3:
					if(Alphabets1.size()!=0){
					Type=(int) (Math.random() * Alphabets1.size());
					B[i*VN+j].setText(Alphabets1.get(Type));
					BT[i*VN+j]=2;
					Alphabets1.remove(Type);
					}
					break;
				case 4:
					if(Character1.size()!=0){
					Type=(int) (Math.random() * Character1.size());
					B[i*VN+j].setText(Character1.get(Type));
					BT[i*VN+j]=3;
					Character1.remove(Type);
					}
					break;
				case 5:
					if(Roman1.size()!=0){
					Type=(int) (Math.random() * Roman1.size());
					B[i*VN+j].setText(Roman1.get(Type));
					BT[i*VN+j]=4;
					Roman1.remove(Type);
					}
					break;
				}
				B[i*VN+j].setVisibility(View.INVISIBLE);
				try {
					
		    		B[i*VN+j].setOnClickListener(new ButtonClick());
				} catch (RuntimeException e) {
					Log.e("exp", e.toString());
				}

			}
		}
		
		tvD=new TextView(this);
		RelativeLayout.LayoutParams paramTV= new RelativeLayout.LayoutParams(TW,TH);
		paramTV.setMargins(W/2-TW/2, H/2-TH/2, W/2-TW/2, H/2-TH/2);
		tvD.setLayoutParams(paramTV);
		tvD.setGravity(Gravity.CENTER);
		tvD.setVisibility(View.VISIBLE);
		
		layout.addView(tvD);
		tvD.setOnTouchListener(new Touch());
		tvD.setBackgroundColor(0xFFFFFF00);
		
		GameType=(int) (Math.random() * 2);
		
		if(Roman1.size()>=7)ChoiceType1.remove(4);
		if(Character1.size()>=10)ChoiceType1.remove(3);
		if(Alphabets1.size()>=24)ChoiceType1.remove(2);
		if(Alpha1.size()>=25)ChoiceType1.remove(1);
		if(Num1.size()>=9)ChoiceType1.remove(0);
		
		
		CT=ChoiceType1.get((int)(Math.random()*(ChoiceType1.size()-1)));
		switch(GameType){
		case 0:
			Pick=(int) (Math.random() * (ON*VN));
			
			for(int i=0;i<ON*VN;i++){
				if(ChoiceType.get(BT[i])==CT){
					BP[i]=Distance(Pick,i);
				}
				else BP[i]=0;
			}
			
			
			
			if(Chapter<6){
				int min=GetMin(BP);
				tvD.setText("ÇëÕÒµ½ËùÓÐÀë¡°"+B[Pick].getText()+"¡±×î½üµÄ"+CT);
				for(int i=0;i<ON*VN;i++){
					
					if(BP[i]==min){BP[i]=2;}
					else BP[i]=0;
				}	
			}
			else { 
				tvD.setText("ÇëÕÒµ½ËùÓÐÀë¡°"+B[Pick].getText()+"¡±×îÔ¶µÄ"+CT);
				tvD.setTextColor(0xFF0000FF);
				int max=GetMax(BP);
				for(int i=0;i<ON*VN;i++){
					if(BP[i]==max){BP[i]=2;}
					else BP[i]=0;
				}	
			}
			break;
		case 1:
			tvD.setText("ÇëÕÒ³öËùÓÐµÄ"+CT);
			for(int i=0;i<ON*VN;i++){
				if(ChoiceType.get(BT[i])==CT){BP[i]=2;}
				else BP[i]=0;
			}	
			break;
		}
		
		
		tvT=new TextView(this);
		layout.addView(tvT);
		
		tvC=new TextView(this);
		layout.addView(tvC);
		tvC.setVisibility(View.INVISIBLE);

		setContentView(layout);
	}
	 
	
	public class Touch implements View.OnTouchListener {     
	  	  @Override    
	  	  public boolean onTouch(View v, MotionEvent event) {     
	  	   if(event.getAction() == MotionEvent.ACTION_UP &&Game==false){
	  		 Animation mScaleAnimation = new ScaleAnimation(1f, 0.3f, 1f,0.5f, 
	 				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
	         mScaleAnimation.setDuration(2000);

	         Animation mTranslateAnimation = new TranslateAnimation(0, W/3+W/4, 0, -BH/6);
	         mTranslateAnimation.setDuration(2000);
	         
	         
	         AnimationSet mAnimationSet=new AnimationSet(true);
	         mAnimationSet.addAnimation(mScaleAnimation);
	         mAnimationSet.addAnimation(mTranslateAnimation);
	         mAnimationSet.setAnimationListener(new DeleteAnimationListener());
	         v.startAnimation(mAnimationSet);
	    
	         Game=true;
	         
	      
	  		 
	  	    }     
	  	   return true;     
	  	       
	  	 }
	}
	private class DeleteAnimationListener implements AnimationListener{ 
		 
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            if(GameType==0){
            	if(Chapter<6){
            		tvD.setText(B[Pick].getText()+"\n"+"×î½ü\n"+CT);
            	}
            	else {tvD.setText(B[Pick].getText()+"\n"+"×îÔ¶\n"+CT);}
            }
            else {tvD.setText("È«²¿\n"+CT);}
            RelativeLayout.LayoutParams paramsTV=new RelativeLayout.LayoutParams(3*TW/10,TH/3);
            paramsTV.setMargins(W/2-TW/2+W/3+W/4, H/2-TH/2-BH/6, 0, 0);
            tvD.setLayoutParams(paramsTV);
            for(int i=0;i<VN*ON;i++){
            	B[i].setVisibility(View.VISIBLE);
            }
            startT=FragmentPage1.count;
            
            RelativeLayout.LayoutParams paramTVT= new RelativeLayout.LayoutParams(3*TW/10,2*H/5);
	 		paramTVT.setMargins(W/2-TW/2+W/3+W/4, H/2-TH/6-BH/6, 0, 0);
	 		tvT.setLayoutParams(paramTVT);
	 		tvT.setVisibility(View.VISIBLE);
	 		tvT.setBackgroundColor(0xFF0000FF);
	         Animation mScaleAnimation2 = new ScaleAnimation(1f, 1f, 1f,0f, 
	  				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
	          mScaleAnimation2.setDuration(TimeLimit*1000);
	          tvT.setAnimation(mScaleAnimation2);
            time=new Thread(new RunningTime());
            time.start();
            ct0 = System.currentTimeMillis();
        }
 
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }
 
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

    }

	class RunningTime implements Runnable{
	    
	    @Override
	    public void run() {
	    	try {
				Thread.sleep(TimeLimit*1000);
				if(Game==true){
				Game=false;
				Chapter=1;
				if(FragmentPage1.experiment==true){
					handler.sendEmptyMessage(0x01);
   	        		}
   	        		else {
   	        			handler.sendEmptyMessage(0x02);
   	        		}
				
				} 
	    	}catch (Exception ef) {
				ef.printStackTrace();
				}
	    	
	    	 
	    }
	}
	
	private class ButtonClick implements View.OnClickListener{

   		@Override
   		public void onClick(View v) {
   			// TODO Auto-generated method stub
   			
   			int VL=(v.getLeft()-(int)(0.382*W)+BH*VN/2)/BH;
   			int OL=v.getTop()/BH-1/3;
   			Log.e("pre",BP[OL*VN+VL]+"");
   			if(BP[OL*VN+VL]==0||BP[OL*VN+VL]==2){
   				BP[OL*VN+VL]--;
   				v.setBackgroundResource(R.drawable.gamef_bb2);
   			}
   			else{
   				BP[OL*VN+VL]++;
   				v.setBackgroundResource(R.drawable.gamef_bb1);
   			}
   			Log.e("end",BP[OL*VN+VL]+"");
   			int i;
   			for( i=0;i<VN*ON;i++){
   				if(BP[i]==2||BP[i]==-1)break;
   			}
   			if (i==VN*ON){ 
   				
   					Game=false;
   					
   					
   					for(i=0;i<VN*ON;i++){
   	                	B[i].setVisibility(View.INVISIBLE);
   	                }
   	        		tvT.setVisibility(View.INVISIBLE);
   	        		tvT.clearAnimation();
   	        		tvD.setVisibility(View.INVISIBLE);
   	        		RelativeLayout.LayoutParams paramTV= new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
   	        		paramTV.setMargins(W/2-TW/4, H/2-TH/2, W/2-TW/4, H/2-TH/4);
   	        		tvC.setVisibility(View.VISIBLE);
   	        		tvC.setLayoutParams(paramTV);
   	        		tvC.setText("CONGRATULATIONS!");
   	        		tvC.setGravity(Gravity.CENTER);
   	        		tvC.setBackgroundColor(0x00000000);
   	        		tvC.setTextSize(20);
   	        		Continue.setText("ÏÂÒ»¹Ø");
   	        		Continue.setVisibility(View.VISIBLE);
   	        		Exit.setText("ÍË³ö");
   	        		Exit.setVisibility(View.VISIBLE);
   	        		time.interrupt();
   	        		ct1 = System.currentTimeMillis();
   	        		ct=(ct1-ct0)/1000;
   	        		if(GameType==0)
   	        		    score=score+(20-ct)*(long)Math.pow(1.7, Chapter);
   	        		else
   	        			score=score+(40-ct)*(long)Math.pow(1.7, Chapter);
   	        		
   	        		Chapter++;

   	        		Porperties();
   	    			CreateGameView();
   	        		
                
   	    			
        		
   			} 
   			
   		}
   		
   		
   			
   		
   	}
	
	private class ContinueClick implements View.OnClickListener{

   		@Override
   		public void onClick(View v) {
   			Porperties();
   			CreateGameView();
   		}		
   		
   	}
	
	private class ExitClick implements View.OnClickListener{

   		@Override
   		public void onClick(View v) {
   			finish();
   		}	
   		
   	}

}
