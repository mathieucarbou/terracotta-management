/*
 * Copyright Terracotta, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.terracotta.management.entity.server;

import org.terracotta.management.entity.ManagementAgent;
import org.terracotta.management.entity.ManagementAgentConfig;
import org.terracotta.management.service.monitoring.IMonitoringConsumer;
import org.terracotta.monitoring.IMonitoringProducer;
import org.terracotta.voltron.proxy.server.ProxiedServerEntity;

/**
 * @author Mathieu Carbou
 */
class ManagementAgentServerEntity extends ProxiedServerEntity<ManagementAgent> {

  private final IMonitoringConsumer consumer;

  ManagementAgentServerEntity(ManagementAgentConfig config, IMonitoringConsumer consumer, IMonitoringProducer producer) {
    super(ManagementAgent.class, new ManagementAgentImpl(config, consumer, producer));
    this.consumer = consumer;
  }

  @Override
  public void destroy() {
    consumer.close();
    super.destroy();
  }
}