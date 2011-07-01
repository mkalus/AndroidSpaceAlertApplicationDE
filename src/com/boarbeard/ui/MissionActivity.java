package com.boarbeard.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.boarbeard.R;
import com.boarbeard.audio.MediaPlayerMainMission;
import com.boarbeard.audio.MediaPlayerSequence;
import com.boarbeard.generator.beimax.ConstructedMissions;
import com.boarbeard.generator.beimax.MissionImpl;

public class MissionActivity extends Activity {
	
	private MediaPlayerSequence sequence;
	
	private TextView logTextView;
	private TextView missionTypeTextView;
	
	private ScrollView scrollView;
	private ToggleButton togglebutton;
	
	private PowerManager.WakeLock wakeLock;
	
	private StopWatch stopWatch;
	
	private MissionType missionType = MissionType.Random;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
                
    	PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    	wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Mission Wake Lock");
        
    	missionTypeTextView = (TextView)findViewById(R.id.missionTypeTextView);
    	missionTypeTextView.setText(missionType.toString());
    	
        stopWatch = new StopWatch((TextView)findViewById(R.id.missionClockTextView));
        
        scrollView = (ScrollView) findViewById(R.id.missionScrollView); 
        logTextView = (TextView) findViewById(R.id.missionTextView);
        logTextView.setMovementMethod(ScrollingMovementMethod.getInstance());        
                                        
        togglebutton = (ToggleButton) findViewById(R.id.togglePlayMission);
        togglebutton.setOnClickListener(new OnClickListener() {            

			public void onClick(View v) {
                if (togglebutton.isChecked()) {
                	if(sequence == null) {
                		createMission();
                		startMission();
                	} else {
                		startMission();
                	}                	
                } else {
                	pauseMission();
                }
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    	    	
    	// Handle item selection
        switch (item.getItemId()) {
        case R.id.menuNewMission:
        	createMission();
            return true;
        case R.id.menuResetMission:
        	if(sequence != null) {
        		sequence.reset();
        	}
        	return true;
        case R.id.menuMissionOptions:
        	startActivity(new Intent(this, PreferencesActivity.class));
        	return true;
        case R.id.menuMissionAbout:
        	startActivity(new Intent(this, AboutActivity.class));
        	return true;
        case R.id.menuMissionHelp:
        	startActivity(new Intent(this, HelpActivity.class));
        	return true;     
        case R.id.menuTypeMission:
        	createMissionTypeDialog();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void createMission() {
    	stopMission();
		sequence = new MediaPlayerMainMission(this, stopWatch);
		
		switch(missionType) {
		case Random:
	    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			MissionImpl mission = new MissionImpl(preferences);
			mission.generateMission();
			sequence.write(mission.getMissionEvents());
			break;
		default:
			sequence.write(missionType.getEventList());
		}		
    }
    
    private void createMissionTypeDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Choose Mission Type");
    	builder.setItems(MissionType.toStringValues(this), new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	missionType = MissionType.values()[item];
    	    	missionTypeTextView.setText(missionType.toString(MissionActivity.this));
    	    	createMission();
    	    }
    	});
    	AlertDialog alertDialog = builder.create();
    	alertDialog.show();
    }
    
    private void startMission() {    	
    	if(sequence != null) {
    		if(!wakeLock.isHeld())       	 	
    			wakeLock.acquire();
    		sequence.start();
    	}
    }
    
    private void pauseMission() {
    	if(sequence != null) {
    		sequence.pause();
    		if(wakeLock.isHeld())
       	 		wakeLock.release();
    	}
    }
    
    private void stopMission() {
    	if(sequence != null) {
    		sequence.stop();
    		if(wakeLock.isHeld())
       	 		wakeLock.release();
    	}
    }

	public void updateMissionLog(String missionLog) {
		logTextView.setText(missionLog);
		
    	scrollView.post(new Runnable() { 
    	    public void run() { 
    	    	scrollView.fullScroll(ScrollView.FOCUS_DOWN); 
    	    } 
    	});     			
	}
	
	public void toggleOff() {
    	togglebutton.setChecked(false);    	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	    	if(sequence != null) {
	    		sequence.reset();
	    	}
	    }
	    return super.onKeyDown(keyCode, event);
	}
}