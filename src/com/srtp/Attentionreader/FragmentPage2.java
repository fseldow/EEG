package com.srtp.Attentionreader;
import java.io.File;
import java.security.PrivateKey;
import java.util.ArrayList;

import javax.security.auth.PrivateCredentialPermission;

import android.R.string;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.srtp.Attentionreader.extra.showdata;
import com.srtp.Attentionreader.extra.sqlitreader;


public class FragmentPage2 extends Fragment {
	private SQLiteDatabase db;
	private ImageButton btn1;
	private ImageButton btn2;
	private ImageButton btn3;
	private String tablename[];
	private String tabledata[][];
	private sqlitreader sqlor;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       String fileName = "mind";
		String path = Environment.getExternalStorageDirectory().getAbsolutePath();
		path += "/" + "HelloEEG";
		File destDir = new File(path);
		  if (!destDir.exists()) {
		   destDir.mkdirs();//创建文件夹
		  }
		  path+="/" +fileName;
		if (!path.endsWith(".sqlite"))
				path += ".sqlite";
		sqlor =new sqlitreader(path,getActivity().getApplicationContext());
		tablename =sqlor.getTables();
		
		
    }
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("exp","ok1");
		View v =  inflater.inflate(R.layout.fragment_2, null);	
		btn1 =(ImageButton)v.findViewById(R.id.imageButtonSignal);
		btn2 =(ImageButton)v.findViewById(R.id.imageButtonSometimes);
		btn3 =(ImageButton)v.findViewById(R.id.imageButtonAll);
		btn1.setOnClickListener(new myonclicklisten());
		return v;
	}
	private class myonclicklisten implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
				tabledata = sqlor.getTableData(tablename[2], 0, 25600, true);//limit 25600 data
				Log.e("exp","q");
			if(tabledata.length>0)
			{
			Log.e("exp", tabledata[0][0]);
			Bundle myBundle = new Bundle();
			String aString[] = getcol(tabledata, 0);
			String bString[] = getcol(tabledata, 1);
			if (aString.length>1&&bString.length>1)
			{myBundle.putStringArray("attdata", aString);
			myBundle.putStringArray("meddata", bString);
			
 			//String aString[] = {"1","2","4","5","6","7","8","9","10"};
 			//String bString[] = {"11","22","34","35","36","37","38","39","40"};
 			//myBundle.putStringArray("attdata", bString);
			//myBundle.putStringArray("meddata", aString);
			Intent intent;
			
			intent = new Intent(getActivity(), showdata.class);
			intent.putExtras(myBundle);
            startActivity(intent);}}
			else 
			{
				Toast.makeText(getActivity().getApplicationContext(), "no data", 1000).show();
			}
		}
		
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
}
  