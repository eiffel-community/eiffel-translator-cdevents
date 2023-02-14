package com.ericsson.event.translator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ericsson.eiffel.semantics.events.EiffelActivityFinishedEventMeta;
import com.ericsson.eiffel.semantics.events.EiffelArtifactCreatedEventMeta;
import com.ericsson.eiffel.semantics.events.EiffelArtifactPublishedEventMeta;
import com.ericsson.eiffel.semantics.events.EiffelTestSuiteFinishedEventMeta;
import com.ericsson.eiffel.semantics.events.EiffelTestSuiteStartedEventMeta;

import dev.cdevents.CDEventEnums;

public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    /**
     * Constant value for EiffelArtifactCreatedEvent.
     */
    public static final String EIFFEL_ART_CREATED = EiffelArtifactCreatedEventMeta.Type.EIFFEL_ARTIFACT_CREATED_EVENT
            .value();

    /**
     * Constant value for EiffelArtifactPublishedEvent.
     */
    public static final String EIFFEL_ART_PUBLISHED = EiffelArtifactPublishedEventMeta.Type.EIFFEL_ARTIFACT_PUBLISHED_EVENT
            .value();

    /**
     * Constant value for EiffelActivityFinishedEvent.
     */
    public static final String EIFFEL_ACTIVITY_FINISHED = EiffelActivityFinishedEventMeta.Type.EIFFEL_ACTIVITY_FINISHED_EVENT
            .value();

    /**
     * Constant value for EiffelTestSuiteStartedEvent.
     */
    public static final String EIFFEL_TESTSUITE_STARTED = EiffelTestSuiteStartedEventMeta.Type.EIFFEL_TEST_SUITE_STARTED_EVENT
            .value();

    /**
     * Constant value for EiffelTestSuiteFinishedEvent.
     */
    public static final String EIFFEL_TESTSUITE_FINISHED = EiffelTestSuiteFinishedEventMeta.Type.EIFFEL_TEST_SUITE_FINISHED_EVENT
            .value();

    /**
     * Constant value for ArtifactCreated CDEvent.
     */
    public static final String CDEVENTS_ART_CREATED = CDEventEnums.ArtifactCreatedEventV1.getEventType();

    /**
     * Constant value for ArtifactPublishedEvent CDEvent.
     */
    public static final String CDEVENTS_ART_PUBLISHED = CDEventEnums.ArtifactPublishedEventV1.getEventType();

    /**
     * Constant value for PipelineRunFinishedEvent CDEvent.
     */
    public static final String CDEVENTS_PIPELINERUN_FINISHED = CDEventEnums.PipelineRunFinishedEventV1.getEventType();

    /**
     * Constant Map for CDEvents to Eiffel Events.
     */
    public static final Map<String, String> CDEVENT_TO_EIFFEL;

    static {
        Map<String, String> eventsMap = new HashMap<>();
        eventsMap.put(CDEVENTS_ART_PUBLISHED, EIFFEL_ART_PUBLISHED);
        eventsMap.put(CDEVENTS_ART_CREATED, EIFFEL_ART_CREATED);
        eventsMap.put(CDEVENTS_PIPELINERUN_FINISHED, EIFFEL_ACTIVITY_FINISHED);

        CDEVENT_TO_EIFFEL = Collections.unmodifiableMap(eventsMap);
    }

}
