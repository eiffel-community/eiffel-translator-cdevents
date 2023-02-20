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
package com.ericsson.event.translator.eiffel;

import static com.ericsson.event.translator.Constants.CDEVENT_TO_EIFFEL;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ericsson.eiffel.semantics.events.CustomData;
import com.ericsson.eiffel.semantics.events.EiffelActivityFinishedEventMeta;
import com.ericsson.eiffel.semantics.events.EiffelActivityFinishedEventOutcome;
import com.ericsson.eiffel.semantics.events.EiffelArtifactCreatedEventMeta;
import com.ericsson.eiffel.semantics.events.EiffelArtifactPublishedEventMeta;
import com.ericsson.eiffel.semantics.events.Link;
import com.ericsson.eiffel.semantics.events.Location;
import com.ericsson.event.translator.Constants;
import com.ericsson.event.translator.CustomObjectMapper;
import com.ericsson.event.translator.eiffel.events.EiffelActivityFinishedEvent;
import com.ericsson.event.translator.eiffel.events.EiffelArtifactCreatedEvent;
import com.ericsson.event.translator.eiffel.events.EiffelArtifactPublishedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EiffelEventsTranslator {

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private RestTemplate resetTemplate;

    @Value("${remrem.publish.url}")
    private String remRemPublishURL;

    /**
     * Translate CDEvent to mapped Eiffel event.
     *
     * @param cdevent
     * @return true if CDEvent translated to mapped Eiffel event and sent to
     *         configured remRemPublishURL, all other conditions false
     */
    public boolean translateToEiffelEvent(CloudEvent cdevent) {
        String cdeventType = cdevent.getType();
        String eiffelEventType = CDEVENT_TO_EIFFEL.get(cdeventType);
        log.info("Translating from CDEvent {} to EiffelEvent {}", cdeventType, eiffelEventType);
        String eiffelEventJson = buildEiffelEventJson(cdevent, cdeventType);
        if (StringUtils.isEmpty(eiffelEventJson)) {
            log.error("Error translating to EiffelEvent from CDEvent type {} ", cdeventType);
            return false;
        }
        HttpStatus response = sendEiffelEventToRemRemPublish(eiffelEventJson, eiffelEventType);
        if (response == HttpStatus.OK) {
            log.info("Eiffel event {} is published to RemRem Publish URL - {}", eiffelEventType, remRemPublishURL);
        } else {
            log.error("Error sending Eiffel event to REMReM Publish response is {} ", response);
            return false;
        }
        return true;
    }

    private HttpStatus sendEiffelEventToRemRemPublish(String eiffelEventJson, String eiffelEventType) {
        String remRemPublishPostUrl = remRemPublishURL + "?mp=eiffelsemantics&msgType=" + eiffelEventType;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(eiffelEventJson, headers);

        ResponseEntity<String> response = resetTemplate.postForEntity(remRemPublishPostUrl, entity, String.class);

        return response.getStatusCode();
    }

    private String buildEiffelEventJson(CloudEvent cdevent, String cdeventType) {
        String eiffelEventJson = "";
        if (cdeventType.equals(Constants.CDEVENTS_ART_PUBLISHED)) {
            eiffelEventJson = buildEiffelArtifactPublishedEvent(cdevent);
        } else if (cdeventType.equals(Constants.CDEVENTS_ART_CREATED)) {
            eiffelEventJson = buildEiffelArtifactCreatedEvent(cdevent);
        } else if (cdeventType.equals(Constants.CDEVENTS_PIPELINERUN_FINISHED)) {
            eiffelEventJson = buildEiffelActivityFinishedEvent(cdevent);
        }
        /*
         * todo: Different Eiffel types can be created here once the corresponding
         * CDEvents are identified
         */
        return eiffelEventJson;
    }

    private String buildEiffelActivityFinishedEvent(CloudEvent cdevent) {
        String eiffelActFEventJson = "";
        try {
            EiffelActivityFinishedEvent eiffelActivityFinishedEvent = new EiffelActivityFinishedEvent();

            // For eiffel-demo-cdevents purpose, update the artifact details
            updateArtifactDetailsPoC(cdevent, eiffelActivityFinishedEvent);

            eiffelActivityFinishedEvent.getMsgParams().getMeta()
                    .setType(EiffelActivityFinishedEventMeta.Type.EIFFEL_ACTIVITY_FINISHED_EVENT);
            eiffelActivityFinishedEvent.getMsgParams().getMeta()
                    .setVersion(EiffelActivityFinishedEventMeta.Version._3_0_0);

            // To know the event is published by this translator to avoid cyclic publish
            eiffelActivityFinishedEvent.getMsgParams().getMeta().getTags().add("Published");

            eiffelActivityFinishedEvent.getEventParams().getLinks()
                    .add(getLinkParams("ACTIVITY_EXECUTION", cdevent.getId()));

            EiffelActivityFinishedEventOutcome outcome = new EiffelActivityFinishedEventOutcome();
            outcome.setConclusion(EiffelActivityFinishedEventOutcome.Conclusion.SUCCESSFUL);
            outcome.setDescription("Activity execution is success");
            eiffelActivityFinishedEvent.getEventParams().getData().setOutcome(outcome);
            /*
             * todo: match the exact CDEvents fields to Eiffel fields once the latest
             * cdevents/sdk-java is available
             */
            eiffelActFEventJson = objectMapper.writeValueAsString(eiffelActivityFinishedEvent);
            log.info("Updated eiffelActFEventJson - {}", eiffelActFEventJson);

        } catch (Exception e) {
            log.error("Exception occurred while building EiffelActivityFinishedEvent from CDEvent {} ", e.getMessage());
        }

        return eiffelActFEventJson;
    }

    private String buildEiffelArtifactCreatedEvent(CloudEvent inputEvent) {
        String eiffelArtCEventJson = "";
        try {
            EiffelArtifactCreatedEvent eiffelArtifactCreatedEvent = new EiffelArtifactCreatedEvent();

            eiffelArtifactCreatedEvent.getMsgParams().getMeta()
                    .setType(EiffelArtifactCreatedEventMeta.Type.EIFFEL_ARTIFACT_CREATED_EVENT);
            eiffelArtifactCreatedEvent.getMsgParams().getMeta()
                    .setVersion(EiffelArtifactCreatedEventMeta.Version._3_0_0);

            // To know the event is published by this translator to avoid cyclic publish
            eiffelArtifactCreatedEvent.getMsgParams().getMeta().getTags().add("Published");

            eiffelArtifactCreatedEvent.getEventParams().getLinks().add(getLinkParams("CAUSE", inputEvent.getId()));

            if (inputEvent.getExtension("source") != null) {
                String artifactSource = inputEvent.getExtension("source").toString();
                log.info("Received data source from event {} ", artifactSource);
                eiffelArtifactCreatedEvent.getMsgParams().getMeta().getSource().setUri(artifactSource);
            }

            if (inputEvent.getExtension("id") != null) {
                String artifactIdentity = inputEvent.getExtension("id").toString();
                log.info("Received data identity from event {} ", artifactIdentity);
                eiffelArtifactCreatedEvent.getEventParams().getData().setIdentity(artifactIdentity);
            }
            /*
             * todo: match the exact CDEvents fields to Eiffel fields once the latest
             * cdevents/sdk-java is available
             */
            eiffelArtCEventJson = objectMapper.writeValueAsString(eiffelArtifactCreatedEvent);
            log.info("Updated eiffelArtCEventJson - {}", eiffelArtCEventJson);
        } catch (Exception e) {
            log.error("Exception occurred while building EiffelArtifactCreatedEvent from CDEvent {} ", e.getMessage());
        }

        return eiffelArtCEventJson;
    }

    private String buildEiffelArtifactPublishedEvent(CloudEvent inputEvent) {
        String eiffelArtPEventJson = "";

        try {
            EiffelArtifactPublishedEvent eiffelArtifactPublishedEvent = new EiffelArtifactPublishedEvent();

            eiffelArtifactPublishedEvent.getMsgParams().getMeta()
                    .setType(EiffelArtifactPublishedEventMeta.Type.EIFFEL_ARTIFACT_PUBLISHED_EVENT);
            eiffelArtifactPublishedEvent.getMsgParams().getMeta()
                    .setVersion(EiffelArtifactPublishedEventMeta.Version._3_1_0);

            // To know the event is published by this translator
            eiffelArtifactPublishedEvent.getMsgParams().getMeta().getTags().add("Published");

            eiffelArtifactPublishedEvent.getEventParams().getLinks().add(getLinkParams("ARTIFACT", inputEvent.getId()));

            if (inputEvent.getExtension("source") != null) {
                String artifactSource = inputEvent.getExtension("source").toString();
                log.info("Received data source from event {} ", artifactSource);
                eiffelArtifactPublishedEvent.getMsgParams().getMeta().getSource().setUri(artifactSource);
            }

            if (inputEvent.getExtension("id") != null) {
                Location location = new Location();
                location.setUri(inputEvent.getExtension("id").toString());
                location.setType(Location.Type.OTHER);
                eiffelArtifactPublishedEvent.getEventParams().getData().getLocations().add(location);
            }
            /*
             * todo: match the exact CDEvents fields to Eiffel fields once the latest
             * cdevents/sdk-java is available
             */
            eiffelArtPEventJson = objectMapper.writeValueAsString(eiffelArtifactPublishedEvent);
            log.info("Updated eiffelArtPEventJson - {}", eiffelArtPEventJson);
        } catch (Exception e) {
            log.error("Exception occurred while building EiffelArtifactPublishedEvent from CDEvent {} ",
                    e.getMessage());
        }
        return eiffelArtPEventJson;
    }

    private Link getLinkParams(String linkType, String target) {
        Link link = new Link();
        link.setType(linkType);
        link.setTarget(target);
        return link;
    }

    private void updateArtifactDetailsPoC(CloudEvent cdevent, EiffelActivityFinishedEvent eiffelActFEvent)
            throws JsonProcessingException {
        String ceDataJsonString = new String(cdevent.getData().toBytes(), StandardCharsets.UTF_8);
        log.info("invoked translateToEiffelEvent()");
        Map<String, Object> ceDataMap = objectMapper.readValue(ceDataJsonString, HashMap.class);

        if (ceDataMap.get("artifactId") != null) {
            CustomData customData = new CustomData();
            customData.setKey("artifactid");
            customData.setValue(ceDataMap.get("artifactId"));
            eiffelActFEvent.getEventParams().getData().getCustomData().add(customData);
        }

        if (ceDataMap.get("artifactName") != null) {
            CustomData customData = new CustomData();
            customData.setKey("artifactname");
            customData.setValue(ceDataMap.get("artifactName"));
            eiffelActFEvent.getEventParams().getData().getCustomData().add(customData);
        }
    }
}
