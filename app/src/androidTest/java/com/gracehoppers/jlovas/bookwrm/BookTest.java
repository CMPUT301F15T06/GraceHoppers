package com.gracehoppers.jlovas.bookwrm;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by jlovas on 10/17/15.
 */
public class BookTest extends ActivityInstrumentationTestCase2 {

    public BookTest(){super(AddBookScreen.class);}

    //im testing for blanks
    //catch '-' quantities

    public void testAddBook() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException {

        //Need to create an account first to have an inventory to add to


        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        Category testCategory = null;

        Book book = new Book();
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setQuantity("1");

        book.setCategory(testCategory.HARDBACK);
        book.setDescription("None");
        book.setQuality(4);
        book.setIsPrivate(false);
        //will have to test picture later, relying on default for now

        //I need some way to access the inventory and add the book

        //books.add(book);
        //assertTrue(testAccount.books.hasItem(book));

    }


}
