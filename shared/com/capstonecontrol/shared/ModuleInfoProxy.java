package com.capstonecontrol.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.capstonecontrol.server.ModuleInfo", locator = "com.capstonecontrol.server.ModuleInfoLocator")
public interface ModuleInfoProxy extends EntityProxy  {

	String getModuleMacAddr();

	String getModuleName();

	String getModuleType();

	String getUser();

}
