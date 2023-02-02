package com.ericsson.event.translator.eiffel.models;

import com.ericsson.eiffel.semantics.events.EiffelArtifactCreatedEventMeta;

public class EiffelArtifactCreatedMsgParams {

    public EiffelArtifactCreatedMsgParams() {
    }

    private EiffelArtifactCreatedEventMeta meta = new EiffelArtifactCreatedEventMeta();

    public EiffelArtifactCreatedEventMeta getMeta() {
        return meta;
    }

    public void setMeta(EiffelArtifactCreatedEventMeta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "EiffelArtifactCreatedMsgParams{" +
                "meta=" + meta +
                '}';
    }
}
