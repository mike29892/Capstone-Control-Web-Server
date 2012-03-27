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
package com.capstonecontrol.client;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.ServiceName;

import com.capstonecontrol.client.ModulesRequestFactory.ScheduledModuleEventFetchRequest;
import com.capstonecontrol.shared.MessageProxy;
import com.capstonecontrol.shared.ModuleEventProxy;
import com.capstonecontrol.shared.ModuleInfoProxy;
import com.capstonecontrol.shared.RegistrationInfoProxy;
import com.capstonecontrol.shared.ScheduledModuleEventProxy;

public interface ModulesRequestFactory extends RequestFactory {

	@ServiceName("com.capstonecontrol.server.ModuleFetchService")
	  public interface ModuleFetchRequest extends RequestContext {
	    /**
	     * Retrieve users modules from the server.
	     */
		Request<List<ModuleInfoProxy>> getModules();
	  }
	
	
	@ServiceName("com.capstonecontrol.server.ModuleEventFetchService")
	  public interface ModuleEventFetchRequest extends RequestContext {
	    /**
	     * Retrieve users modules from the server.
	     */
		Request<List<ModuleEventProxy>> getModules();
	  }
	
	@ServiceName("com.capstonecontrol.server.ScheduledModuleEventFetchService")
	  public interface ScheduledModuleEventFetchRequest extends RequestContext {
	    /**
	     * Retrieve users modules from the server.
	     */
		Request<List<ScheduledModuleEventProxy>> getModules();
	  }
	
	
  @ServiceName("com.capstonecontrol.server.HelloWorldService")
  public interface HelloWorldRequest extends RequestContext {
    /**
     * Retrieve a "Hello, World" message from the server.
     */
    Request<String> getMessage();
  }

  @ServiceName("com.capstonecontrol.server.RegistrationInfo")
  public interface RegistrationInfoRequest extends RequestContext {
    /**
     * Register a device for C2DM messages.
     */
    InstanceRequest<RegistrationInfoProxy, Void> register();

    /**
     * Unregister a device for C2DM messages.
     */
    InstanceRequest<RegistrationInfoProxy, Void> unregister();
  }

  @ServiceName("com.capstonecontrol.server.Message")
  public interface MessageRequest extends RequestContext {
    /**
     * Send a message to a device using C2DM.
     */
    InstanceRequest<MessageProxy, String> send();
  }

  HelloWorldRequest helloWorldRequest();

  RegistrationInfoRequest registrationInfoRequest();

  MessageRequest messageRequest();

  ModuleFetchRequest moduleFetchRequest();
  
  ModuleEventFetchRequest moduleEventFetchRequest();

  ScheduledModuleEventFetchRequest scheduledModuleEventFetchRequest();
}
