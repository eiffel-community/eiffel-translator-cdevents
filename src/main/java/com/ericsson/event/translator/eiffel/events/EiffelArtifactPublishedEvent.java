package com.ericsson.event.translator.eiffel.events;

import com.ericsson.event.translator.eiffel.models.EiffelArtifactPublishedEventParams;
import com.ericsson.event.translator.eiffel.models.EiffelArtifactPublishedMsgParams;

public class EiffelArtifactPublishedEvent {

    private EiffelArtifactPublishedMsgParams msgParams = new EiffelArtifactPublishedMsgParams();
    private EiffelArtifactPublishedEventParams eventParams = new EiffelArtifactPublishedEventParams();

    /**
     * @return msgParams
     */
    public EiffelArtifactPublishedMsgParams getMsgParams() {
        return msgParams;
    }

    /**
     * @param msgParams
     */
    public void setMsgParams(EiffelArtifactPublishedMsgParams msgParams) {
        this.msgParams = msgParams;
    }

    /**
     * @return eventParams
     */
    public EiffelArtifactPublishedEventParams getEventParams() {
        return eventParams;
    }

    /**
     * @param eventParams
     */
    public void setEventParams(EiffelArtifactPublishedEventParams eventParams) {
        this.eventParams = eventParams;
    }

    @Override
    public String toString() {
        return "EiffelArtifactPublishedEvent{" + "msgParams=" + msgParams + ", eventParams=" + eventParams + '}';
    }
}
