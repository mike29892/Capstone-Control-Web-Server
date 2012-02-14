package com.capstonecontrol.server;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.labs.taskqueue.TaskQueuePb.TaskQueueQueryAndOwnTasksResponse.Task;

public class DataStore {
	private static PersistenceManagerFactory PMF;
	private static ThreadLocal<PersistenceManager> PER_THREAD_PM;

	public static void initialize() {
		if (PMF != null) {
			throw new IllegalStateException("initialize() already called");
		}
		PMF = JDOHelper.getPersistenceManagerFactory("jdo.properties");
	}

	public static PersistenceManager getPersistanceManager() {
		PER_THREAD_PM = new ThreadLocal<PersistenceManager>();
		PersistenceManager pm = PER_THREAD_PM.get();
		if (pm == null) {
			pm = PMF.getPersistenceManager();
			PER_THREAD_PM.set(pm);
		}
		return pm;
	}

	public static void finishRequest() {
		PersistenceManager pm = PER_THREAD_PM.get();
		if (pm != null) {
			PER_THREAD_PM.remove();
			Transaction tx = (Transaction) pm.currentTransaction();
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<ModuleInfo> findAll() throws IOException {
		PersistenceManager pm = (PersistenceManager) ((ThreadLocal<PersistenceManager>) PMF)
				.get().getPersistenceManagerFactory();
		try {
			javax.jdo.Query query = pm
					.newQuery("where emailAddress=='mike.caulley@gmail.com'");
			List<ModuleInfo> list = (List<ModuleInfo>) query.execute();
			return list;
		} finally {
			// ... cleanup that will execute whether or not an error occurred
			// ...
			return null;
		}
	}
}