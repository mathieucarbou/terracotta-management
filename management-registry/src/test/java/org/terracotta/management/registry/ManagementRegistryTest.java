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
package org.terracotta.management.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.terracotta.management.model.context.ContextContainer;
import org.terracotta.management.provider.action.MyManagementProvider;
import org.terracotta.management.provider.action.MyObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.Assert.assertEquals;

/**
 * @author Mathieu Carbou
 */
@RunWith(JUnit4.class)
public class ManagementRegistryTest {

  @Test
  public void test_management_registry_exposes() throws IOException {
    ManagementRegistry registry = new AbstractManagementRegistry() {
      @Override
      public ContextContainer getContextContainer() {
        return new ContextContainer("cacheManagerName", "my-cm-name");
      }
    };

    registry.addManagementProvider(new MyManagementProvider());

    registry.register(new MyObject("myCacheManagerName", "myCacheName1"));
    registry.register(new MyObject("myCacheManagerName", "myCacheName2"));

    String expectedJson = new String(read(new File("src/test/resources/capabilities.json")), "UTF-8");
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    assertEquals(expectedJson, mapper.writeValueAsString(registry.getCapabilities()));
  }

  static byte[] read(File f) throws IOException {
    byte[] data = new byte[(int) f.length()];
    RandomAccessFile reader = new RandomAccessFile(f, "r");
    try {
      reader.readFully(data);
      return data;
    } finally {
      reader.close();
    }
  }

}