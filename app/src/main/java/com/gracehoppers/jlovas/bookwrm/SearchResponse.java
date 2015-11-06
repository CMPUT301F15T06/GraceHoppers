package com.gracehoppers.jlovas.bookwrm;

/**
 * @deprecated until part 5
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
