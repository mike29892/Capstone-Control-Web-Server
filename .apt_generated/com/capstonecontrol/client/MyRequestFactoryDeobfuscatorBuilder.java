// Automatically Generated -- DO NOT EDIT
// com.capstonecontrol.client.MyRequestFactory
package com.capstonecontrol.client;
import java.util.Arrays;
import com.google.web.bindery.requestfactory.vm.impl.OperationData;
import com.google.web.bindery.requestfactory.vm.impl.OperationKey;
public final class MyRequestFactoryDeobfuscatorBuilder extends com.google.web.bindery.requestfactory.vm.impl.Deobfuscator.Builder {
{
withOperation(new OperationKey("qSiTvfEitsSMgg2g1iXZzpxJ3r8="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("send")
  .withRequestContext("com.capstonecontrol.client.MyRequestFactory$MessageRequest")
  .build());
withOperation(new OperationKey("F6kathav8W5pTgcdZmd40ayba$0="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Ljava/lang/String;")
  .withMethodName("getMessage")
  .withRequestContext("com.capstonecontrol.client.MyRequestFactory$HelloWorldRequest")
  .build());
withOperation(new OperationKey("JNDSNQJlAflJHpFSoHcEj9ryhQc="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("register")
  .withRequestContext("com.capstonecontrol.client.MyRequestFactory$RegistrationInfoRequest")
  .build());
withOperation(new OperationKey("Xb_UlZo_bHXG2OC9RABGcQNudiE="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("unregister")
  .withRequestContext("com.capstonecontrol.client.MyRequestFactory$RegistrationInfoRequest")
  .build());
withRawTypeToken("QzJco7b70HN7C1lvWDF5VJG1PB0=", "com.capstonecontrol.shared.MessageProxy");
withRawTypeToken("RSBMY_Mw2OFLjgVqaI2UvIKOXpI=", "com.capstonecontrol.shared.RegistrationInfoProxy");
withRawTypeToken("8KVVbwaaAtl6KgQNlOTsLCp9TIU=", "com.google.web.bindery.requestfactory.shared.ValueProxy");
withRawTypeToken("FXHD5YU0TiUl3uBaepdkYaowx9k=", "com.google.web.bindery.requestfactory.shared.BaseProxy");
withClientToDomainMappings("com.capstonecontrol.server.Message", Arrays.asList("com.capstonecontrol.shared.MessageProxy"));
withClientToDomainMappings("com.capstonecontrol.server.RegistrationInfo", Arrays.asList("com.capstonecontrol.shared.RegistrationInfoProxy"));
}}
