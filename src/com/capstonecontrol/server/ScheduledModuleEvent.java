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
	String action;
	Date scheduledDate;
	int scheduledDays;
	boolean activated;
	@Id
	Long id;

	public Date getScheduledDate(){
		return scheduledDate;
	}
	
	public int getScheduledDays(){
		return scheduledDays;
	}
	
	public boolean getActivated(){
		return activated;
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
	
	public String getAction(){
		return action;
	}
	
	public String getValue(){
		return value;
	}

	ScheduledModuleEvent(String moduleName, String moduleType, String user,
			String action, Date date, String value) {
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.user = user;
		this.action = action;
		this.date = date;
		this.value = value;
	}


}