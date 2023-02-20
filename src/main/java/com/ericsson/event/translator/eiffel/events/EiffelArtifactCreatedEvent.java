/*
    Copyright (C) 2023 Nordix Foundation.
    For a full list of individual contributors, please see the commit history.
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
          http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    SPDX-License-Identifier: Apache-2.0
*/
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
