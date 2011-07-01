package com.boarbeard.audio;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.media.MediaPlayer;

import com.boarbeard.R;
import com.boarbeard.Settings;

public class SoundLibrary
{
	public enum Elements
    {
        CommunicationsDownHeader,
        CommunicationsDownNoise,
        CommunicationsDownFooter,

        AnnounceBeginFirstPhase,
        AnnounceFirstPhaseEndsInOneMinute,
        AnnounceFirstPhaseEndsInTwentySeconds,
        AnnounceFirstPhaseEnds,
        AnnounceSecondPhaseBegins,
        AnnounceSecondPhaseEndsInOneMinute,
        AnnounceSecondPhaseEndsInTwentySeconds,
        AnnounceSecondPhaseEnds,
        AnnounceThirdPhaseBegins,
        AnnounceThirdPhaseEndsInOneMinute,
        AnnounceThirdPhaseEndsInTwentySeconds,
        AnnounceThirdPhaseEnds,

        IncomingData,
        DataTransfer,

        AlertHeader,
        InternalThreat,
        SeriousInternalThreat,
        Repeat,
        UnconfirmedReport,

        TimeTPlus1,
        TimeTPlus2,
        TimeTPlus3,
        TimeTPlus4,
        TimeTPlus5,
        TimeTPlus6,
        TimeTPlus7,
        TimeTPlus8,

        RedAlertLevel0,
        RedAlertLevel1,
        RedAlertLevel2,
        RedAlertLevel3,
        //English specific elements
        Threat,
        SeriousThreat,
        ZoneBlue,
        ZoneRed,
        ZoneWhite,

        //German specific elements
        ThreatZoneBlue,
        ThreatZoneRed,
        ThreatZoneWhite,
        SeriousThreatZoneBlue,
        SeriousThreatZoneRed,
        SeriousThreatZoneWhite,
    };
    Map<Elements, MediaInfo> soundTable;
    Map<Settings.AudioSetGrammarType, Map<Elements, Integer>> languageSoundFileTable;
    String currentAudioSet;
    Settings.AudioSetGrammarType currentAudioSetGrammar;
    Map<Elements, String> currentAudioSetTextTable;
    
    private static SoundLibrary soundLibrary = null;
	public static SoundLibrary getInstance() {
		if(soundLibrary == null) {
			soundLibrary = new SoundLibrary();
		}
		return soundLibrary;
	}
      
    public SoundLibrary()
    { 	    	
        Map<Elements, Integer> soundFileTable = new HashMap<Elements, Integer>();
        soundFileTable.put(Elements.CommunicationsDownHeader, R.raw.communications_down);
        soundFileTable.put(Elements.CommunicationsDownNoise, R.raw.white_noise);
        soundFileTable.put(Elements.CommunicationsDownFooter, R.raw.communications_restored);               

        soundFileTable.put(Elements.AnnounceBeginFirstPhase, R.raw.begin_first_phase);
        soundFileTable.put(Elements.AnnounceFirstPhaseEndsInOneMinute, R.raw.first_phase_ends_in_1_minute);
        soundFileTable.put(Elements.AnnounceFirstPhaseEndsInTwentySeconds, R.raw.first_phase_ends_in_20_seconds);
        soundFileTable.put(Elements.AnnounceFirstPhaseEnds, R.raw.first_phase_ends);
        soundFileTable.put(Elements.AnnounceSecondPhaseBegins, R.raw.second_phase_begins);
        soundFileTable.put(Elements.AnnounceSecondPhaseEndsInOneMinute, R.raw.second_phase_ends_in_1_minute);
        soundFileTable.put(Elements.AnnounceSecondPhaseEndsInTwentySeconds, R.raw.second_phase_ends_in_20_seconds);
        soundFileTable.put(Elements.AnnounceSecondPhaseEnds, R.raw.second_phase_ends);
        soundFileTable.put(Elements.AnnounceThirdPhaseBegins, R.raw.third_phase_begins);
        soundFileTable.put(Elements.AnnounceThirdPhaseEndsInOneMinute, R.raw.operation_ends_in_1_minute);
        soundFileTable.put(Elements.AnnounceThirdPhaseEndsInTwentySeconds, R.raw.operation_ends_in_20_seconds);
        soundFileTable.put(Elements.AnnounceThirdPhaseEnds, R.raw.operation_ends);

        soundFileTable.put(Elements.IncomingData, R.raw.incoming_data);
        soundFileTable.put(Elements.DataTransfer, R.raw.data_transfer);

        soundFileTable.put(Elements.AlertHeader, R.raw.alert);
        soundFileTable.put(Elements.InternalThreat, R.raw.internal_threat);
        soundFileTable.put(Elements.SeriousInternalThreat, R.raw.serious_internal_threat);
        soundFileTable.put(Elements.Repeat, R.raw.repeat);
        soundFileTable.put(Elements.UnconfirmedReport, R.raw.unconfirmed_report);

        soundFileTable.put(Elements.TimeTPlus1, R.raw.time_t_plus_1);
        soundFileTable.put(Elements.TimeTPlus2, R.raw.time_t_plus_2);
        soundFileTable.put(Elements.TimeTPlus3, R.raw.time_t_plus_3);
        soundFileTable.put(Elements.TimeTPlus4, R.raw.time_t_plus_4);
        soundFileTable.put(Elements.TimeTPlus5, R.raw.time_t_plus_5);
        soundFileTable.put(Elements.TimeTPlus6, R.raw.time_t_plus_6);
        soundFileTable.put(Elements.TimeTPlus7, R.raw.time_t_plus_7);
        soundFileTable.put(Elements.TimeTPlus8, R.raw.time_t_plus_8);

        soundFileTable.put(Elements.RedAlertLevel0, R.raw.red_alert_0);
        soundFileTable.put(Elements.RedAlertLevel1, R.raw.red_alert_1);
        soundFileTable.put(Elements.RedAlertLevel2, R.raw.red_alert_2);
        soundFileTable.put(Elements.RedAlertLevel3, R.raw.red_alert_3);

        languageSoundFileTable = new HashMap<Settings.AudioSetGrammarType, Map<Elements,Integer>>();
        /*languageSoundFileTable.put(Settings.AudioSetGrammarType.English, new HashMap<Elements,Integer>(soundFileTable));
        languageSoundFileTable.get(Settings.AudioSetGrammarType.English).put(Elements.Threat, R.raw.threat);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.English).put(Elements.SeriousThreat, R.raw.serious_threat);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.English).put(Elements.ZoneBlue, R.raw.zone_blue);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.English).put(Elements.ZoneWhite, R.raw.zone_white);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.English).put(Elements.ZoneRed, R.raw.zone_red);*/

        // MK: Change from boarbeard's original
        languageSoundFileTable.put(Settings.AudioSetGrammarType.German, new HashMap<Elements,Integer>(soundFileTable));
        languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.ThreatZoneBlue, R.raw.threat_zone_blue);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.ThreatZoneRed, R.raw.threat_zone_red);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.ThreatZoneWhite, R.raw.threat_zone_white);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.SeriousThreatZoneBlue, R.raw.serious_threat_zone_blue);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.SeriousThreatZoneRed, R.raw.serious_threat_zone_red);
        languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.SeriousThreatZoneWhite, R.raw.serious_threat_zone_white);
        //languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.ZoneBlue, R.raw.zone_blue);
        //languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.ZoneWhite, R.raw.zone_white);
        //languageSoundFileTable.get(Settings.AudioSetGrammarType.German).put(Elements.ZoneRed, R.raw.zone_red);
        
        languageSoundFileTable.put(Settings.AudioSetGrammarType.German, languageSoundFileTable.get(Settings.AudioSetGrammarType.German));
        //languageSoundFileTable.put(Settings.AudioSetGrammarType.Spanish, languageSoundFileTable.get(Settings.AudioSetGrammarType.English));
        //languageSoundFileTable.put(Settings.AudioSetGrammarType.Czech, languageSoundFileTable.get(Settings.AudioSetGrammarType.English));        
        
        soundTable = null;
        currentAudioSet = "";
    }

    public void loadAudioSet(Context context, String audioSet, Settings.AudioSetGrammarType grammar)
    {

        if (!currentAudioSet.equals(audioSet))
        {
            currentAudioSet = audioSet;
            currentAudioSetGrammar = grammar;
            loadAudioSetText();
            //currentAudioSetTextTable = Form1.globalSettings.getAudioSetText(audioSet);

            soundTable = new HashMap<Elements, MediaInfo>();
            for(Entry<Elements, Integer> languageSet : languageSoundFileTable.get(currentAudioSetGrammar).entrySet())
            {
                //String filename = Form1.globalSettings.RelativePath + "clips/" + currentAudioSet + "/" + languageSoundFileTable[currentAudioSetGrammar][element];
                //if (!File.Exists(filename))
                //{
                //    form1.reportError("An audio clip (" + languageSoundFileTable[currentAudioSetGrammar][element] + ") was missing from audio set \"" + currentAudioSet + "\"");
                //}
                //else
                //{
            	MediaPlayer mp = MediaPlayer.create(context, languageSet.getValue());
            	/*
        	    try {
					mp.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
        	    int duration = mp.getDuration();        	
        		MediaInfo mediaInfo = new MediaInfo(languageSet.getValue(), duration);
            	soundTable.put(languageSet.getKey(), mediaInfo);
            	mp.release();
                    //soundTable[element] = new Mp3File(new FileStream(filename, FileMode.Open, FileAccess.Read));
                //}
            }

            //soundTable.get(Elements.CommunicationsDownNoise).removeFirstAudioFrames(1);
            //soundTable.get(Elements.CommunicationsDownNoise).removeLastAudioFrames(1);
        }

    }

	private void loadAudioSetText() {
		currentAudioSetTextTable = new HashMap<Elements, String>();
		
		// MK: Change from boarbeard's original
		currentAudioSetTextTable.put(Elements.CommunicationsDownHeader, "Kommunikation ausgefallen...");
		currentAudioSetTextTable.put(Elements.CommunicationsDownNoise, "");
		currentAudioSetTextTable.put(Elements.CommunicationsDownFooter, "...Kommunikation wiederhergestellt.");

		currentAudioSetTextTable.put(Elements.AnnounceBeginFirstPhase, "Feindaktivität geortet. Mit 1. Phase beginnen.");
		currentAudioSetTextTable.put(Elements.AnnounceFirstPhaseEndsInOneMinute, "1. Phase endet in einer Minute.");
		currentAudioSetTextTable.put(Elements.AnnounceFirstPhaseEndsInTwentySeconds, "1. Phase endet in 20 Sekunden.");
		currentAudioSetTextTable.put(Elements.AnnounceFirstPhaseEnds, "1. Phase beendet.");
		currentAudioSetTextTable.put(Elements.AnnounceSecondPhaseBegins, "Mit 2. Phase beginnen.");
		currentAudioSetTextTable.put(Elements.AnnounceSecondPhaseEndsInOneMinute, "2. Phase endet in einer Minute.");
		currentAudioSetTextTable.put(Elements.AnnounceSecondPhaseEndsInTwentySeconds, "2. Phase endet in 20 Sekunden.");
		currentAudioSetTextTable.put(Elements.AnnounceSecondPhaseEnds, "2. Phase beendet.");
		currentAudioSetTextTable.put(Elements.AnnounceThirdPhaseBegins, "Mit 3. Phase beginnen.");
		currentAudioSetTextTable.put(Elements.AnnounceThirdPhaseEndsInOneMinute, "3. Phase endet in einer Minute.");
		currentAudioSetTextTable.put(Elements.AnnounceThirdPhaseEndsInTwentySeconds, "3. Phase endet in 20 Sekunden.");
		currentAudioSetTextTable.put(Elements.AnnounceThirdPhaseEnds, "3. Phase beendet. Hyperraumsprung. Mission beendet.");

		currentAudioSetTextTable.put(Elements.IncomingData, "Eingehende Daten");
		currentAudioSetTextTable.put(Elements.DataTransfer, "Datenübertragung");

		currentAudioSetTextTable.put(Elements.AlertHeader, "Alarm!!!");
		currentAudioSetTextTable.put(Elements.InternalThreat, "Interne Bedrohung");
		currentAudioSetTextTable.put(Elements.SeriousInternalThreat, "Ernsthafte interne Bedrohung");
		currentAudioSetTextTable.put(Elements.Repeat, "Wiederhole");
		currentAudioSetTextTable.put(Elements.UnconfirmedReport, "Unbestätigt:");

		currentAudioSetTextTable.put(Elements.TimeTPlus1, "Zeit T+1");
		currentAudioSetTextTable.put(Elements.TimeTPlus2, "Zeit T+2");
		currentAudioSetTextTable.put(Elements.TimeTPlus3, "Zeit T+3");
		currentAudioSetTextTable.put(Elements.TimeTPlus4, "Zeit T+4");
		currentAudioSetTextTable.put(Elements.TimeTPlus5, "Zeit T+5");
		currentAudioSetTextTable.put(Elements.TimeTPlus6, "Zeit T+6");
		currentAudioSetTextTable.put(Elements.TimeTPlus7, "Zeit T+7");
		currentAudioSetTextTable.put(Elements.TimeTPlus8, "Zeit T+8");

		currentAudioSetTextTable.put(Elements.RedAlertLevel0, "");
		currentAudioSetTextTable.put(Elements.RedAlertLevel1, "");
		currentAudioSetTextTable.put(Elements.RedAlertLevel2, "");
		currentAudioSetTextTable.put(Elements.RedAlertLevel3, "");

		currentAudioSetTextTable.put(Elements.Threat, "Bedrohung");
		currentAudioSetTextTable.put(Elements.SeriousThreat, "Ernsthafte Bedrohung");
		currentAudioSetTextTable.put(Elements.ZoneBlue, "Zone Blau");
		currentAudioSetTextTable.put(Elements.ZoneRed, "Zone Rot");
		currentAudioSetTextTable.put(Elements.ZoneWhite, "Zone Weiß");
		currentAudioSetTextTable.put(Elements.ThreatZoneBlue, "Bedrohung in Zone Blau");
		currentAudioSetTextTable.put(Elements.ThreatZoneRed, "Bedrohung in Zone Rot");
		currentAudioSetTextTable.put(Elements.ThreatZoneWhite, "Bedrohung in Zone Weiß");
		currentAudioSetTextTable.put(Elements.SeriousThreatZoneBlue, "Ernsthafte Bedrohung in Zone Blau");
		currentAudioSetTextTable.put(Elements.SeriousThreatZoneRed, "Ernsthafte Bedrohung in Zone Rot");
		currentAudioSetTextTable.put(Elements.SeriousThreatZoneWhite, "Ernsthafte Bedrohung in Zone Weiß");
	}

	public void writeElement(int fileId, int length, int startTime,
			MediaPlayerSequence sequence, Duration writtenDuration) {
		
		MediaInfo mediaInfo = null;
		for(MediaInfo info : soundTable.values()) {
			if(info.getResId() == fileId) {
				mediaInfo = info.copy();
				break;
			}
		}
		
		mediaInfo.setStartTime(startTime);
		
		if(length > 0) {
	    	int repeat =  Math.round((length * 1000f) / mediaInfo.getDuration());
	    	mediaInfo.setRepeat(repeat);
	    	sequence.addAudioClip(mediaInfo);
	        writtenDuration.addDuration(mediaInfo.getDuration() * repeat);
		}
		else {
			sequence.addAudioClip(mediaInfo);
        	writtenDuration.addDuration(mediaInfo.getDuration());
		}
	}
	
	public void writeElement(int fileId, MediaPlayerSequence sequence) {
		
		MediaInfo mediaInfo = null;
		for(MediaInfo info : soundTable.values()) {
			if(info.getResId() == fileId) {
				mediaInfo = info.copy();
				break;
			}
		}
		
		sequence.addAudioClip(mediaInfo);
	}	

}