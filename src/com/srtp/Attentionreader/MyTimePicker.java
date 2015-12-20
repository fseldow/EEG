package com.srtp.Attentionreader;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class MyTimePicker extends LinearLayout {
	
	private  MyNumberPicker HourSpinner;
    private  MyNumberPicker MinuteSpinner;
    public   String          []minute={"00","05","10","15","20","25","30","35","40","45","50","55"};
    



     
    public MyTimePicker(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public MyTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_timepicker, this);
        HourSpinner=( MyNumberPicker)findViewById(R.id.numberPicker1);
        MinuteSpinner=( MyNumberPicker)findViewById(R.id.numberPicker2);
        MinuteSpinner.setDisplayedValues(minute);
        HourSpinner.setMaxValue(2);
        HourSpinner.setMinValue(0);
        MinuteSpinner.setMaxValue(minute.length - 1);
        MinuteSpinner.setMinValue(0);
        MinuteSpinner.setValue(20);
        
        MinuteSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
            	if(oldVal==11&&newVal==0){
            	if(HourSpinner.getValue()<2)
            	HourSpinner.setValue(HourSpinner.getValue()+1);
            	else
            		spinner.setValue(11);
            	}
            	if(HourSpinner.getValue()==0&&oldVal==1&&newVal==0)spinner.setValue(1);
            	if(oldVal==0&&newVal==11){
            			HourSpinner.setValue(HourSpinner.getValue()-1);
            	}
            	
            }
            	
            	
            
        });
        
        }
        
        
        
        
    
    
     
    public int getHour(){
    	return HourSpinner.getValue();
    }
    
    public int getMinute(){
    	return MinuteSpinner.getValue()*10;
    }
    
    
   
    
    
    
 
}
