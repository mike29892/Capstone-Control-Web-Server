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

import java.util.List;
import java.util.logging.Logger;

public class ModuleFetchService {

	private static final Logger log = Logger.getLogger(ModuleFetchService.class
			.getName());

	public ModuleFetchService() {
	}

	public static List<ModuleInfo> getModules() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		List<ModuleInfo> modules;
		List<ModuleInfo> moduleInfo;
		if (user == null) {
			//no user loged in so nothing to return
			//initialize modules to nothing
			modules=null;
		} else {
			// get modules that match user
			String username = user.getNickname();
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Key moduleKey = KeyFactory.createKey("user", username);
			// Run an ancestor query to ensure we see the most up-to-date
			Query query = new Query("moduleInfo", moduleKey).addSort("date",
					Query.SortDirection.DESCENDING);
			//modules = datastore.prepare(query).asList(
			//		FetchOptions.Builder.withLimit(15));
			modules = null;
		}
		moduleInfo = modules;
		return modules;
	}
}
