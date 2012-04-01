package com.capstonecontrol.server;

import com.google.web.bindery.requestfactory.shared.Locator;


public class PowerDataLocator extends Locator<PowerData, Void> {

	@Override
	public PowerData create(Class<? extends PowerData> clazz) {
		return new PowerData(null, null, null, null);
	}

	@Override
	public Class<Void> getIdType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PowerData find(Class<? extends PowerData> clazz, Void id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Void getId(PowerData domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVersion(PowerData domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<PowerData> getDomainType() {
		// TODO Auto-generated method stub
		return null;
	}

}
