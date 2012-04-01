package com.capstonecontrol.server;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class PowerData {
	String moduleName;
	String data;
	String user;
	Date date;
	@Id
	Long id;

	public String getModuleName() {
		return moduleName;
	}

	public String getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}
	
	
	
	public String getData(){
		return data;
	}

	PowerData(String moduleName, String data, String user,Object date) {
		this.moduleName = moduleName;
		this.user = user;
		this.date = (Date) date;
		this.data = data;
	}


}