package com.boarbeard.audio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.boarbeard.generator.beimax.EventList;
import com.boarbeard.generator.beimax.event.Event;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerSequence {

	protected List<MediaInfo> mediaPlayerList = new ArrayList<MediaInfo>();
	
	protected int playerIndex = 0;
	protected boolean stopped = false;
	protected boolean paused = false;
		
	protected MediaPlayer activeMediaPlayer = null;

	private Context context;	
	
	public MediaPlayerSequence(Context context) {
		this.context = context;
	}
	
	public void addAudioClip(MediaInfo mediaInfo) {
		mediaPlayerList.add(mediaInfo);
		
	}
	
	public synchronized void start() {
		if(mediaPlayerList.size() <= playerIndex) {
			init();
		}
		
		stopped = false;
		if(paused) {
			paused = false;
			if(activeMediaPlayer != null) {
				activeMediaPlayer.start();
				return;
			}
		}
				
		playNextAudio(mediaPlayerList.get(playerIndex));
	}
	
	public synchronized void init() {
		playerIndex = 0;
		activeMediaPlayer = null;
	}
	
	public synchronized void stop() {
		stopped = true;
		if(activeMediaPlayer != null) {
			activeMediaPlayer.stop();
			activeMediaPlayer.release();
			activeMediaPlayer = null;
		}
	}

	private synchronized void playNextAudio(final MediaInfo mediaInfo) {	
		activeMediaPlayer = MediaPlayer.create(context, mediaInfo.getResId());
		activeMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {            	
        		if(mediaInfo.setRepeat(mediaInfo.getRepeat()-1) > 0) {
        			activeMediaPlayer.start();
        			return;
        		}     

            	activeMediaPlayer.stop();
        		activeMediaPlayer.release();
        		
    			if(stopped)
					return;
				
				playerIndex = nextIndex();
				if(mediaPlayerList.size() > playerIndex) {
					playNextAudio(mediaPlayerList.get(playerIndex));
				}
            }
        });
		
		if(!paused && !stopped) {
			activeMediaPlayer.start();
		}		
	}
	
	public int nextIndex() {
		return playerIndex + 1;
	}

	public synchronized void pause() {
		paused = true;
		if(activeMediaPlayer != null && activeMediaPlayer.isPlaying()) {
			activeMediaPlayer.pause();
		}
	}
	
	public void reset() {
		stop();
		init();
	}

	public void writeDescription(String description) {
		mediaPlayerList.get(mediaPlayerList.size()-1).setDescription(description);
	}
	
	public void write(EventList eventList) {		
		Duration durationWrittenSoFar = new Duration();		
        for(Entry<Integer, Event> eventItem : eventList.getEntrySet())
        {
        	int startTime = eventItem.getKey() * 1000;
        	Event event = eventItem.getValue();
            write(event, startTime, durationWrittenSoFar);
        }   
	}

	public void write(Event event, int startTime, Duration writtenDuration)
    {    	 
		boolean written = false;
		String mp3s = event.getMP3s(writtenDuration.getDuration());		
		String[] files = mp3s.split(",");		
		
		for(String file : files) {			
			int length = 0;	
			int fileId = 0;
			if (file.indexOf(':') != -1) {
				fileId = Integer.parseInt(file.split(":")[0]);
				length = Integer.parseInt(file.split(":")[1]);
			} else {
				fileId = Integer.parseInt(file);
			}
			
			SoundLibrary.getInstance().writeElement(fileId, length, startTime, this, writtenDuration);
			
			if(!written) {
				written = true;
				writeDescription(event.toString());
			}
		}						           
    }

}
