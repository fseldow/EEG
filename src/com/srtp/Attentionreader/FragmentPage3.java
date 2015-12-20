package com.srtp.Attentionreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentPage3 extends Fragment{
	Button ButtonGame [] = new Button[5];
	Button ButtonBackground;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		View v= inflater.inflate(R.layout.fragment_3, null);
		
		

		
		ButtonGame[0]=(Button)v.findViewById(R.id.button1);
		ButtonGame[1]=(Button)v.findViewById(R.id.button2);
		ButtonGame[2]=(Button)v.findViewById(R.id.button3);
		ButtonGame[3]=(Button)v.findViewById(R.id.button4);
		ButtonGame[4]=(Button)v.findViewById(R.id.button5);
		ButtonBackground = (Button)v.findViewById(R.id.buttonback);
		
		for(int i=0;i<5;i++){
			ButtonGame[i].setBackgroundColor(0x00000000);
			ButtonGame[i].setHeight(50);
			ButtonGame[i].setWidth(50);
			//ButtonGame[i].setBackgroundColor(0x00000000);
			try {  		
				ButtonGame[i].setOnClickListener(new GameOnClickListener());
			} catch (RuntimeException e) {
			}
		}
		
		return v;
	}	
	
	private class GameOnClickListener implements View.OnClickListener{

   		@Override
   		public void onClick(View v) {
   			Intent intent = new Intent();
   			switch(v.getId()){
   			case R.id.button1:
   				intent.setClass(getActivity(), com.games.GameSchulte.class);
   				break;
   			case R.id.button2:
   				intent.setClass(getActivity(), com.games.GameTable.class);
   				break;
   			case R.id.button3:
   				intent.setClass(getActivity(), com.games.GameStare.class);
   				break;
   			case R.id.button4:
   				intent.setClass(getActivity(), com.games.GameWaterDrop.class);
   				break;
   			case R.id.button5:
   				intent.setClass(getActivity(), com.games.GameNumber.class);
   				break;
   			
   			
   			}
   			
            startActivity(intent);  
   			// TODO Auto-generated method stub
   			
   		}
   	}
}