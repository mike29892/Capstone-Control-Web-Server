/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.capstonecontrol.server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import java.util.Date;

public class ScheduledModuleEventFetchService {

	private static final Logger log = Logger
			.getLogger(ScheduledModuleEventFetchService.class.getName());

	public ScheduledModuleEventFetchService() {
	}

	public static List<ScheduledModuleEvent> getModules() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		List<Entity> events;
		List<ScheduledModuleEvent> scheduledModulesEvent = new ArrayList<ScheduledModuleEvent>();

		String firstModuleName = null;
		if (user == null) {
			// no user loged in so nothing to return
			// initialize modules to nothing
			events = null;
		} else {
			// get modules that match user
			String username = user.getNickname();
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Key moduleKey = KeyFactory.createKey("user", username);
			// Run an ancestor query to ensure we see the most up-to-date
			Query query = new Query("ScheduleModuleEvent", moduleKey).addSort(
					"date", Query.SortDirection.DESCENDING);
			events = datastore.prepare(query).asList(
					FetchOptions.Builder.withLimit(2147483647));
			if (events.isEmpty()) {

				// There are no modules added to your account.

			} else {

				// There modules on your account are
				for (Entity event : events) {
					// create moduleInfo and add it to the list
					ScheduledModuleEvent scheduledModuleEvent = new ScheduledModuleEvent(
							(String) event.getProperty("moduleName"),
							(String) event.getProperty("moduleType"),
							(String) event.getProperty("user"),
							(String) event.getProperty("action"),
							(Date) event.getProperty("date"),
							(String) event.getProperty("value"),
							(Boolean) event.getProperty("Mon"),
							(Boolean) event.getProperty("Tue"),
							(Boolean) event.getProperty("Wed"),
							(Boolean) event.getProperty("Thu"),
							(Boolean) event.getProperty("Fri"),
							(Boolean) event.getProperty("Sat"),
							(Boolean) event.getProperty("Sun"),
							(Boolean) event.getProperty("active"), (Integer) event.getProperty("hours"), (Integer) event.getProperty("minutes"),
							(String) event.getProperty("schedDate"), (Integer) event.getProperty("year"),(Integer) event.getProperty("day"), (Boolean) event.getProperty("recur"));
					scheduledModulesEvent.add(scheduledModuleEvent);
				}
			}

		}
		return scheduledModulesEvent;
	}
}
