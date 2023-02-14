package com.ericsson.event.translator.eiffel.events;

import com.ericsson.event.translator.eiffel.models.EiffelArtifactCreatedEventParams;
import com.ericsson.event.translator.eiffel.models.EiffelArtifactCreatedMsgParams;

public class EiffelArtifactCreatedEvent {

    private EiffelArtifactCreatedMsgParams msgParams = new EiffelArtifactCreatedMsgParams();
    private EiffelArtifactCreatedEventParams eventParams = new EiffelArtifactCreatedEventParams();

    /**
     * @return eventParams
     */
    public EiffelArtifactCreatedEventParams getEventParams() {
        return eventParams;
    }

    /**
     * @param eventParams
     */
    public void setEventParams(EiffelArtifactCreatedEventParams eventParams) {
        this.eventParams = eventParams;
    }

    /**
     * @return msgParams
     */
    public EiffelArtifactCreatedMsgParams getMsgParams() {
        return msgParams;
    }

    /**
     * @param msgParams
     */
    public void setMsgParams(EiffelArtifactCreatedMsgParams msgParams) {
        this.msgParams = msgParams;
    }

    @Override
    public String toString() {
        return "EiffelArtifactCreatedEvent{" + "msgParams=" + msgParams + ", eventParams=" + eventParams + '}';
    }
}
