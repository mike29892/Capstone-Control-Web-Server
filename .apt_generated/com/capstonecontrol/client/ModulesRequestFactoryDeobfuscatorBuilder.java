// Automatically Generated -- DO NOT EDIT
// com.capstonecontrol.client.ModulesRequestFactory
package com.capstonecontrol.client;
import java.util.Arrays;
import com.google.web.bindery.requestfactory.vm.impl.OperationData;
import com.google.web.bindery.requestfactory.vm.impl.OperationKey;
public final class ModulesRequestFactoryDeobfuscatorBuilder extends com.google.web.bindery.requestfactory.vm.impl.Deobfuscator.Builder {
{
withOperation(new OperationKey("vRpTZDhtyJ4ZCG4Wg9lkmsdL7Ik="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/util/List;")
  .withMethodName("getModuleEvents")
  .withRequestContext("com.capstonecontrol.client.ModulesRequestFactory$ModuleEventFetchRequest")
  .build());
withOperation(new OperationKey("4FdRR0_17KvRf5Pc_kHMBHrSA1U="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/util/List;")
  .withMethodName("getPowerData")
  .withRequestContext("com.capstonecontrol.client.ModulesRequestFactory$PowerDataFetchService")
  .build());
withOperation(new OperationKey("uN6ipyS7VQDhhXUY5C52Qr43HZ4="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("send")
  .withRequestContext("com.capstonecontrol.client.ModulesRequestFactory$MessageRequest")
  .build());
withOperation(new OperationKey("wuYkXrmR7jNnpX_NlOsniBzOWGY="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("getMessage")
  .withRequestContext("com.capstonecontrol.client.ModulesRequestFactory$HelloWorldRequest")
  .build());
withOperation(new OperationKey("bkGU_T4RK6fo4cN9vq10PxX6f58="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("register")
  .withRequestContext("com.capstonecontrol.client.ModulesRequestFactory$RegistrationInfoRequest")
  .build());
withOperation(new OperationKey("muWFAFvSkhMorhSZ4tSgv4Td_x8="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("unregister")
  .withRequestContext("com.capstonecontrol.client.ModulesRequestFactory$RegistrationInfoRequest")
  .build());
withOperation(new OperationKey("t8uAuj7W_eGZh5279iAcL8BiK2U="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/util/List;")
  .withMethodName("getModules")
  .withRequestContext("com.capstonecontrol.client.ModulesRequestFactory$ModuleFetchRequest")
  .build());
withOperation(new OperationKey("sdTydV1waUcFepWPz47MYOoHwCg="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/util/List;")
  .withMethodName("getScheduledEvents")
  .withRequestContext("com.capstonecontrol.client.ModulesRequestFactory$ScheduledModuleEventFetchRequest")
  .build());
withRawTypeToken("QzJco7b70HN7C1lvWDF5VJG1PB0=", "com.capstonecontrol.shared.MessageProxy");
withRawTypeToken("h91pLb3WWmJeaHbl1Gykgdp6C20=", "com.capstonecontrol.shared.ModuleEventProxy");
withRawTypeToken("5o5PieQ$p2mpyQId43v$xtZUJJw=", "com.capstonecontrol.shared.ModuleInfoProxy");
withRawTypeToken("Xb3PyOdojxSlZZAW6T78qcblcCM=", "com.capstonecontrol.shared.PowerDataProxy");
withRawTypeToken("RSBMY_Mw2OFLjgVqaI2UvIKOXpI=", "com.capstonecontrol.shared.RegistrationInfoProxy");
withRawTypeToken("0VhxB6U9hB8ANWzwCn2i$EOoBVE=", "com.capstonecontrol.shared.ScheduledModuleEventProxy");
withRawTypeToken("w1Qg$YHpDaNcHrR5HZ$23y518nA=", "com.google.web.bindery.requestfactory.shared.EntityProxy");
withRawTypeToken("8KVVbwaaAtl6KgQNlOTsLCp9TIU=", "com.google.web.bindery.requestfactory.shared.ValueProxy");
withRawTypeToken("FXHD5YU0TiUl3uBaepdkYaowx9k=", "com.google.web.bindery.requestfactory.shared.BaseProxy");
withClientToDomainMappings("com.capstonecontrol.server.Message", Arrays.asList("com.capstonecontrol.shared.MessageProxy"));
withClientToDomainMappings("com.capstonecontrol.server.ModuleEvent", Arrays.asList("com.capstonecontrol.shared.ModuleEventProxy"));
withClientToDomainMappings("com.capstonecontrol.server.ModuleInfo", Arrays.asList("com.capstonecontrol.shared.ModuleInfoProxy"));
withClientToDomainMappings("com.capstonecontrol.server.PowerData", Arrays.asList("com.capstonecontrol.shared.PowerDataProxy"));
withClientToDomainMappings("com.capstonecontrol.server.RegistrationInfo", Arrays.asList("com.capstonecontrol.shared.RegistrationInfoProxy"));
withClientToDomainMappings("com.capstonecontrol.server.ScheduledModuleEvent", Arrays.asList("com.capstonecontrol.shared.ScheduledModuleEventProxy"));
}}
