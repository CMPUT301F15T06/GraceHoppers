package com.gracehoppers.jlovas.bookwrm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by jlovas on 10/25/15.
 */
public class InventoryTest extends ActivityInstrumentationTestCase2 {

    public InventoryTest(){
        super(Inventory.class);
    }

    //***this is for the owner inventory only




    //test adding a book into inventory and is successfully added, test order of inventory
    public void testAddToInventory() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        Category testCategory = null;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        //create book 1
        Book book1 = new Book(testImage);
        book1.setTitle("Eragon");
        book1.setAuthor("Christopher Paolini");

        book1.setQuantity("1");

        book1.setCategory(testCategory.HARDBACK);
        book1.setDescription("None");
        book1.setQuality(4);
        book1.setIsPrivate(false);

        //one book
        testAccount.getInventory().addBook(book1);
        assertTrue(testAccount.getInventory().hasBook(book1));

        //create book 2 - allowing duplications? why not
        Book book2 = new Book(testImage);
        book2.setTitle("Tokyo Ghoul");
        book2.setAuthor("Not sure");

        book2.setQuantity("1");

        book2.setCategory(testCategory.PAPERBACK);
        book2.setDescription("None");
        book2.setQuality(4);
        book2.setIsPrivate(false);

        //add second book
        testAccount.getInventory().addBook(book2);
        assertTrue(testAccount.getInventory().getBookByTitle("Eragon") == book1); //going to count on the arrayAapter to display in its order - do we need to test the order? test display in UI test i think
        assertTrue(testAccount.getInventory().getBookByTitle("Tokyo Ghoul") == book2);
        assertFalse(testAccount.getInventory().getBookByTitle("TokyoGhoul") == book2);

    }

    //test finding a book when there are duplicates of the book stored, assuming we're allowing duplicates (i don't see why not)
    public void testFindDuplicateBooks() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        Category testCategory = null;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        //create book 1
        Book book1 = new Book(testImage);
        book1.setTitle("Eragon");
        book1.setAuthor("Christopher Paolini");

        book1.setQuantity("1");

        book1.setCategory(testCategory.HARDBACK);
        book1.setDescription("None");
        book1.setQuality(4);
        book1.setIsPrivate(false);


        //create book 2 - allowing duplications? why not
        Book book2 = new Book(testImage);
        book2.setTitle("Eragon");
        book2.setAuthor("Christopher Paolini");

        book2.setQuantity("1");

        book2.setCategory(testCategory.PAPERBACK);
        book2.setDescription("None");
        book2.setQuality(4);
        book2.setIsPrivate(false);

        //add the books to inventory, make sure they are there
        testAccount.getInventory().addBook(book1);
        assertTrue(testAccount.getInventory().hasBook(book1));
        testAccount.getInventory().addBook(book2);
        assertTrue(testAccount.getInventory().hasBook(book2));

        //try and search for the book by title - what should come back? a list?

    }

    //test editing a book - make sure the book you want to edit is pulled from the list
    //and is successfully edited

    //test if no items and how it is handled (class side instead of UI)

    //test connectivity? it's going to be its own class so idk

}
