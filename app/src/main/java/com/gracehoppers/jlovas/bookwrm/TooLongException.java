package com.gracehoppers.jlovas.bookwrm;

import java.io.IOException;

/**
 * @author nlovas on 10/21/15.
 * thrown when a user's input is too long,
 * or if an inventory or friendslist is accessed with a number larger than its size
 * @see Account, Inventory, Friends
 */
public class TooLongException extends IOException {
    public TooLongException() {
    }
}
