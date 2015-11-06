package com.gracehoppers.jlovas.bookwrm;

import java.io.IOException;

/**
 * @author nlovas on 10/17/15.
 * thrown when an input that does not allow spaces receives input with spaces.
 * @see Account,
 */
public class NoSpacesException extends IOException {
    public NoSpacesException() {
    }
}
