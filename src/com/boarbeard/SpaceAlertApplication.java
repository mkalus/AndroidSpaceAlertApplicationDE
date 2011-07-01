package com.boarbeard;

import com.boarbeard.audio.SoundLibrary;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.Log;

public class SpaceAlertApplication extends Application {
	private static SpaceAlertApplication singleton;

	public static SpaceAlertApplication getInstance() {
		return singleton;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
		
		// MK: Change from boarbeard's original
        SoundLibrary.getInstance().loadAudioSet(this.getBaseContext(), "german" , Settings.AudioSetGrammarType.German);
	}
}
