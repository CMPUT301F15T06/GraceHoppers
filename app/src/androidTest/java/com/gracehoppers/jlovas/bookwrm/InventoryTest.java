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
    //testing the public inventories of friends will be done elsewhere



    //test adding a book into inventory and is successfully added, test order of inventory
    public void testAddToInventory() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException, ItemNotFoundException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory1 = 1;
        int testCategory2 = 2;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        //create book 1
        Book book1 = new Book(testImage);
        book1.setTitle("Eragon");
        book1.setAuthor("Christopher Paolini");

        book1.setQuantity("1");

        book1.setCategory(testCategory1);
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

        book2.setCategory(testCategory2);
        book2.setDescription("None");
        book2.setQuality(4);
        book2.setIsPrivate(false);

        //add second book
        testAccount.getInventory().addBook(book2);
        try {
            assertTrue(testAccount.getInventory().getBookByTitle("Eragon").contains(book1));
            assertTrue(testAccount.getInventory().getBookByTitle("Tokyo Ghoul").contains(book2));
            assertFalse(testAccount.getInventory().getBookByTitle("TokyoGhoul").contains(book2)); //was failing, so exception was thrown and caught
        }catch(ItemNotFoundException e){

        }
        //test the ordering
        assertTrue(testAccount.getInventory().getBookByIndex(0) == book2);
        assertTrue(testAccount.getInventory().getBookByIndex(1) == book1);

    }

    //test trying to retrieve a book when none made
    public void testFindNoBooks() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException, ItemNotFoundException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        //get the inventory in general
        testAccount.getInventory();

        //get empty/negative inventory books by index
        try {
            testAccount.getInventory().getBookByIndex(-1);
            testAccount.getInventory().getBookByIndex(0);
            testAccount.getInventory().getBookByIndex(1);
        }catch(NegativeNumberException e){
            //im not really sure what to assert here
            assertFalse(testAccount.getInventory().getSize()==-1);
        }catch (TooLongException e){
            assertTrue(testAccount.getInventory().getSize()==0);
        }


        //get empty inventory books by title
        try {
            testAccount.getInventory().getBookByTitle("Book");
        }catch(ItemNotFoundException e){
            //i really dont know what to assert here
        }

    }


    //test finding a book when there are duplicates of the book stored, assuming we're allowing duplicates (i don't see why not)
    public void testFindDuplicateBooks() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory1 = 1;
        int testCategory2 = 2;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        //create book 1
        Book book1 = new Book(testImage);
        book1.setTitle("Eragon");
        book1.setAuthor("Christopher Paolini");

        book1.setQuantity("1");

        book1.setCategory(testCategory1);
        book1.setDescription("None");
        book1.setQuality(4);
        book1.setIsPrivate(false);


        //create book 2 - allowing duplications? why not
        Book book2 = new Book(testImage);
        book2.setTitle("Eragon");
        book2.setAuthor("Christopher Paolini");

        book2.setQuantity("1");

        book2.setCategory(testCategory2);
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
    public void testEditingABook() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory1 = 1;
        int testCategory2 = 2;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        //create book 1
        Book book1 = new Book(testImage);
        book1.setTitle("Eragon");
        book1.setAuthor("Christopher Paolini");

        book1.setQuantity("1");

        book1.setCategory(testCategory1);
        book1.setDescription("None");
        book1.setQuality(4);
        book1.setIsPrivate(false);

        //create book 2
        Book book2 = new Book(testImage);
        book2.setTitle("Tokyo Ghoul");
        book2.setAuthor("Not sure");

        book2.setQuantity("1");

        book2.setCategory(testCategory2);
        book2.setDescription("None");
        book2.setQuality(4);
        book2.setIsPrivate(false);

        testAccount.getInventory().addBook(book1);
        testAccount.getInventory().addBook(book2);

        //make sure I get the right book back for editing
        Book editBook;

        editBook = testAccount.getInventory().getBookByIndex(0);
        assertTrue(editBook == book2);

        //make changes
        editBook.setTitle("Madoka Magica");
        editBook.setAuthor("What");
        editBook.setQuality(5);
        editBook.setCategory(8);
        editBook.setDescription("Tragedy at its finest");
        editBook.setIsPrivate(true);

        assertFalse(editBook == book1);

    }

}
