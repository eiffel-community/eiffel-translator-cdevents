package com.ericsson.event.translator.eiffel.events;

import com.ericsson.event.translator.eiffel.models.EiffelActivityFinishedEventParams;
import com.ericsson.event.translator.eiffel.models.EiffelActivityFinishedMsgParams;

public class EiffelActivityFinishedEvent {
    public EiffelActivityFinishedEvent() {
    }

    private EiffelActivityFinishedMsgParams msgParams = new EiffelActivityFinishedMsgParams();
    private EiffelActivityFinishedEventParams eventParams = new EiffelActivityFinishedEventParams();

    public EiffelActivityFinishedMsgParams getMsgParams() {
        return msgParams;
    }

    public void setMsgParams(EiffelActivityFinishedMsgParams msgParams) {
        this.msgParams = msgParams;
    }

    public EiffelActivityFinishedEventParams getEventParams() {
        return eventParams;
    }

    public void setEventParams(EiffelActivityFinishedEventParams eventParams) {
        this.eventParams = eventParams;
    }

    @Override
    public String toString() {
        return "EiffelActivityFinishedEvent{" +
                "msgParams=" + msgParams +
                ", eventParams=" + eventParams +
                '}';
    }
}
