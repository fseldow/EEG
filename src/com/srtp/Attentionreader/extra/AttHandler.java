package com.srtp.Attentionreader.extra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * @author fseldow
 * @version 1.0
 * @import sqlitreader
 * @description handle attention data in sqlit file
 */

public class AttHandler {
	private sqlitreader sqlor;
	private String tableName[];
	
	public AttHandler() {
		super();
		this.sqlor = null;
		this.tableName =null ;
	}
	/**
	 * @param sqlitreader
	 */
	public AttHandler(sqlitreader sqlor) {
		super();
		this.sqlor = sqlor;
		this.tableName =sqlor.getTables(); ;
	}
	/**
	 * @return the SqlitReader
	 */
	public sqlitreader getSqlor() {
		return sqlor;
	}
	/**
	 * @param SqlitReader
	 */
	public void readSql(sqlitreader s){
		this.sqlor=s;
		this.tableName=sqlor.getTables();
	}
	/**
	 * @param filePath
	 * @param fileName
	 * @param context
	 * @description load the sqlit file from a local path
	 */
	public void readSql(String filePath, String fileName,Context context){
		String path;
		path=filePath;
		File destDir = new File(path);
		if (!destDir.exists()) {
		   destDir.mkdirs();//create new folder
		}
		path+="/" +fileName;
		if (!path.endsWith(".sqlite"))
				path += ".sqlite";
		Log.i("Path",path);
		
		try{
		sqlor =new sqlitreader(path,context);
		Log.i("sqlReader","success");
		tableName =sqlor.getTables();
		Log.e("tableName",tableName[3]);
		}catch (Exception e) {
			Log.e("sqlReader","fail");
		}
	}
	/**
	 * @parameters sqlName 
	 * @description delete certain sqlit sheet
	 */
	public void deleteSql(String sheet){
		
	}
	/**
	 * @param year
	 * @exception getDataYearError
	 * @return a group of scores during a year, interval in month
	 */
    public float [] getDataYear(int year){
    	if(year<1990||year>2100){Log.e("getDataYearError","Out of Year extent");return null;}
    	else{
    	if(sqlor==null||tableName==null){Log.e("getDataYearError","no sql");return null;}
    	else{
    	float []data=new float [12];
    	for(int i=1;i<13;i++){
    		data[i-1]=returnGroup(dataClassify(year,i));
    	}
    	return data;
    	}
    	}
	}
    /**
	 * @param 
	 * @exception getDataMonthError
	 * @return calculate a group of scores during a month, interval in day
	 */
    public float [] getDataMonth(int year,int month){
    	if(year<1990||year>2100){Log.e("getDataMonthError","Out of Year extent");return null;}
    	else if(month<1||month>12){Log.e("getDataMonthError","Error Month");return null;}
    	else{
    	if(sqlor==null||tableName==null){Log.e("getDataMonthError","no sql");return null;}
    	
    	else{
		float []data;
		int b=0;
		if(month==2&&year%4==0)b=29;
		else if(month==2&&year%4>0)b=28;
		else if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)b=31;
		else b=30;
		Log.e("b",b+"");
		data=new float[b];
    	for(int i=1;i<b+1;i++){
    		data[i-1]=returnGroup(dataClassify(year,month,i));
    	}
    	return data;
    	}
    	}
	}
    
    /**
	 * @param 
	 * @exception getDataDateError
	 * @return calculate a group of scores during a day, interval in hour
	 */
	public float [] getDataDay(int year,int month,int date){
		if(year<1990||year>2100){Log.e("getDataDayError","Out of Year extent");return null;}
    	else if(month<1||month>12){Log.e("getDataDayError","Error Month");return null;}
    	else if(date<0||date>31){Log.e("getDataDayError","Error date");return null;}
    	else if(sqlor==null||tableName==null){Log.e("getDataDayError","no sql");return null;}
    	
    	else{
		float []data=new float [24];
    	for(int i=1;i<25;i++){
    		data[i-1]=returnGroup(dataClassify(year,month,date,i));
    	}
    	return data;
    	}
    	
	}
	
	private String[] dataClassify(int year,int month){
		String []list;
		List<String> temp = new ArrayList<String>();
		for(int i=0;i<tableName.length;i++)
		{
			if(tableName[i]!="sqlite_master" && !tableName[i].equals("android_metadata") && !tableName[i].equals("mindwave"))
			{	
				if(tableName[i].contains(year+"_"+month+"_")){
					temp.add(tableName[i]);
				}
				else{
				if(temp.size()>0)break;
				}
			}
		}
		list=new String[temp.size()]; 
		for(int i=0;i<temp.size();i++){ 
		      list[i]=(String)temp.get(i); 
		} 
		return list;
	}
	private String[] dataClassify(int year,int month,int day){
		String []list;
		List<String> temp = new ArrayList<String>();
		for(int i=0;i<tableName.length;i++)
		{
			if(tableName[i]!="sqlite_master" && !tableName[i].equals("android_metadata") && !tableName[i].equals("mindwave"))
			{	
				if(tableName[i].contains(year+"_"+month+"_"+day+"_")){
					temp.add(tableName[i]);
				}
				else{
				if(temp.size()>0)break;
				}
			}
		}
		list=new String[temp.size()]; 
		for(int i=0;i<temp.size();i++){ 
		      list[i]=(String)temp.get(i); 
		} 
		return list;
	}
	private String[] dataClassify(int year,int month,int day,int hour){
		String []list;
		List<String> temp = new ArrayList<String>();
		for(int i=0;i<tableName.length;i++)
		{
			if(tableName[i]!="sqlite_master" && !tableName[i].equals("android_metadata") && !tableName[i].equals("mindwave"))
			{	
				if(tableName[i].contains(year+"_"+month+"_"+day+"_"+hour+"_")){
					temp.add(tableName[i]);
				}
				else{
				if(temp.size()>0)break;
				}
			}
		}
		list=new String[temp.size()]; 
		for(int i=0;i<temp.size();i++){ 
		      list[i]=(String)temp.get(i); 
		} 
		return list;
	}
	
	private float calculateStandard(String sheet){
		float s=0;
		String aString[];
		String [][]tabledata = sqlor.getTableData(sheet, 0, 25600, true);
		if(tabledata.length>0)
		{
		aString = getcol(tabledata, 0);
		int l=0;
		for(int i=0;i<aString.length;i++){
			if(aString[i]!="0"){
				try{
			          float temp=Float.parseFloat(aString[i]);
			          s+=temp;
			          l++;
				}catch(Exception e){
					Log.e("dataRead","FormatError");
				}
			}
		}
		return s/l;
		}
		else
			return 0;
	}
	private float returnGroup(String[]table){
		float s=0;
		if(table.length>0&&table!=null){
		for(int i=0;i<table.length;i++){
			s+=calculateStandard(table[i]);
		}
		return s/table.length;
		}
		else return 0;
	}
	
	
	
	private String[] getcol(String a[][],int col) {
		ArrayList<String> b = new ArrayList<String>();
		
		for (int i=0;i<a.length;i++)
		{
			b.add(a[i][col]);
		}
		String s[]=new String[b.size()];
		for (int i=0;i<a.length;i++)
		{
			s[i]=b.get(i);
		}
		return s;
	}
};
