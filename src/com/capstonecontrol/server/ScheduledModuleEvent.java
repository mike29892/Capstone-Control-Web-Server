package com.capstonecontrol.server;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ScheduledModuleEvent {
	String moduleName;
	String value;
	String moduleType;
	String user;
	Date date;
	String schedDate;
	String action;
	int hours, minutes, day, month, year;
	boolean active, reoccurence;
	boolean Sun, Mon, Tue, Wed, Thu, Fri, Sat;
	@Id
	Long id;

	public ScheduledModuleEvent(String moduleName, String moduleType,
			String user, String action, Date date, String value, boolean mon,
			boolean tue, boolean wed, boolean thu, boolean fri, boolean sat,
			boolean sun, boolean active, int hours, int minutes,
			String schedDate, int year, int day, boolean reoccurence) {
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.user = user;
		this.action = action;
		this.date = date;
		this.value = value;
		this.Mon = mon;
		this.Tue = tue;
		this.Wed = wed;
		this.Thu = thu;
		this.Fri = fri;
		this.Sat = sat;
		this.Sun = sun;
		this.active = active;
		this.reoccurence = reoccurence;
		this.hours = hours;
		this.minutes = minutes;
		this.day = day;
		this.year = year;
		this.schedDate = schedDate;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getYear(){
		return year;
	}
	
	public boolean getReoccurence(){
		return reoccurence;
	}
	
	public String getSchedDate(){
		return schedDate;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getHours() {
		return hours;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getMon() {
		return Mon;
	}

	public boolean getTue() {
		return Tue;
	}

	public boolean getWed() {
		return Wed;
	}

	public boolean getThu() {
		return Thu;
	}

	public boolean getFri() {
		return Fri;
	}

	public boolean getSat() {
		return Sat;
	}

	public boolean getSun() {
		return Sun;
	}

	public void setMon(boolean Mon) {
		this.Mon = Mon;
	}

	public void setTue(boolean Tue) {
		this.Tue = Tue;
	}

	public void setWed(boolean Wed) {
		this.Wed = Wed;
	}

	public void setThu(boolean Thu) {
		this.Thu = Thu;
	}

	public void setFri(boolean Fri) {
		this.Fri = Fri;
	}

	public void setSat(boolean Sat) {
		this.Sat = Sat;
	}

	public void setSun(boolean Sun) {
		this.Sun = Sun;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getModuleType() {
		return moduleType;
	}

	public String getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}

	public String getAction() {
		return action;
	}

	public String getValue() {
		return value;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// @TODO figure out why this is needed and how its used
	int getVersion() {
		return (Integer) null;
	}

	// @TODO figure out why this is needed and how its used
	Long getId() {
		return null;
	}

	public static ScheduledModuleEvent findModuleInfo(Long id) {
		return null;

	}
}