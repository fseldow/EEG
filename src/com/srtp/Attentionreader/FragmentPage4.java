package com.srtp.Attentionreader;
import com.srtp.Attentionreader.extra.DeleteData;
import com.srtp.Attentionreader.extra.PreferenceFragment;
import com.srtp.Attentionreader.extra.preferenceActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;

public class FragmentPage4 extends PreferenceFragment {  
	public static final String BG_COLOR = "bgcolor";
	public static final String Module = "module";
    PreferenceScreen ps;
    
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        addPreferencesFromResource(R.layout.fragment_4);  
        ps=(PreferenceScreen)findPreference("delete_files");
		ps.setOnPreferenceClickListener(new deletingonclickListener());
    }  
    
    
    
    
    private class deletingonclickListener implements OnPreferenceClickListener{

		@Override
		public boolean onPreferenceClick(Preference p) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
            intent.setClass(getActivity(), DeleteData.class);
            startActivity(intent);
            return true;
		}
		
	}
    
    public void onDestroyView(){
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        MainActivity.Module  = sharedPreferences.getString("module", "experiment");
    	super.onDestroyView();
    }
    
}  

