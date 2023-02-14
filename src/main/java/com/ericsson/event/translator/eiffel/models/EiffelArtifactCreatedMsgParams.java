package com.ericsson.event.translator.eiffel.models;

import com.ericsson.eiffel.semantics.events.EiffelArtifactCreatedEventMeta;

public class EiffelArtifactCreatedMsgParams {

    private EiffelArtifactCreatedEventMeta meta = new EiffelArtifactCreatedEventMeta();

    /**
     * @return meta
     */
    public EiffelArtifactCreatedEventMeta getMeta() {
        return meta;
    }

    /**
     * @param meta
     */
    public void setMeta(EiffelArtifactCreatedEventMeta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "EiffelArtifactCreatedMsgParams{" + "meta=" + meta + '}';
    }
}
