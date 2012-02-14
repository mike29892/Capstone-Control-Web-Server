package com.capstonecontrol.server;

import java.io.IOException;
import java.util.List;

import com.capstonecontrol.annotation.ServiceMethod;
import com.google.appengine.api.labs.taskqueue.TaskQueuePb.TaskQueueQueryAndOwnTasksResponse.Task;


public class CapstoneControlService {

	static DataStore db = new DataStore();
	@ServiceMethod
	public ModuleInfo createModuleInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@ServiceMethod
	public ModuleInfo readModuleInfo(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@ServiceMethod
	public ModuleInfo updateModuleInfo(ModuleInfo moduleinfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@ServiceMethod
	public void deleteModuleInfo(ModuleInfo moduleinfo) {
		// TODO Auto-generated method stub

	}

	@ServiceMethod
	public List<ModuleInfo> queryModuleInfos() throws IOException {
		return db.findAll();
	}

}
