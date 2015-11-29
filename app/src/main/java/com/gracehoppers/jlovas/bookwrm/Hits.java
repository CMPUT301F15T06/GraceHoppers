package com.gracehoppers.jlovas.bookwrm;

import java.util.List;

/**
 * Hits grabs the hits.
 *
 * @author dzhang4 on 11/3/15.
 *
 */
public class Hits<T> {
    private int total;
    private List<SearchHit<T>> hits;
    public Hits() {}
    public List<SearchHit<T>> getHits() {return hits;}
    public int getTotal() {
        return total;
    }
}
