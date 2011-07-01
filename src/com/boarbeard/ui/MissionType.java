package com.boarbeard.ui;

import android.content.Context;

import com.boarbeard.R;
import com.boarbeard.generator.beimax.ConstructedMissions;
import com.boarbeard.generator.beimax.EventList;

public enum MissionType {
	Random(R.string.mission_random, null),
	FirstTestRun(R.string.mission_first_test_run, ConstructedMissions.firstTestRun()),
	SecondTestRun(R.string.mission_second_test_run, ConstructedMissions.secondTestRun()),
	Simulation1(R.string.mission_simulation_1, ConstructedMissions.simulation1()),
	Simulation2(R.string.mission_simulation_2, ConstructedMissions.simulation2());
	
	private int resId;
	private EventList eventList;
	
	MissionType(int resId, EventList eventList) {
		this.eventList = eventList;
		this.resId = resId;
	}
	
	public EventList getEventList() {
		return eventList;
	}

	public int getResId() {
		return resId;
	}
	
	public String toString(Context context) {
		return context.getString(getResId());
	}
	
	public static CharSequence[] toStringValues(Context context) {
		MissionType[] values = values();
		String[] names = new String[values.length];
		
		for(int i=0; i<values.length; ++i) {
			names[i] = context.getString(values[i].resId);
		}
		
		return names;
	}
}
