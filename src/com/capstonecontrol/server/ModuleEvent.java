package com.capstonecontrol.server;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ModuleEvent {
	String moduleName;
	String value;
	String moduleType;
	String user;
	String date;
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

	public String getDate() {
		return date;
	}

	ModuleEvent(String moduleName, String moduleType, String user,
			String action, String date) {
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.user = user;
		this.action = action;
		this.date = date;
	}

}