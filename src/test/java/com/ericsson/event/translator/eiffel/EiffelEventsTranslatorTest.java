package com.ericsson.event.translator.eiffel;

import com.ericsson.event.translator.cdevents.models.CDEventsData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cdevents.CDEventEnums;
import dev.cdevents.CDEventTypes;
import io.cloudevents.CloudEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class EiffelEventsTranslatorTest {

    @Autowired
    EiffelEventsTranslator eiffelEventsTranslator;

    @MockBean
    RestTemplate resetTemplate;

    @MockBean
    ResponseEntity responseEntity;

    @Test
    void testTranslateCDArtPublishedEventToEiffelEvent() throws JsonProcessingException {

        CDEventsData cdEventsData = new CDEventsData();
        cdEventsData.setEventId("123");
        cdEventsData.setEventName("ArtifactPublishedEventV1");
        cdEventsData.setArtifactId("testArtifactId");
        cdEventsData.setArtifactName("testArtifactName");
        cdEventsData.setSubject("testSubject");

        ObjectMapper objectMapper = new ObjectMapper();
        CloudEvent cdevent = CDEventTypes.createArtifactEvent(CDEventEnums.ArtifactPublishedEventV1.getEventType(), "artifactID", "artifactName", "3.0.0", objectMapper.writeValueAsString(cdEventsData));

        ResponseEntity<String> response = new ResponseEntity("eiffelEventJson", HttpStatus.OK);
        when(resetTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(response);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        boolean result = eiffelEventsTranslator.translateToEiffelEvent(cdevent);
        assertTrue(result);
    }

    @Test
    void testTranslateCDArtCreatedEventToEiffelEvent() throws JsonProcessingException {

        CDEventsData cdEventsData = new CDEventsData();
        cdEventsData.setEventId("123");
        cdEventsData.setEventName("ArtifactCreatedEventV1");
        cdEventsData.setArtifactId("testArtifactId");
        cdEventsData.setArtifactName("testArtifactName");
        cdEventsData.setSubject("testSubject");

        ObjectMapper objectMapper = new ObjectMapper();
        CloudEvent cdevent = CDEventTypes.createArtifactEvent(CDEventEnums.ArtifactCreatedEventV1.getEventType(), "artifactID", "artifactName", "3.0.0", objectMapper.writeValueAsString(cdEventsData));

        ResponseEntity<String> response = new ResponseEntity("eiffelEventJson", HttpStatus.OK);
        when(resetTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(response);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        boolean result = eiffelEventsTranslator.translateToEiffelEvent(cdevent);
        assertTrue(result);
    }

    @Test
    void testTranslateCDPipelineRunFinishedEventToEiffelEvent() throws JsonProcessingException {

        CDEventsData cdEventsData = new CDEventsData();
        cdEventsData.setEventId("123");
        cdEventsData.setEventName("PipelineRunFinishedEventV1");
        cdEventsData.setSubject("testSubject");

        ObjectMapper objectMapper = new ObjectMapper();
        CloudEvent cdevent = CDEventTypes.createPipelineRunEvent(CDEventEnums.PipelineRunFinishedEventV1.getEventType(), "pipelineID", "pipelineName", "Success", "pipelineRunURL", "pipelineRunErrors", objectMapper.writeValueAsString(cdEventsData));

        ResponseEntity<String> response = new ResponseEntity("eiffelEventJson", HttpStatus.OK);
        when(resetTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(response);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        boolean result = eiffelEventsTranslator.translateToEiffelEvent(cdevent);
        assertTrue(result);
    }

}