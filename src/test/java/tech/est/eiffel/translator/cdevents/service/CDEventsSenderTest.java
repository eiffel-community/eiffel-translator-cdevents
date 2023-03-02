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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.cdevents.CDEventEnums;
import dev.cdevents.CDEventTypes;
import io.cloudevents.CloudEvent;
import tech.est.eiffel.translator.cdevents.models.CDEventsData;

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
