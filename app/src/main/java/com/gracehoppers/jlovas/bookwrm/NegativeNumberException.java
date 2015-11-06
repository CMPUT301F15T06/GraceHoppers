package com.gracehoppers.jlovas.bookwrm;

import java.io.IOException;

/**
 * @author  jlovas on 10/21/15.
 * thrown when a quantity number is made negative,
 * or when an inventory or friends list is accessed by an invalid index
 *@see Book, Inventory, Friends
 */
public class NegativeNumberException extends IOException {
}
