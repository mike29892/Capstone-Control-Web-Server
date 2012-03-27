package com.capstonecontrol.shared;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.capstonecontrol.server.ScheduleModuleEvent", locator = "com.capstonecontrol.server.ScheduleModuleEventLocator")
public interface ScheduledModuleEventProxy extends EntityProxy {

	public int getMinutes();

	public int getHours();

	public void setMinutes(int minutes);

	public void setHours(int hours);

	public boolean getActive();

	public void setActive(boolean active);

	public boolean getMon();

	public boolean getTue();

	public boolean getWed();

	public boolean getThu();

	public boolean getFri();

	public boolean getSat();

	public boolean getSun();

	public void setMon(boolean Mon);

	public void setTue(boolean Tue);

	public void setWed(boolean Wed);

	public void setThu(boolean Thu);

	public void setFri(boolean Fri);

	public void setSat(boolean Sat);

	public void setSun(boolean Sun);

	public String getModuleName();

	public String getModuleType();

	public String getUser();

	public Date getDate();

	public String getAction();

	public String getValue();

	public void setDate(Date date);

	// @TODO figure out why this is needed and how its used
	int getVersion();

	// @TODO figure out why this is needed and how its used
	Long getId();

}
