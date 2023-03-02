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
package tech.est.eiffel.translator.cdevents.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ericsson.eiffel.semantics.events.EiffelTestSuiteFinishedEvent;
import com.ericsson.eiffel.semantics.events.EiffelTestSuiteStartedEvent;
import com.fasterxml.jackson.databind.SerializationFeature;

import dev.cdevents.CDEventEnums;
import dev.cdevents.CDEventTypes;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import tech.est.eiffel.translator.cdevents.Constants;
import tech.est.eiffel.translator.cdevents.CustomObjectMapper;
import tech.est.eiffel.translator.cdevents.models.CDEventsData;

@Slf4j
@Component
public class CDEventsTranslator {
    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private CDEventsSender cdEventsSender;

    @Value("${cloudevent.broker.url}")
    private String cloudEventBrokerURL;

    /**
     * Translate Eiffel event with specific type to mapped CDEvent.
     *
     * @param eiffelEventJson
     * @param eiffelEventType
     * @return true If translate is success and CDEvent sent to
     *         cloudEventBrokerURL,all other conditions false
     * @throws IOException
     */
    public boolean translateToCDEvent(String eiffelEventJson, String eiffelEventType) throws IOException {
        CloudEvent cloudEvent = buildCDEvent(eiffelEventJson, eiffelEventType);
        if (cloudEvent == null) {
            log.error("Error translating to CDEvent from Eiffel event type {} ", eiffelEventType);
            return false;
        }
        URL url = new URL(cloudEventBrokerURL);
        HttpURLConnection httpStatus = cdEventsSender.sendCDEvent(cloudEvent, url);
        int statusCode = httpStatus.getResponseCode();
        if (statusCode == HttpStatus.ACCEPTED.value() || statusCode == HttpStatus.OK.value()) {
            log.info("CDEvent {} is published to events-broker URL - {}", cloudEvent.getType(), cloudEventBrokerURL);
        } else {
            log.error("Error sending CDEvent to events-broker response is {} ", httpStatus.getResponseCode());
            return false;
        }
        return true;
    }

    private CloudEvent buildCDEvent(String eiffelEventJson, String eiffelEventType) throws IOException {
        CloudEvent cloudEvent = null;
        if (eiffelEventType.equalsIgnoreCase(Constants.EIFFEL_TESTSUITE_STARTED)) {
            EiffelTestSuiteStartedEvent eiffelTestSuiteStartedEvent = objectMapper.readValue(eiffelEventJson,
                    EiffelTestSuiteStartedEvent.class);
            cloudEvent = createCDTestSuiteStartedEvent(eiffelTestSuiteStartedEvent);
        } else if (eiffelEventType.equalsIgnoreCase(Constants.EIFFEL_TESTSUITE_FINISHED)) {
            EiffelTestSuiteFinishedEvent eiffelTestSuiteFinishedEvent = objectMapper.readValue(eiffelEventJson,
                    EiffelTestSuiteFinishedEvent.class);
            cloudEvent = createCDTestSuiteFinishedEvent(eiffelTestSuiteFinishedEvent);
        }
        /*
         * todo: CDEvent types can be created here once the corresponding Eiffel events
         * are identified
         */

        return cloudEvent;
    }

    private CloudEvent createCDTestSuiteStartedEvent(EiffelTestSuiteStartedEvent eiffelTestSuiteStartedEvent)
            throws IOException {
        log.info("Create TestSuiteStarted CDEvent from Eiffel event - {}",
                eiffelTestSuiteStartedEvent.getMeta().getType());
        CDEventsData cdEventsData = new CDEventsData();
        cdEventsData.setEventId(eiffelTestSuiteStartedEvent.getMeta().getId());
        cdEventsData.setEventName(eiffelTestSuiteStartedEvent.getMeta().getType().value());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        /*
         * todo: match the exact CDEvents fields to Eiffel fields once the latest
         * cdevents/sdk-java is available
         */
        CloudEvent cloudEvent = CDEventTypes.createTestEvent(CDEventEnums.TestSuiteStartedEventV1.getEventType(),
                "testSuiteId", eiffelTestSuiteStartedEvent.getData().getName(), "3.0.0",
                objectMapper.writeValueAsString(cdEventsData));
        log.info("CDEvent testSuite started Data {} ", cloudEvent.getData());
        return cloudEvent;
    }

    private CloudEvent createCDTestSuiteFinishedEvent(EiffelTestSuiteFinishedEvent eiffelTestSuiteFinishedEvent)
            throws IOException {
        log.info("Create TestSuiteFinished CDEvent from Eiffel event - {}",
                eiffelTestSuiteFinishedEvent.getMeta().getType());
        CDEventsData cdEventsData = new CDEventsData();
        cdEventsData.setEventId(eiffelTestSuiteFinishedEvent.getMeta().getId());
        cdEventsData.setEventName(eiffelTestSuiteFinishedEvent.getMeta().getType().value());

        // For eiffel-demo-cdevents purpose, update the artifact details
        eiffelTestSuiteFinishedEvent.getData().getCustomData().forEach(data -> {
            if (data.getKey().equalsIgnoreCase("artifactid")) {
                cdEventsData.setArtifactName(data.getValue().toString());
            } else if (data.getKey().equalsIgnoreCase("artifactname")) {
                cdEventsData.setArtifactId(data.getValue().toString());
            }
        });
        log.info("TestSuiteFinished cdEventData is {} ", cdEventsData.toString());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        /*
         * todo: match the exact CDEvents fields to Eiffel fields once the latest
         * cdevents/sdk-java is available
         */
        CloudEvent cloudEvent = CDEventTypes.createTestEvent(CDEventEnums.TestSuiteFinishedEventV1.getEventType(),
                "testSuiteId", "poc", "3.0.0", objectMapper.writeValueAsString(cdEventsData));
        log.info("CDEvent testSuite finished Data {} ", cloudEvent.getData());
        return cloudEvent;
    }
}
