package com.ericsson.event.translator.eiffel.models;

import com.ericsson.eiffel.semantics.events.EiffelArtifactPublishedEventMeta;

public class EiffelArtifactPublishedMsgParams {

    private EiffelArtifactPublishedEventMeta meta = new EiffelArtifactPublishedEventMeta();

    /**
     * @return meta
     */
    public EiffelArtifactPublishedEventMeta getMeta() {
        return meta;
    }

    /**
     * @param meta
     */
    public void setMeta(EiffelArtifactPublishedEventMeta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "EiffelArtifactPublishedMsgParams{" + "meta=" + meta + '}';
    }
}
