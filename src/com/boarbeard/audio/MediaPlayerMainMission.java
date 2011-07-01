package com.boarbeard.audio;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;

import com.boarbeard.R;
import com.boarbeard.ui.MissionActivity;
import com.boarbeard.ui.StopWatch;

public class MediaPlayerMainMission extends MediaPlayerSequence {
	
	private Handler mHandler = new Handler();
	
	private long startTime;
	private long pauseTime;

	protected StopWatch stopWatch;
	
	protected MissionActivity missionActivity;
	protected String missionLog;
	
	private MediaPlayerCycle mediaPlayerBackgroundSounds;
	
	private final Runnable mPlayNextAudioTask = new Runnable() {
		public void run() {
			mediaPlayerBackgroundSounds.pause();
			playNextAudio();
		}
	};
	
	public MediaPlayerMainMission(MissionActivity missionActivity, StopWatch stopWatch) {
		super(missionActivity);
				
		this.stopWatch = stopWatch;
		this.missionActivity = missionActivity;
		init();
		
		mediaPlayerBackgroundSounds = new MediaPlayerCycle(missionActivity);
		SoundLibrary.getInstance().writeElement(R.raw.red_alert_0, mediaPlayerBackgroundSounds);
		SoundLibrary.getInstance().writeElement(R.raw.red_alert_1, mediaPlayerBackgroundSounds);
		SoundLibrary.getInstance().writeElement(R.raw.red_alert_2, mediaPlayerBackgroundSounds);
		SoundLibrary.getInstance().writeElement(R.raw.red_alert_3, mediaPlayerBackgroundSounds);

	}
	
	public void addAudioClip(MediaInfo mediaInfo) {
		mediaPlayerList.add(mediaInfo);		
	}
	
	public synchronized void start() {
		if(mediaPlayerList.size() <= playerIndex) {
			init();
		}
		
    	stopWatch.start();
		
		stopped = false;
		if(paused) {
			paused = false;
			if(activeMediaPlayer != null) {
				activeMediaPlayer.start();
				return;
			} else {
				startTime += SystemClock.uptimeMillis() - pauseTime;
				mediaPlayerBackgroundSounds.start();
				mHandler.postAtTime(mPlayNextAudioTask, 							 
						startTime + mediaPlayerList.get(playerIndex).getStartTime());
				
				return;
			}
		}
				
		startTime = SystemClock.uptimeMillis();
		playNextAudio();
	}
	
	public synchronized void init() {
		playerIndex = 0;
		activeMediaPlayer = null;
		missionLog = "";
		missionActivity.updateMissionLog(missionLog);
		stopWatch.reset();
	}	

	private synchronized void playNextAudio() {	
		final MediaInfo mediaInfo = mediaPlayerList.get(playerIndex);
		activeMediaPlayer = MediaPlayer.create(missionActivity, mediaInfo.getResId());	
		
    	if(!TextUtils.isEmpty(mediaInfo.toString())) {
        	missionLog = missionLog + stopWatch.toString() + " -- "
        		+ mediaInfo.toString() + "\n";
        	missionActivity.updateMissionLog(missionLog);	            	
    	}
		
		activeMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {            	
        		if(mediaInfo.setRepeat(mediaInfo.getRepeat()-1) > 0) {
        			activeMediaPlayer.start();
        			return;
        		}     
        		
            	activeMediaPlayer.stop();
        		activeMediaPlayer.release();
        		activeMediaPlayer = null;
        		
    			if(stopped)
					return;
				
				playerIndex++;
				if(mediaPlayerList.size() > playerIndex) {					
					long nextTime = startTime + mediaPlayerList.get(playerIndex).getStartTime();
					if(nextTime > SystemClock.uptimeMillis())
						mediaPlayerBackgroundSounds.start();
					
					mHandler.postAtTime(mPlayNextAudioTask, 							 
							nextTime);
				} else {
					stop();
				}
            }
        });
		
		if(!paused && !stopped) {			
			activeMediaPlayer.start();
		}		
	}

	public synchronized void pause() {
		paused = true;
		stopWatch.pause();
		mediaPlayerBackgroundSounds.pause();
		missionActivity.toggleOff();
		pauseTime = SystemClock.uptimeMillis();
		mHandler.removeCallbacks(mPlayNextAudioTask);
		
		if(activeMediaPlayer != null && activeMediaPlayer.isPlaying()) {
			activeMediaPlayer.pause();
		}
	}

	public synchronized void stop() {
		stopped = true;
		if(activeMediaPlayer != null) {
			activeMediaPlayer.stop();
			activeMediaPlayer.release();
			activeMediaPlayer = null;
		}
		missionActivity.toggleOff();
		mediaPlayerBackgroundSounds.stop();
		stopWatch.stop();
	}
	
	public void reset() {
		stop();
		init();
	}

	public void writeDescription(String description) {
		mediaPlayerList.get(mediaPlayerList.size()-1).setDescription(description);
	}

}
