package com.capstonecontrol.server;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ScheduledModuleEvent {
	String moduleName, moduleType, action;
	Date date;
	Date schedDate;
	Boolean mon, tue, wed, thu, fri, sat, sun, active, recur;
	Long minute, hour, day, month, year, timeOffset, value;

	public ScheduledModuleEvent(String moduleName, String moduleType, Date date, Date schedDate,
			Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri,
			Boolean sat, Boolean sun, Boolean active, Boolean recur,
			Long minute, Long hour, Long day, Long month, Long year,
			Long timeOffset, Long value, String action) {
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.date = date;
		this.schedDate = schedDate;
		this.mon = mon;
		this.tue = tue;
		this.wed = wed;
		this.thu = thu;
		this.fri = fri;
		this.sat = sat;
		this.sun = sun;
		this.active = active;
		this.recur = recur;
		this.minute = minute;
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.year = year;
		this.timeOffset = timeOffset;
		this.value = value;
		this.action = action;
	}

	public String getModuleType(){
		return moduleType;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public String getAction(){
		return action;
	}

	public Date getDate() {
		return date;
	}

	public Date getSchedDate() {
		return schedDate;
	}

	public Boolean getMon() {
		return mon;
	}

	public Boolean getTue() {
		return tue;
	}

	public Boolean getWed() {
		return wed;
	}

	public Boolean getThu() {
		return thu;
	}

	public Boolean getFri() {
		return fri;
	}

	public Boolean getSat() {
		return sat;
	}

	public Boolean getSun() {
		return sun;
	}

	public Boolean getActive() {
		return active;
	}

	public Boolean getRecur() {
		return recur;
	}

	public Long getMinute() {
		return minute;
	}

	public Long getHour() {
		return hour;
	}
	
	public Long getDay(){
		return day;
	}

	public Long getMonth() {
		return month;
	}

	public Long getYear() {
		return year;
	}

	public Long getTimeOffset() {
		return timeOffset;
	}

	public Long getValue() {
		return value;
	}
}