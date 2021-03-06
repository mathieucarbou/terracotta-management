/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terracotta.management.entity.helloworld.client.management;

import org.terracotta.management.entity.helloworld.client.HelloWorldEntity;
import org.terracotta.management.model.context.Context;
import org.terracotta.management.registry.action.Exposed;
import org.terracotta.management.registry.action.ExposedObject;
import org.terracotta.management.registry.action.Named;

/**
 * @author Mathieu Carbou
 */
public class ExposedHelloWorldEntity implements ExposedObject<HelloWorldEntity> {

  private final String entityName;
  private final HelloWorldEntity helloWorldEntity;

  public ExposedHelloWorldEntity(String entityName, HelloWorldEntity helloWorldEntity) {
    this.entityName = entityName;
    this.helloWorldEntity = helloWorldEntity;
  }

  @Exposed
  public String sayHello(@Named("name") String name) {return helloWorldEntity.sayHello(name);}

  @Override
  public HelloWorldEntity getTarget() {
    return helloWorldEntity;
  }

  @Override
  public ClassLoader getClassLoader() {
    return HelloWorldEntity.class.getClassLoader();
  }

  @Override
  public boolean matches(Context context) {
    return entityName.equals(context.get("entityName"));
  }
}
