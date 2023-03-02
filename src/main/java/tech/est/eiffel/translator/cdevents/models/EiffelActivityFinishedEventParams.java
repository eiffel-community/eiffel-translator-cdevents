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

import java.util.ArrayList;

import com.ericsson.eiffel.semantics.events.EiffelActivityFinishedEventData;
import com.ericsson.eiffel.semantics.events.Link;

public class EiffelActivityFinishedEventParams {

    private EiffelActivityFinishedEventData data = new EiffelActivityFinishedEventData();

    private ArrayList<Link> links = new ArrayList<>();

    /**
     * @return data
     */
    public EiffelActivityFinishedEventData getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(EiffelActivityFinishedEventData data) {
        this.data = data;
    }

    /**
     * @return links
     */
    public ArrayList<Link> getLinks() {
        return links;
    }

    /**
     * @param links
     */
    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "EiffelArtifactPublishedEventParams{" + "data=" + data + ", links=" + links + '}';
    }
}
