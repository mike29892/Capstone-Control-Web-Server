package com.capstonecontrol.server;

import com.google.web.bindery.requestfactory.shared.Locator;


public class ScheduledModuleEventLocator extends Locator<ScheduledModuleEvent, Void> {

	@Override
	public ScheduledModuleEvent create(Class<? extends ScheduledModuleEvent> clazz) {
		return new ScheduledModuleEvent(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	}

	@Override
	public ScheduledModuleEvent find(Class<? extends ScheduledModuleEvent> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Void getId(ScheduledModuleEvent domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVersion(ScheduledModuleEvent domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<ScheduledModuleEvent> getDomainType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		// TODO Auto-generated method stub
		return null;
	}

}
