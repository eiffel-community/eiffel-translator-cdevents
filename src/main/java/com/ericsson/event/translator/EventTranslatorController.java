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
package com.ericsson.event.translator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.event.translator.cdevents.CDEventsTranslator;
import com.ericsson.event.translator.eiffel.EiffelEventsTranslator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/translate")
public class EventTranslatorController {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CDEventsTranslator cdEventTranslator;
    @Autowired
    private EiffelEventsTranslator eiffelEventsTranslator;

    /**
     * Sample Get map to check EventTranslatorController.
     *
     * @return String message
     */
    @RequestMapping("/")
    public String hello() {
        return "Hello EventTranslatorController";
    }

    /**
     * Takes CloudEvent as input and translate to mapped Eiffel event.
     *
     * @param inputEvent
     * @return ResponseEntity
     */
    @RequestMapping(value = "/eiffel", method = RequestMethod.POST)
    public ResponseEntity<Void> translateToEiffelEvent(@RequestBody CloudEvent inputEvent) {
        boolean result = eiffelEventsTranslator.translateToEiffelEvent(inputEvent);
        if (!result) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Takes Eiffel event as input and translate to mapped CDEvent.
     *
     * @param eiffelEventJson
     * @return ResponseEntity
     */
    @RequestMapping(value = "/cdevent", method = RequestMethod.POST)
    public ResponseEntity<Void> translateToCDEvent(@RequestBody String eiffelEventJson) {
        log.info("IN translateToCDEvent received eiffelEventJson {} ", eiffelEventJson);
        try {
            JsonNode jsonNode = objectMapper.readTree(eiffelEventJson);
            JsonNode metaObj = jsonNode.get("meta");
            String eventType = metaObj.get("type").asText();

            log.info("Eiffel event {} received to translate to CDEvent and publish", eventType);
            cdEventTranslator.translateToCDEvent(eiffelEventJson, eventType);
        } catch (Exception e) {
            log.error("Exception occurred while translateToEiffelEvent {} ", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
