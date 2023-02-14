package com.ericsson.event.translator.eiffel.models;

import com.ericsson.eiffel.semantics.events.EiffelArtifactPublishedEventData;
import com.ericsson.eiffel.semantics.events.Link;

import java.util.ArrayList;

public class EiffelArtifactPublishedEventParams {

    private EiffelArtifactPublishedEventData data = new EiffelArtifactPublishedEventData();

    private ArrayList<Link> links = new ArrayList<>();

    /**
     * @return data
     */
    public EiffelArtifactPublishedEventData getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(EiffelArtifactPublishedEventData data) {
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
