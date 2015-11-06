package com.gracehoppers.jlovas.bookwrm;

import java.io.IOException;

/**
 * @author nlovas on 10/17/15.
 *thrown when an email does not have an "@" in it.
 * @see Account
 */
public class IllegalEmailException extends IOException {
    public IllegalEmailException() {
    }
}
