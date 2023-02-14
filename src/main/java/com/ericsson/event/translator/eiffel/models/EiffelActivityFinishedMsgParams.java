package com.ericsson.event.translator.eiffel.models;

import com.ericsson.eiffel.semantics.events.EiffelActivityFinishedEventMeta;

public class EiffelActivityFinishedMsgParams {

    private EiffelActivityFinishedEventMeta meta = new EiffelActivityFinishedEventMeta();

    /**
     * @return meta
     */
    public EiffelActivityFinishedEventMeta getMeta() {
        return meta;
    }

    /**
     * @param meta
     */
    public void setMeta(EiffelActivityFinishedEventMeta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "EiffelActivityFinishedEventMsgParams{" + "meta=" + meta + '}';
    }
}
