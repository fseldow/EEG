package com.srtp.Attentionreader.extra;

import com.srtp.Attentionreader.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class preferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	public static final String BG_COLOR = "bgcolor";
	public static final String Module = "module";
	PreferenceScreen ps;
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.fragment_4);  
		ps=(PreferenceScreen)findPreference("delete_files");
		ps.setOnPreferenceClickListener(new deletingonclickListener());
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated m0ethod stub
		if (key.equals(BG_COLOR))
		{
			Preference connPreference = findPreference(key);
			connPreference.setSummary(sharedPreferences.getInt(key, 0));
		}
	}
	

	
	private class deletingonclickListener implements OnPreferenceClickListener{

		@Override
		public boolean onPreferenceClick(Preference p) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
            intent.setClass(preferenceActivity.this, DeleteData.class);
            startActivity(intent);
            return true;
		}
		
	}
}
