package com.gracehoppers.jlovas.bookwrm;

/**
 *
 * Contains a collection of Hits returned form a search.
 * Contains a Getter for this.
 * @author Linda Zhang
 */
public class SearchResponse<T> {


    private Hits<T> hits;
    public SearchResponse() {}
    public Hits<T> getHits() {
        return hits;
    }
}
