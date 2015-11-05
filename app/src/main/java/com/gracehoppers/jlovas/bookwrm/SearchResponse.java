package com.gracehoppers.jlovas.bookwrm;

/**
 * Created by dzhang4 on 11/3/15.
 */
public class SearchResponse<T> {
    private Hits<T> hits;
    public SearchResponse() {}
    public Hits<T> getHits() {
        return hits;
    }
}
