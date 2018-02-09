package com.srtp.Attentionreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentPage3 extends Fragment{
	public Button  ButtonGame1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		View v= inflater.inflate(R.layout.fragment_3, null);
		ButtonGame1=(Button)v.findViewById(R.id.buttonGame1);
    	try {
			ButtonGame1.setOnClickListener(new Game1OnClickListener());

		} catch (RuntimeException e) {
			
		}
    	
    	
    	
		
		
		return v;
	}	
	
	private class Game1OnClickListener implements View.OnClickListener{

   		@Override
   		public void onClick(View v) {
   			
   			
   			Intent intent = new Intent();
            intent.setClass(getActivity(), GameSchulte.class);
            startActivity(intent);  
   			// TODO Auto-generated method stub
   			
   		}
   	}
}