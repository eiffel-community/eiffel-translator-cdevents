package com.ericsson.event.translator.eiffel.events;

import com.ericsson.event.translator.eiffel.models.EiffelArtifactCreatedEventParams;
import com.ericsson.event.translator.eiffel.models.EiffelArtifactCreatedMsgParams;

public class EiffelArtifactCreatedEvent {
    public EiffelArtifactCreatedEvent() {
    }

    private EiffelArtifactCreatedMsgParams msgParams = new EiffelArtifactCreatedMsgParams();
    private EiffelArtifactCreatedEventParams eventParams = new EiffelArtifactCreatedEventParams();

    public EiffelArtifactCreatedEventParams getEventParams() {
        return eventParams;
    }

    public void setEventParams(EiffelArtifactCreatedEventParams eventParams) {
        this.eventParams = eventParams;
    }

    public EiffelArtifactCreatedMsgParams getMsgParams() {
        return msgParams;
    }

    public void setMsgParams(EiffelArtifactCreatedMsgParams msgParams) {
        this.msgParams = msgParams;
    }

    @Override
    public String toString() {
        return "EiffelArtifactCreatedEvent{" +
                "msgParams=" + msgParams +
                ", eventParams=" + eventParams +
                '}';
    }
}
