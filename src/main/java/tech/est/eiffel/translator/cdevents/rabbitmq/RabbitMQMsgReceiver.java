/*
    Copyright (C) 2023 Nordix Foundation.
    For a full list of individual contributors, please see the commit history.
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
          http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    SPDX-License-Identifier: Apache-2.0
*/
package tech.est.eiffel.translator.cdevents.rabbitmq;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.extern.slf4j.Slf4j;
import tech.est.eiffel.translator.cdevents.service.CDEventsTranslator;

@Slf4j
@Component
public class RabbitMQMsgReceiver {

    @Autowired
    private CDEventsTranslator cdEventTranslator;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${cloudevent.broker.url}")
    private String cloudEventBrokerURL;

    /**
     * Receives a message from configured RabbitMQ host.
     *
     * @param message
     */
    public void receiveMessage(byte[] message) {
        String eventJson = new String(message, StandardCharsets.UTF_8);
        boolean isPublished = false;
        log.info("RabbitMQMsgReceiver Received message from RabbitMQ <" + eventJson + ">");
        try {
            JsonNode jsonNode = objectMapper.readTree(eventJson);
            JsonNode metaObj = jsonNode.get("meta");
            String eiffelEventType = metaObj.get("type").asText();
            log.info("Eiffel event {} received", eiffelEventType);

            ArrayNode tagsArrayNode = (ArrayNode) metaObj.get("tags");
            for (Iterator<JsonNode> iterator = tagsArrayNode.elements(); iterator.hasNext();) {
                String tag = iterator.next().asText();
                log.info("Eiffel event meta tag {}", tag);
                if (tag.equalsIgnoreCase("published")) {
                    isPublished = true;
                    break;
                }
            }
            if (!isPublished) {
                log.info(
                        "The Eiffel event {} will be translated to CDEvent and published to configured events-broker {}",
                        eiffelEventType, cloudEventBrokerURL);
                cdEventTranslator.translateToCDEvent(eventJson, eiffelEventType);
            } else {
                log.info("Ignoring, the Eiffel event {} is published by event-translator itself..", eiffelEventType);
            }

        } catch (Exception e) {
            log.error("Exception occurred RabbitMQ receiveMessage {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
