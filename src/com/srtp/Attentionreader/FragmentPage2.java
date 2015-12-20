package com.srtp.Attentionreader;
import java.io.File;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import android.R.string;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.srtp.Attentionreader.extra.showPieData;
import com.srtp.Attentionreader.extra.showdata;
import com.srtp.Attentionreader.extra.sqlitreader;


public class FragmentPage2 extends Fragment {
	private SQLiteDatabase db;
	private ImageButton btn1;
	private ImageButton btn2;
	private ImageButton btn3;
	private String tablename[];
	private List<String> tablename2 = new ArrayList<String>();
	private String targetsql;
	private String tabledata[][];
	private sqlitreader sqlor;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private String path;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        String fileName = "mind";
		path = Environment.getExternalStorageDirectory().getAbsolutePath();
		path += "/" + "AttReader";
		File destDir = new File(path);
		  if (!destDir.exists()) {
		   destDir.mkdirs();//create new folder
		  }
		  path+="/" +fileName;
		if (!path.endsWith(".sqlite"))
				path += ".sqlite";
		
		
		adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.preference_category, tablename2);
		//adapter.setDropDownViewResource(android.R.layout.);
		
    }
    
    
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("exp","ok1");
		View v =  inflater.inflate(R.layout.fragment_2, null);	
		
		
		sqlor =new sqlitreader(path,getActivity().getApplicationContext());
		tablename =sqlor.getTables();
		tablename2.clear();
		
		
		btn1 =(ImageButton)v.findViewById(R.id.imageButtonSignal);
		btn2 =(ImageButton)v.findViewById(R.id.imageButtonSometimes);
		btn3 =(ImageButton)v.findViewById(R.id.imageButtonAll);
		btn1.setOnClickListener(new LinearClick());
		btn2.setOnClickListener(new PieClick());
		
		
		for(int i=0;i<tablename.length;i++)
		{
			Log.e("name", tablename[i]);
			if(tablename[i]!="sqlite_master" && !tablename[i].equals("android_metadata") && !tablename[i].equals("mindwave"))
			{	
				Log.e("name", String.valueOf(tablename[i].length()));
				tablename2.add(tablename[i]);}
		}
		
		
		spinner = (Spinner)v.findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				targetsql = tablename2.get(position);
				parent.setVisibility(view.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		
			
		});
		return v;
	}
	private class LinearClick implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			tabledata = sqlor.getTableData(targetsql, 0, 25600, true);//limit 25600 data
				
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
	
	private class PieClick implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			tabledata = sqlor.getTableData(targetsql, 0, 25600, true);//limit 25600 data
				
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
			
			intent = new Intent(getActivity(), showPieData.class);
			intent.putExtras(myBundle);
            startActivity(intent);}}
			else 
			{
				Toast.makeText(getActivity().getApplicationContext(), "no data", 1000).show();
			}
		}
		
	}
	
	
	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	    
	    inflater.inflate(R.menu.menu, menu);    	
		
	    super.onCreateOptionsMenu(menu,inflater);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.Share:
	        	shareIt();
	            return true;
	        case R.id.help:
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void shareIt() {
		// TODO Auto-generated method stub
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		String kString = "";
		if(tabledata.length>0)
		{
		StringBuffer aaString = new StringBuffer();
		String aString[] = getcol(tabledata, 0);
		for (String next:aString)
		{
			aaString.append(next);
			aaString.append(",");
		}
		kString = aaString.toString();
		}
		shareIntent.putExtra(Intent.EXTRA_TEXT,kString);
		startActivity(shareIntent);
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
  