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
package com.ericsson.event.translator.cdevents;

import com.ericsson.event.translator.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CDEventsTranslatorTest {

    @Autowired
    private CDEventsTranslator cdEventsTranslator;

    @MockBean
    private CDEventsSender cdEventsSender;

    @Test
    void testTranslateEiffelTSFFEventToCDEventSuccess() throws IOException {
        String eiffelTSFEventJson = "{\"meta\":{\"id\":\"a3ed16f5-3ed8-4724-9443-d80904b92711\",\"type\":\"EiffelTestSuiteFinishedEvent\","
                + "\"version\":\"3.0.0\",\"time\":1675359443384,\"tags\":[\"\"],\"source\":{\"domainId\":\"eiffel\",\"host\":\"82945db0a411\","
                + "\"name\":\"eiffel-jenkins\",\"serializer\":\"pkg:maven/com.github.eiffel-community/eiffel-remrem-semantics@2.0.13\","
                + "\"uri\":\"nulljob/Eiffel_ActF_From_CDEvents_PoC/14/\"}},\"data\":{\"outcome\":{\"verdict\":\"PASSED\","
                + "\"conclusion\":\"SUCCESSFUL\",\"description\":\"\"},\"customData\":[{\"key\":\"artifactid\","
                + "\"value\":\"kind-registry:5000/cdevent/poc@sha256:cd061e85f07086a0df54c7f5c51a2d9fb8a721282c0292a1662b7971907b14bd\"},"
                + "{\"key\":\"artifactname\",\"value\":\"poc\"}]},\"links\":[{\"type\":\"TEST_SUITE_EXECUTION\","
                + "\"target\":\"c358779a-44d7-42d1-8cc0-a50ed2e5229e\"}]}";
        MockHttpURLConnection httpURLConnection = new MockHttpURLConnection(new URL("http://events-broker-url"));
        httpURLConnection.setResponseCode(HttpStatus.ACCEPTED.value());
        when(cdEventsSender.sendCDEvent(any(), any())).thenReturn(httpURLConnection);
        boolean result = cdEventsTranslator.translateToCDEvent(eiffelTSFEventJson, Constants.EIFFEL_TESTSUITE_FINISHED);
        assertTrue(result);
    }

    @Test
    void testTranslateEiffelTSSFEventToCDEventSuccess() throws IOException {
        String eiffelTSSEventJson = "{\"meta\":{\"id\":null,\"type\":\"EiffelTestSuiteStartedEvent\",\"version\":\"3.0.0\",\"time\":null,"
                + "\"tags\":[],\"source\":null,\"security\":null},\"data\":{\"name\":\"test_suite\",\"categories\":[],\"types\":[],"
                + "\"liveLogs\":[],\"customData\":[]},\"links\":[{\"type\":\"CONTEXT\","
                + "\"target\":\"c358779a-44d7-42d1-8cc0-a50ed2e5229e\"}]}\n";
        MockHttpURLConnection httpURLConnection = new MockHttpURLConnection(new URL("http://events-broker-url"));
        httpURLConnection.setResponseCode(HttpStatus.ACCEPTED.value());
        when(cdEventsSender.sendCDEvent(any(), any())).thenReturn(httpURLConnection);
        boolean result = cdEventsTranslator.translateToCDEvent(eiffelTSSEventJson, Constants.EIFFEL_TESTSUITE_STARTED);
        assertTrue(result);
    }

    @Test
    void testTranslateEiffelTSSFEventToCDEventFailure() throws IOException {
        String eiffelTSSEventJson = "{\"meta\":{\"id\":null,\"type\":\"EiffelTestSuiteStartedEvent\",\"version\":\"3.0.0\","
                + "\"time\":null,\"tags\":[],\"source\":null,\"security\":null},\"data\":{\"name\":\"test_suite\","
                + "\"categories\":[],\"types\":[],\"liveLogs\":[],\"customData\":[]},\"links\":[{\"type\":\"CONTEXT\","
                + "\"target\":\"c358779a-44d7-42d1-8cc0-a50ed2e5229e\"}]}\n";
        MockHttpURLConnection httpURLConnection = new MockHttpURLConnection(new URL("http://events-broker-url"));
        httpURLConnection.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        when(cdEventsSender.sendCDEvent(any(), any())).thenReturn(httpURLConnection);
        boolean result = cdEventsTranslator.translateToCDEvent(eiffelTSSEventJson, Constants.EIFFEL_TESTSUITE_STARTED);
        assertFalse(result);
    }
}
