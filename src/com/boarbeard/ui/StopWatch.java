package com.boarbeard.ui;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

public class StopWatch {

	private TextView timeTextView;
	private long startTime = 0;
	private long initialPausedTime = 0;
	private long pausedTime = 0;
	private Handler mHandler = new Handler();
	
	private int minutes = 0;
	private int seconds = 0;

	private final Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			timeTextView.setText(StopWatch.this.toString());
			mHandler.postAtTime(this, startTime
					+ (((minutes * 60) + seconds + 1) * 1000));
		}
	};

	public StopWatch(TextView display) {
		timeTextView = display;
	}

	public void start() {
		if (startTime == 0L) {
			startTime = SystemClock.uptimeMillis();
			mHandler.removeCallbacks(mUpdateTimeTask);
			mHandler.postDelayed(mUpdateTimeTask, 100);
		} else {
			pausedTime = SystemClock.uptimeMillis() - initialPausedTime;
			initialPausedTime = 0;
			mHandler.postDelayed(mUpdateTimeTask, 100);
		}
	}

	public void pause() {
		mHandler.removeCallbacks(mUpdateTimeTask);
		initialPausedTime = SystemClock.uptimeMillis();
	}

	public void stop() {
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	public void reset() {
		mHandler.removeCallbacks(mUpdateTimeTask);
		startTime = 0;		
		timeTextView.setText("0:00");
	}

	@Override
	public String toString() {
		startTime += pausedTime;
		pausedTime = 0;
		long millis = SystemClock.uptimeMillis() - startTime;
		seconds = (int) (millis / 1000);
		minutes = seconds / 60;
		seconds = seconds % 60;
	
		return String.format("%01d:%02d", minutes, seconds);
	}
}
