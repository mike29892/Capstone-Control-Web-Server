package com.capstonecontrol.shared;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.capstonecontrol.server.ScheduledModuleEvent", locator = "com.capstonecontrol.server.ScheduledModuleEventLocator")
public interface ScheduledModuleEventProxy extends EntityProxy {

	public String getModuleName();

	public String getModuleType();
	
	public String getAction();
	
	public Date getDate();

	public Date getSchedDate();

	public Boolean getMon();

	public Boolean getTue();

	public Boolean getWed();

	public Boolean getThu();

	public Boolean getFri();

	public Boolean getSat();

	public Boolean getSun();

	public Boolean getActive();

	public Boolean getRecur();

	public Long getMinute();

	public Long getHour();
	
	public Long getDay();

	public Long getMonth();

	public Long getYear();

	public Long getTimeOffset();

	public Long getValue();
}
