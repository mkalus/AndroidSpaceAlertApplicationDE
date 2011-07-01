package com.boarbeard.generator.beimax;

import com.boarbeard.generator.beimax.event.DataTransfer;
import com.boarbeard.generator.beimax.event.IncomingData;
import com.boarbeard.generator.beimax.event.Threat;
import com.boarbeard.generator.beimax.event.WhiteNoise;


public class ConstructedMissions {

	public static EventList firstTestRun() {		
		EventList eventList = new EventList();		
		eventList.addPhaseEvents(255, 170);
		
		//Phase 1
		eventList.addEvent(15, threat(true, Threat.THREAT_SECTOR_BLUE, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 1));
		eventList.addEvent(60, threat(true, Threat.THREAT_SECTOR_WHITE, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 2));
		eventList.addEvent(90, new DataTransfer());
		eventList.addEvent(135, threat(true, Threat.THREAT_SECTOR_RED, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 3));
		eventList.addEvent(200, new DataTransfer());

		//Phase 2
		eventList.addEvent(280, new IncomingData());
		eventList.addEvent(310, new DataTransfer());
		
		return eventList;
	}
	
	public static EventList secondTestRun() {		
		EventList eventList = new EventList();		
		eventList.addPhaseEvents(225, 200);
		
		//Phase 1
		eventList.addEvent(10, threat(true, Threat.THREAT_SECTOR_WHITE, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 1));
		eventList.addEvent(50, new IncomingData());
		eventList.addEvent(80, threat(true, Threat.THREAT_SECTOR_RED, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 2));
		eventList.addEvent(135, new DataTransfer());

		//Phase 2
		eventList.addEvent(225, threat(true, Threat.THREAT_SECTOR_BLUE, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 4));
		eventList.addEvent(290, new DataTransfer());
		eventList.addEvent(330, new IncomingData());
		
		return eventList;
	}
	
	public static EventList simulation1() {
		EventList eventList = new EventList();		
		eventList.addPhaseEvents(225, 230, 145);

		//Phase 1
		eventList.addEvent(10, threat(true, Threat.THREAT_SECTOR_RED, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 2));
		eventList.addEvent(70, new IncomingData());
		eventList.addEvent(90, threat(true, Threat.THREAT_SECTOR_WHITE, Threat.THREAT_LEVEL_SERIOUS, 
				Threat.THREAT_POSITION_EXTERNAL, 3));
		eventList.addEvent(120, new DataTransfer());
		eventList.addEvent(170, new DataTransfer());

		//Phase 2
		eventList.addEvent(230, threat(false, Threat.THREAT_SECTOR_RED, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 5));
		eventList.addEvent(290, threat(true, Threat.THREAT_SECTOR_BLUE, Threat.THREAT_LEVEL_SERIOUS, 
				Threat.THREAT_POSITION_EXTERNAL, 6));		
		eventList.addEvent(340, new DataTransfer());
		eventList.addEvent(360, new WhiteNoise(15));
		eventList.addEvent(405, new IncomingData());
		
		//Phase 3
		eventList.addEvent(470, new WhiteNoise(10));
		eventList.addEvent(505, new DataTransfer());
		
		return eventList;
	}
	
	public static EventList simulation2() {
		EventList eventList = new EventList();		
		eventList.addPhaseEvents(225, 230, 150);

		//Phase 1
		eventList.addEvent(10, new IncomingData());		
		eventList.addEvent(20, threat(true, Threat.THREAT_SECTOR_BLUE, Threat.THREAT_LEVEL_SERIOUS, 
				Threat.THREAT_POSITION_EXTERNAL, 2));
		eventList.addEvent(70, new DataTransfer());
		eventList.addEvent(100, threat(true, Threat.THREAT_SECTOR_WHITE, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 4));
		eventList.addEvent(180, new DataTransfer());

		//Phase 2
		eventList.addEvent(230, new WhiteNoise(10));
		eventList.addEvent(250, threat(true, Threat.THREAT_SECTOR_RED, Threat.THREAT_LEVEL_SERIOUS, 
				Threat.THREAT_POSITION_EXTERNAL, 6));
		eventList.addEvent(285, new IncomingData());
		eventList.addEvent(300, new WhiteNoise(10));
		eventList.addEvent(330, threat(false, Threat.THREAT_SECTOR_WHITE, Threat.THREAT_LEVEL_NORMAL, 
				Threat.THREAT_POSITION_EXTERNAL, 7));
		eventList.addEvent(360, new DataTransfer());

		//Phase 3
		eventList.addEvent(480, new DataTransfer());
		eventList.addEvent(520, new WhiteNoise(10));
		
		return eventList;
	}
	
	private static Threat threat(boolean confirmed, int sector, int threatLevel,
			int threatPosition, int time) {
		Threat threat = new Threat();
		threat.setConfirmed(confirmed);
		threat.setSector(sector);
		threat.setThreatLevel(threatLevel);
		threat.setThreatPosition(threatPosition);
		threat.setTime(time);
		return threat;
	}
}
