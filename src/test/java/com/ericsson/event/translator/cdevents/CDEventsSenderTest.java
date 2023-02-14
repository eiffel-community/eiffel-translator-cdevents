package com.ericsson.event.translator.cdevents;

import com.ericsson.event.translator.cdevents.models.CDEventsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cdevents.CDEventEnums;
import dev.cdevents.CDEventTypes;
import io.cloudevents.CloudEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CDEventsSenderTest {

    @Spy
    private CDEventsSender cdEventsSender;

    @Mock
    private HttpURLConnection connection;

    @Mock
    private URL url;

    @Mock
    private OutputStream outputStream;

    @Test
    void testSendCDEventSuccess() throws IOException {
        int expected = HttpStatus.ACCEPTED.value();

        CDEventsData cdEventsData = new CDEventsData();
        cdEventsData.setEventId("123");
        cdEventsData.setEventName("TestSuiteFinishedEventV1");
        cdEventsData.setArtifactId("testArtifactId");
        cdEventsData.setArtifactName("testArtifactName");
        cdEventsData.setSubject("testSubject");

        ObjectMapper objectMapper = new ObjectMapper();
        CloudEvent cloudEvent = CDEventTypes.createTestEvent(CDEventEnums.TestSuiteFinishedEventV1.getEventType(),
                "testSuiteId", "poc", "3.0.0", objectMapper.writeValueAsString(cdEventsData));

        doReturn(connection).when(url).openConnection();
        doReturn(expected).when(connection).getResponseCode();
        doReturn(outputStream).when(connection).getOutputStream();
        HttpURLConnection httpURLConnection = cdEventsSender.sendCDEvent(cloudEvent, url);

        assertEquals(expected, httpURLConnection.getResponseCode());
    }
}
