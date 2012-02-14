package com.capstonecontrol.shared;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.capstonecontrol.server.ModuleInfo", locator = "com.capstonecontrol.server.ModuleInfoLocator")
public interface ModuleInfoProxy extends ValueProxy {

	String getDate();

	String getModuleMacAddr();

	String getModuleName();

	String getModuleType();

	String getUser();

}
