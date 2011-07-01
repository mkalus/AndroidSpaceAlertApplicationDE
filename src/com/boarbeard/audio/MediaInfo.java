package com.boarbeard.audio;

public class MediaInfo {
	private int resId;
	private int duration;	
	private int repeat;
	private int startTime;
	private String description;
	
	public MediaInfo(int resId, int duration) {
		this.resId = resId;
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public int getResId() {
		return resId;
	}
	
	public int getRepeat() {
		return repeat;
	}

	public int setRepeat(int times) {
		repeat = times;
		return repeat;
	}
	
	public MediaInfo copy() {
		MediaInfo mediaInfo = new MediaInfo(resId, duration);
		mediaInfo.description = description;
		mediaInfo.setRepeat(this.repeat);
		return mediaInfo;
	}
	
	@Override
	public String toString() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartTime(int time) {
		startTime = time;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
}
