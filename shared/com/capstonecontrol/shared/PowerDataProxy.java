package com.capstonecontrol.shared;


import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.capstonecontrol.server.PowerData", locator = "com.capstonecontrol.server.PowerDataLocator")
public interface PowerDataProxy extends EntityProxy  {
	
	public String getModuleName();

	public String getUser();

	public Date getDate();
	
	
	public String getData();

}
