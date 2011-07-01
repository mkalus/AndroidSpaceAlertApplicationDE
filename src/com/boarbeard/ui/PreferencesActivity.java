package com.boarbeard.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.boarbeard.R;

public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);	
	}

      public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {}
}