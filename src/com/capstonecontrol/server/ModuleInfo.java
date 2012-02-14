package com.capstonecontrol.server;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ModuleInfo{

		String moduleMacAddr;
		String moduleName;
		String moduleType;
		String user;
		@Id
		Long id;
				
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
		
		ModuleInfo(String moduleMacAddr, String moduleName, String moduleType, String user){
			this.moduleMacAddr = moduleMacAddr;
			this.moduleName = moduleName;
			this.moduleType = moduleType;
			this.user = user;
		}

	}