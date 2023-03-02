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
package tech.est.eiffel.translator.cdevents.models;

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
