package com.srtp.Attentionreader.extra;

import java.util.List;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.srtp.Attentionreader.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter  
{
	private List<String> mDatas;
	private LayoutInflater mInflater;
	private SwipeListView mSwipeLisView;
	private sqlitreader mSqlitreader;
	public DataAdapter(Context context, List<String> datas , SwipeListView swipeListView,sqlitreader sqlitreader) {
		this.mDatas = datas;
		this.mSqlitreader = sqlitreader;
		mInflater = LayoutInflater.from(context);
		mSwipeLisView = swipeListView;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = mInflater.inflate(R.layout.swipe_item, null);
		TextView tView = (TextView) convertView.findViewById(R.id.id_text) ;
		Button delButton = (Button) convertView.findViewById(R.id.id_remove);
		tView.setText(mDatas.get(position));
		delButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				mSqlitreader.dropTables(mDatas.get(position));
				mDatas.remove(position);
				
				notifyDataSetChanged();
				
				mSwipeLisView.closeOpenedItems();
			}
		});
	return convertView;	
	}

}
