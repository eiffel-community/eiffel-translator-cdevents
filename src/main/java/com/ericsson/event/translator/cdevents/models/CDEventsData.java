package com.ericsson.event.translator.cdevents.models;

public class CDEventsData {

    private String eventId;
    private String eventName;
    private String contextId;
    private String triggerId;
    private String subject;
    private String artifactId;
    private String artifactName;

    /**
     * @return eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * @param eventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * @return eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return contextId
     */
    public String getContextId() {
        return contextId;
    }

    /**
     * @param contextId
     */
    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    /**
     * @return triggerId
     */
    public String getTriggerId() {
        return triggerId;
    }

    /**
     * @param triggerId
     */
    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * @param artifactId
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    /**
     * @return artifactName
     */
    public String getArtifactName() {
        return artifactName;
    }

    /**
     * @param artifactName
     */
    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    @Override
    public String toString() {
        return "CDEventData{" + "eventId='" + eventId + '\'' + ", eventName='" + eventName + '\'' + ", contextId='"
                + contextId + '\'' + ", triggerId='" + triggerId + '\'' + ", subject='" + subject + '\''
                + ", artifactId='" + artifactId + '\'' + ", artifactName='" + artifactName + '\'' + '}';
    }
}
