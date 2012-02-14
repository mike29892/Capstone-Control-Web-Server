package com.capstonecontrol.server;

import com.google.web.bindery.requestfactory.shared.Locator;


public class ModuleInfoLocator extends Locator<ModuleInfo, Void> {

	@Override
	public ModuleInfo create(Class<? extends ModuleInfo> clazz) {
		return new ModuleInfo(null, null, null, null);
	}

	@Override
	public ModuleInfo find(Class<? extends ModuleInfo> clazz, Void id) {
		return create(clazz);
	}

	@Override
	public Class<ModuleInfo> getDomainType() {
		return ModuleInfo.class;
	}

	@Override
	public Void getId(ModuleInfo domainObject) {
		return null;
	}

	@Override
	public Class<Void> getIdType() {
		return Void.class;
	}

	@Override
	public Object getVersion(ModuleInfo domainObject) {
		return null;
	}

}
