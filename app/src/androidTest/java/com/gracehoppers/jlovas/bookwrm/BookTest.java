package com.gracehoppers.jlovas.bookwrm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by jlovas on 10/17/15.
 */
public class BookTest extends ActivityInstrumentationTestCase2 {

    public BookTest(){super(Book.class);} //should this be Book or AddBookScreen? I changed it to Book, still works and makes more sense

  //***PLEASE NOTE*** none of the tests below are testing for an added picture, only the DEFAULT
  // Test for this once you figure that part out!

    public void testAddBook() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException {

        //Need to create an account first to have an inventory to add to

        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory1 = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setQuantity("1");

        book.setCategory(testCategory1);
        book.setDescription("None");
        book.setQuality(4);
        book.setIsPrivate(false);
        //will have to test picture later, relying on default for now

        //I need some way to access the inventory and add the book

        testAccount.getInventory().addBook(book);
        assertTrue(testAccount.getInventory().hasBook(book));
    }


    //test creation of a book with no title
    public void testsetTitle() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);

        book.setAuthor("Christopher Paolini");
        book.setQuantity("1");
        book.setCategory(testCategory);
        book.setDescription("None");
        book.setQuality(4);
        book.setIsPrivate(false);

        try{book.setTitle("");
        } catch (IllegalArgumentException e){
            assertFalse(book.getTitle()=="");
        }
    }

    //test creation of a book with no author
    public void testsetAuthor() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");


        book.setQuantity("1");

        book.setCategory(testCategory);
        book.setDescription("None");
        book.setQuality(4);
        book.setIsPrivate(false);


        try{book.setAuthor("");
        } catch (IllegalArgumentException e){
            assertFalse(book.getAuthor()=="");
        }
    }


    //test creation of book with blank quantity
    public void testsetBlankQuantity() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setCategory(testCategory);
        book.setDescription("None");
        book.setQuality(4);
        book.setIsPrivate(false);


        try{book.setQuantity(""); //should not accept blanks
        } catch (IllegalArgumentException e){
            assertFalse(book.getQuantity() == 0); //want this to be assertFalse
        }


    }

    //test creation of a book with negative quantity
    public void testsetQuantity() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setCategory(testCategory);
        book.setDescription("None");
        book.setQuality(4);
        book.setIsPrivate(false);


        try{book.setQuantity("0"); //should not accept quantities <1
        } catch (NegativeNumberException e){
            assertFalse(book.getQuantity() == 0); //want this to be assertFalse
        }


    }

    //test setting category and category return value (it's a string now for ease of display in UI)
    public void testsetCategory() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 7;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setQuantity("1");


        book.setDescription("None");
        book.setQuality(4);
        book.setIsPrivate(false);


        book.setCategory(testCategory);
        String testGetCategory = book.getCategory();

        assertTrue(testGetCategory.equals("Braille"));

    }


    //test creation of book with half quality value - these are pretty weak but im not sure how
    // to test something this secure. It made me change it from int to double though -TDD!
    public void testsetQuality() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setQuantity("1");

        book.setCategory(testCategory);
        book.setDescription("None");
        book.setIsPrivate(false);


        book.setQuality(0.5);

        assertTrue(book.getQuality() == 0.5);

    }


    //again, a secure widget, not sure how it could go wrong exactly
    //so just testing if it is what i set it to be
    //*will check if it is visible in public list once that is set up
    public void testsetPrivate() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException, BlankFieldException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setQuantity("1");

        book.setCategory(testCategory);
        book.setDescription("None");
        book.setQuality(4);



        book.setIsPrivate(true); //user must pick one

        assertTrue(book.isPrivate());

    }

    //comments are optional, but will test if the user opens the comments and types nothing,
    //will correct this to blank
    public void testsetComments() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setQuantity("1");

        book.setCategory(testCategory);
        book.setQuality(4);
        book.setIsPrivate(false);
        book.setDescription(""); //cant be blank

        assertFalse(book.getDescription().equals(""));
        assertTrue(book.getDescription().equals("None"));
        }

    //test adding a comment, very similar to above test
    public void testAddGoodComment() throws NoSpacesException, NegativeNumberException, IllegalEmailException, TooLongException{
        Account testAccount = new Account();
        testAccount.setUsername("Jill");
        testAccount.setEmail("jlovas@ualberta.ca");
        testAccount.setCity("GP");

        int testCategory = 1;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book = new Book(testImage);
        book.setTitle("Eragon");
        book.setAuthor("Christopher Paolini");

        book.setQuantity("1");

        book.setCategory(testCategory);
        book.setQuality(4);
        book.setIsPrivate(false);
        book.setDescription("Wow I've set a description!"); //cant be blank

        assertFalse(book.getDescription().equals(""));
        assertFalse(book.getDescription().equals("None"));
        assertTrue(book.getDescription().equals("Wow I've set a description!"));
    }


    //test connectvitiy ***cannot do this yet?

    //test user uploading a non-photograph file ***haven't looked into adding unique photos yet

    //test user adding a photo that is too large ***see above

    //test user adding a blank (0 bytes) file - is that even possible?

    //test connectivity ***will figure out later


}
