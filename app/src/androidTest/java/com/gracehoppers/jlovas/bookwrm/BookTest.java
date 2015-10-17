package com.gracehoppers.jlovas.bookwrm;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by jlovas on 10/17/15.
 */
public class BookTest extends ActivityInstrumentationTestCase2 {

    public BookTest(){super(AddBookScreen.class);}

    //im testing for blanks
    //catch '-' quantities

    public void testAddBook(){

        //Need to create an account first to have an inventory to add to
        Account testAccount = new Account("patricia", "patricia@gmail.com", "St.Albert");
        //assertTrue(accounts.contains(testAccount)); //***need account list for this

        //Book book = new Book("Eragon", "Christopher Paolini", 1, "Fantasy", 4, 0, 1, testphoto);
        //books.add(book);
        //assertTrue(testAccount.books.hasItem(book));

    }


}
