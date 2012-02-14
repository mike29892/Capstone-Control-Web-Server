package com.capstonecontrol.server;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ModuleInfo{

		String date;
		String moduleMacAddr;
		String moduleName;
		String moduleType;
		String user;
		@Id
		Long id;
		
		public String getDate(){
			return date;
		}
		
		public String getModuleMacAddr(){
			return moduleMacAddr;
		}
		
		public String getModuleName(){
			return moduleName;
		}
		
		public String getModuleType(){
			return moduleType;
		}
		
		
		public String getUser(){
			return user;
		}
	}