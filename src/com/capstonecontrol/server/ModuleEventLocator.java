package com.capstonecontrol.server;

import com.google.web.bindery.requestfactory.shared.Locator;


public class ModuleEventLocator extends Locator<ModuleEvent, Void> {

	@Override
	public ModuleEvent create(Class<? extends ModuleEvent> clazz) {
		return new ModuleEvent(null, null, null, null, null, null);
	}

	@Override
	public ModuleEvent find(Class<? extends ModuleEvent> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Void getId(ModuleEvent domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVersion(ModuleEvent domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<ModuleEvent> getDomainType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		// TODO Auto-generated method stub
		return null;
	}

}
