package com.ericsson.event.translator.cdevents;

import com.ericsson.event.translator.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CDEventsTranslatorTest {

    @Autowired
    CDEventsTranslator cdEventsTranslator;

    @MockBean
    CDEventsSender cdEventsSender;

    @Test
    void testTranslateEiffelTSFFEventToCDEventSuccess() throws IOException {
        String eiffelTSFEventJson = "{\"meta\":{\"id\":\"a3ed16f5-3ed8-4724-9443-d80904b92711\",\"type\":\"EiffelTestSuiteFinishedEvent\",\"version\":\"3.0.0\",\"time\":1675359443384,\"tags\":[\"\"],\"source\":{\"domainId\":\"eiffel\",\"host\":\"82945db0a411\",\"name\":\"eiffel-jenkins\",\"serializer\":\"pkg:maven/com.github.eiffel-community/eiffel-remrem-semantics@2.0.13\",\"uri\":\"nulljob/Eiffel_ActF_From_CDEvents_PoC/14/\"}},\"data\":{\"outcome\":{\"verdict\":\"PASSED\",\"conclusion\":\"SUCCESSFUL\",\"description\":\"\"},\"customData\":[{\"key\":\"artifactid\",\"value\":\"kind-registry:5000/cdevent/poc@sha256:cd061e85f07086a0df54c7f5c51a2d9fb8a721282c0292a1662b7971907b14bd\"},{\"key\":\"artifactname\",\"value\":\"poc\"}]},\"links\":[{\"type\":\"TEST_SUITE_EXECUTION\",\"target\":\"c358779a-44d7-42d1-8cc0-a50ed2e5229e\"}]}";
        MockHttpURLConnection  httpURLConnection = new MockHttpURLConnection(new URL("http://events-broker-url"));
        httpURLConnection.setResponseCode(202);
        when(cdEventsSender.sendCDEvent(any(), any())).thenReturn(httpURLConnection);
        boolean result = cdEventsTranslator.translateToCDEvent(eiffelTSFEventJson, Constants.EIFFEL_TESTSUITE_FINISHED);
        assertTrue(result);
    }

    @Test
    void testTranslateEiffelTSSFEventToCDEventSuccess() throws IOException {
        String eiffelTSSEventJson = "{\"meta\":{\"id\":null,\"type\":\"EiffelTestSuiteStartedEvent\",\"version\":\"3.0.0\",\"time\":null,\"tags\":[],\"source\":null,\"security\":null},\"data\":{\"name\":\"test_suite\",\"categories\":[],\"types\":[],\"liveLogs\":[],\"customData\":[]},\"links\":[{\"type\":\"CONTEXT\",\"target\":\"c358779a-44d7-42d1-8cc0-a50ed2e5229e\"}]}\n";
        MockHttpURLConnection  httpURLConnection = new MockHttpURLConnection(new URL("http://events-broker-url"));
        httpURLConnection.setResponseCode(202);
        when(cdEventsSender.sendCDEvent(any(), any())).thenReturn(httpURLConnection);
        boolean result = cdEventsTranslator.translateToCDEvent(eiffelTSSEventJson, Constants.EIFFEL_TESTSUITE_STARTED);
        assertTrue(result);
    }

    @Test
    void testTranslateEiffelTSSFEventToCDEventFailure() throws IOException {
        String eiffelTSSEventJson = "{\"meta\":{\"id\":null,\"type\":\"EiffelTestSuiteStartedEvent\",\"version\":\"3.0.0\",\"time\":null,\"tags\":[],\"source\":null,\"security\":null},\"data\":{\"name\":\"test_suite\",\"categories\":[],\"types\":[],\"liveLogs\":[],\"customData\":[]},\"links\":[{\"type\":\"CONTEXT\",\"target\":\"c358779a-44d7-42d1-8cc0-a50ed2e5229e\"}]}\n";
        MockHttpURLConnection  httpURLConnection = new MockHttpURLConnection(new URL("http://events-broker-url"));
        httpURLConnection.setResponseCode(500);
        when(cdEventsSender.sendCDEvent(any(), any())).thenReturn(httpURLConnection);
        boolean result = cdEventsTranslator.translateToCDEvent(eiffelTSSEventJson, Constants.EIFFEL_TESTSUITE_STARTED);
        assertFalse(result);
    }
}