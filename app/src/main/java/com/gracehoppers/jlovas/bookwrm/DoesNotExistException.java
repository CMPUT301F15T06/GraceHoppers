package com.gracehoppers.jlovas.bookwrm;

import java.io.IOException;

/**
 * @author jlovas on 11/4/15.
 * thrown when an account is trying to be accessed that does not exist
 * when the program searches for a friend by username and gets no results
 * @see Account
 */
public class DoesNotExistException extends IOException {
}
