package com.capstonecontrol.server;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ModuleEvent {
	String moduleName;
	String value;
	String moduleType;
	String user;
	java.sql.Date date;
	String action;
	@Id
	Long id;

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

	ModuleEvent(String moduleName, String moduleType, String user,
			String action, Date date) {
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.user = user;
		this.action = action;
		this.date = (java.sql.Date) date;
	}


}