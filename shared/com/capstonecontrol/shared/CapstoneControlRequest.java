package com.capstonecontrol.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;

@ServiceName(value = "com.capstonecontrol.server.CapstoneControlService", locator = "com.capstonecontrol.server.CapstoneControlServiceLocator")
public interface CapstoneControlRequest extends RequestContext {

	Request<ModuleInfoProxy> createModuleInfo();

	Request<ModuleInfoProxy> readModuleInfo(Long id);

	Request<ModuleInfoProxy> updateModuleInfo(ModuleInfoProxy moduleinfo);

	Request<Void> deleteModuleInfo(ModuleInfoProxy moduleinfo);

	Request<List<ModuleInfoProxy>> queryModuleInfos();

}
