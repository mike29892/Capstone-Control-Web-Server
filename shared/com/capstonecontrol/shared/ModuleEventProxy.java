package com.capstonecontrol.shared;




import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.capstonecontrol.server.ModuleEvent", locator = "com.capstonecontrol.server.ModuleEventLocator")
public interface ModuleEventProxy extends EntityProxy  {

	String getModuleName();

	String getModuleType();

	String getUser();
	
	String getAction();
	
	Date getDate();
	
	String getValue();

}
 