package com.srtp.Attentionreader.extra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.srtp.Attentionreader.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class DeleteData extends Activity{
    private SwipeListView mSwipeListView;
    private DataAdapter mAdapter;

    private String tablename[];
    private List<String> tablename2 = new ArrayList<String>();
    private String targetsql;
    private String tabledata[][];
    private sqlitreader sqlor;


@Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	Log.e("swipe","aaa");
	setContentView(R.layout.delete_page);
	Log.e("swipe","bbb");
	String fileName = "mind";
    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    path += "/" + "AttReader";
    File destDir = new File(path);
    if (!destDir.exists()) {
        destDir.mkdirs();//create new folder
     }
    path+="/" +fileName;
    if (!path.endsWith(".sqlite"))
          path += ".sqlite";
    sqlor =new sqlitreader(path,this.getApplicationContext());
    tablename =sqlor.getTables();
    initDatas();
    mSwipeListView = (SwipeListView)findViewById(R.id.example_ztc_list);
    mAdapter = new DataAdapter(this,tablename2,mSwipeListView,sqlor);
    mSwipeListView.setAdapter(mAdapter);
    mSwipeListView.setSwipeListViewListener(new BaseSwipeListViewListener()
        {
            public void onListChanged()
            {

                mSwipeListView.closeOpenedItems();
            }
        });
    }
    private void initDatas()
    {
        for(int i=0;i<tablename.length;i++)
        {
            //Log.e("name", tablename[i]);
            if(tablename[i]!="sqlite_master" && !tablename[i].equals("android_metadata") && !tablename[i].equals("mindwave"))
            {
              //  Log.e("name", String.valueOf(tablename[i].length()));
                tablename2.add(tablename[i]);}
        }

    }
}
