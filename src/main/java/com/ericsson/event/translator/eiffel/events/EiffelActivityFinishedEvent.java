package com.ericsson.event.translator.eiffel.events;

import com.ericsson.event.translator.eiffel.models.EiffelActivityFinishedEventParams;
import com.ericsson.event.translator.eiffel.models.EiffelActivityFinishedMsgParams;

public class EiffelActivityFinishedEvent {

    private EiffelActivityFinishedMsgParams msgParams = new EiffelActivityFinishedMsgParams();
    private EiffelActivityFinishedEventParams eventParams = new EiffelActivityFinishedEventParams();

    /**
     * @return msgParams
     */
    public EiffelActivityFinishedMsgParams getMsgParams() {
        return msgParams;
    }

    /**
     * @param msgParams
     */
    public void setMsgParams(EiffelActivityFinishedMsgParams msgParams) {
        this.msgParams = msgParams;
    }

    /**
     * @return eventParams
     */
    public EiffelActivityFinishedEventParams getEventParams() {
        return eventParams;
    }

    /**
     * @param eventParams
     */
    public void setEventParams(EiffelActivityFinishedEventParams eventParams) {
        this.eventParams = eventParams;
    }

    @Override
    public String toString() {
        return "EiffelActivityFinishedEvent{" + "msgParams=" + msgParams + ", eventParams=" + eventParams + '}';
    }
}
