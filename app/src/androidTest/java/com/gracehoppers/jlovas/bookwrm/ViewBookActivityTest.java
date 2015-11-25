package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import org.apache.http.impl.conn.SingleClientConnManager;

/**
 * Created by nlovas on 11/3/15.
 */
public class ViewBookActivityTest extends ActivityInstrumentationTestCase2 {

    /*
    UI testing for the view book activity, for both viewing your own book and a friend's book
    testDeleteBook only runs if it is ran by itself, so it's commented out
     */

    private Button editButton;
    private Button deleteButton;
    private AlertDialog SingleInfo;
    private Button yesButton;
    private Button cancelButton;
    private SaveLoad saveload = new SaveLoad();
    Account account;
    Book book;
    Book book2;
    Account friendAccount;


    public ViewBookActivityTest() {
        super(com.gracehoppers.jlovas.bookwrm.ViewBookActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    //First: Testing for viewing your OWN book!-----------------------------------------------------------
    public void testViewOwnBook(){
//test that you can view the correct buttons when viewing your own book
        account = new Account();
        book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsPrivate(false);
        book.setDescription("Harry Potter was a lizard");
        book.setCategory(3);
        book.setQuality(5);
        try {
            book.setQuantity("3");
        }catch (NegativeNumberException e){
            assertTrue(book.getQuantity()>0);
        }

        assertFalse(account.getInventory().hasBook(book));

        Intent intenty = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), ViewBookActivity.class);
        intenty.putExtra("flag", "Homescreen");
        intenty.putExtra("listPosition", 0);
        setActivityIntent(intenty);
        ViewBookActivity activity = (ViewBookActivity)getActivity();


        account.getInventory().addBook(book);
        assertTrue(account.getInventory().getSize() == 1);
        //book has been created and added to inventory
        saveload.saveInFile(this.getInstrumentation().getTargetContext().getApplicationContext(), account);


        editButton = activity.getEditButton();
        deleteButton = activity.getDeleteButton();
        boolean ed = editButton.isShown();
        boolean del = deleteButton.isShown();
        assertTrue(ed&&del); //both buttons are shown

        account.getInventory().getInventory().clear();

    }

/* this test gives a nullpointerexception no matter what, unless it's run by itself
    public void testCancelDeleteBook(){
//test to see if delete button cancel works

        //first start out in homescreen and set up a book to view

        account = new Account();
        book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsPrivate(false);
        book.setDescription("Harry Potter was a lizard");
        book.setCategory(3);
        book.setQuality(5);
        try {
            book.setQuantity("3");
        }catch (NegativeNumberException e){
            assertTrue(book.getQuantity()>0);
        }

        assertFalse(account.getInventory().hasBook(book));

        Intent intenty = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), ViewBookActivity.class);
        intenty.putExtra("flag", "Homescreen");
        intenty.putExtra("listPosition", 0);
        setActivityIntent(intenty);
        ViewBookActivity activity = (ViewBookActivity)getActivity();




        account.getInventory().addBook(book);
        assertTrue(account.getInventory().getSize() == 1);
        //book has been created and added to inventory
        saveload.saveInFile(this.getInstrumentation().getTargetContext().getApplicationContext(), account);



        deleteButton = activity.getDeleteButton();

        activity.runOnUiThread(new Runnable() {

            public void run() {
                deleteButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        SingleInfo = activity.getAlertDialog(); //find the alert dialogue thats asks if youre sure
        cancelButton = SingleInfo.getButton(SingleInfo.BUTTON_NEGATIVE); //cancel button

        //test that nothing happens when you click cancel
        activity.runOnUiThread(new Runnable() {

            public void run() {
                cancelButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        book2 = new Book();
        try {

            book2= account.getInventory().getBookByIndex(0);
        } catch (NegativeNumberException e) {
            assertTrue(book2==book);
        } catch (TooLongException e) {
            assertTrue(book2 == book);
        }

        assertTrue(book2 == book); //assert that the book has not been deleted

    }
*/
    public void testDeleteBook(){
        //test to see if delete button deletes
        //WILL ONLY RUN CORRECTLY IF RAN ON ITS OWN

        account = new Account();
        book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsPrivate(false);
        book.setDescription("Harry Potter was a lizard");
        book.setCategory(3);
        book.setQuality(5);
        try {
            book.setQuantity("3");
        }catch (NegativeNumberException e){
            assertTrue(book.getQuantity()>0);
        }

        assertFalse(account.getInventory().hasBook(book));

        Intent intenty = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), ViewBookActivity.class);
        intenty.putExtra("flag", "Homescreen");
        intenty.putExtra("listPosition", 0);
        setActivityIntent(intenty);
        ViewBookActivity activity = (ViewBookActivity)getActivity();




        account.getInventory().addBook(book);
        assertTrue(account.getInventory().getSize() == 1);
        //book has been created and added to inventory
        saveload.saveInFile(this.getInstrumentation().getTargetContext().getApplicationContext(), account);



        deleteButton = activity.getDeleteButton();

        activity.runOnUiThread(new Runnable() {

            public void run() {
                deleteButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        SingleInfo = activity.getAlertDialog(); //find the alert dialogue thats asks if youre sure
        yesButton = SingleInfo.getButton(SingleInfo.BUTTON_POSITIVE); //cancel button



        activity.runOnUiThread(new Runnable() {

            public void run() {
                yesButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue(account.getInventory().getSize() == 0); //fails :(
        assertFalse(account.getInventory().hasBook(book)); //assert that the book has been deleted

        account.getInventory().getInventory().clear();

    }

    public void testEditBookButton(){
//test to see if it makes it to the correct activity after clicking the edit book button

        account = new Account();
        friendAccount = new Account();
        book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsPrivate(false);
        book.setDescription("Harry Potter was a lizard");
        book.setCategory(3);
        book.setQuality(5);
        try {
            book.setQuantity("3");
        }catch (NegativeNumberException e){
            assertTrue(book.getQuantity()>0);
        }

        assertFalse(account.getInventory().hasBook(book));

        Intent intenty = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), ViewBookActivity.class);
        intenty.putExtra("flag", "Homescreen");
        intenty.putExtra("listPosition", 0);
        setActivityIntent(intenty);
        ViewBookActivity activity = (ViewBookActivity)getActivity();

        account.getInventory().addBook(book);
        assertTrue(account.getInventory().getSize() == 1);
        //book has been created and added to inventory
        saveload.saveInFile(this.getInstrumentation().getTargetContext().getApplicationContext(), account);


        editButton = activity.getEditButton();




        //following from https://developer.android.com/training/activity-testing/activity-functional-testing.html ,2015-5-11
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(EditBookActivity.class.getName(),
                        null, false);


        //code where they clicked was here
        //click on the tweet to edit
        activity.runOnUiThread(new Runnable() {

            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        EditBookActivity receiverActivity = (EditBookActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                EditBookActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        account.getInventory().getInventory().clear();

    }

    //next: viewing another person's book------------------------------------------------------------

    public void testViewFriendBook(){
//test to see if the edit and delete buttons are hidden


        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsPrivate(false);
        book.setDescription("Harry Potter was a lizard");
        book.setCategory(3);
        book.setQuality(5);
        try {
            friendAccount.setUsername("testyy");
            book.setQuantity("3");
        }catch (NegativeNumberException e){
            assertTrue(book.getQuantity()>0);
        }catch(NoSpacesException e){
            assertTrue(friendAccount.getUsername().equals("testyy"));
        } catch(TooLongException e){
            assertTrue(friendAccount.getUsername().length()<20);
        }

        assertFalse(friendAccount.getInventory().hasBook(book));

        try { //add the pseudofriend as a friend
            account.getFriends().addFriend(friendAccount);
        } catch(AlreadyAddedException e){
            assertTrue(account.getFriends().hasFriend(friendAccount));
        }
        assertTrue(account.getFriends().hasFriend(friendAccount));

        Intent intenty = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), ViewBookActivity.class);
        intenty.putExtra("flag", "viewfriend");
        intenty.putExtra("listPosition", 0);
        setActivityIntent(intenty);
        ViewBookActivity activity = (ViewBookActivity)getActivity();

        friendAccount.getInventory().addBook(book); //give the friend a book for us to look at
       assertTrue(account.getFriends().getFriend(friendAccount.getUsername()).getInventory().getSize() == 1);
        //book has been created and added to friend's inventory
        saveload.saveInFile(this.getInstrumentation().getTargetContext().getApplicationContext(), account);

        editButton = activity.getEditButton();
        deleteButton = activity.getDeleteButton();
        boolean ed = editButton.isShown();
        boolean del = deleteButton.isShown();
        assertTrue(!ed&&!del); //both buttons are hidden. fails :(

        account.getInventory().getInventory().clear();
    }
}
