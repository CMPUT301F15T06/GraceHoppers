package com.gracehoppers.jlovas.bookwrm;

import java.io.IOException;

/**
 * @author jlovas on 10/19/15.
 * thrown when a user leaves a non-optional field blank
 *
 */
public class BlankFieldException extends IOException{
    public BlankFieldException(){}
}
