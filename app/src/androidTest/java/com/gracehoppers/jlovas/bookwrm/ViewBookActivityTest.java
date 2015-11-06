package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import org.apache.http.impl.conn.SingleClientConnManager;

/**
 * Created by nlovas on 11/3/15.
 */
public class ViewBookActivityTest extends ActivityInstrumentationTestCase2 {

    /*
    UI testing for the view book activity
     */

    private Button editButton;
    private Button deleteButton;
    private AlertDialog SingleInfo;
    private Button yesButton;
    private Button cancelButton;
    private SaveLoad saveload = new SaveLoad();

    public ViewBookActivityTest() {
        super(com.gracehoppers.jlovas.bookwrm.ViewBookActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    //First: Testing for viewing your OWN book!
    public void testDeleteBook(){
//test to see if delete button process works as expected.

        //first start out in homescreen and set up a book to view
        //HomeScreen activity1 = (HomeScreen)getActivity();

        Intent intent = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), ViewBookActivity.class);
        intent.putExtra("flag", "Homescreen");
        intent.putExtra("listPosition",0);
        setActivityIntent(intent);
        ViewBookActivity activity = (ViewBookActivity)getActivity();

        Account account = new Account();
        Book book = new Book();
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

        Book book2 = new Book();
        try {

            book2= account.getInventory().getBookByIndex(0);
        } catch (NegativeNumberException e) {
            assertTrue(book2==book);
        } catch (TooLongException e) {
            assertTrue(book2==book);
        }

        assertTrue(book2 == book); //assert that the book has not been deleted



        //test that the book is deleted and were sent back to homescreen when you click ok in alertdialogue

        activity.runOnUiThread(new Runnable() {

            public void run() {
                deleteButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        SingleInfo = activity.getAlertDialog(); //find the alert dialogue thats asks if youre sure
        yesButton = SingleInfo.getButton(SingleInfo.BUTTON_POSITIVE); //yes (delete) button


        activity.runOnUiThread(new Runnable() {

            public void run() {
                yesButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertTrue(account.getInventory().getSize()==0);
        assertFalse(account.getInventory().hasBook(book)); //assert that the book has been deleted



    }
}
