package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by nlovas on 11/5/15.
 */
public class EditBookActivityTest extends ActivityInstrumentationTestCase2 {
    /*
    UI tests for the edit book screen
     */

    Book book;
    Account account;
    EditText title;
    EditText author;
    EditText quantity;
    RatingBar ratingbar;
    Spinner spinner;
    CheckBox checkbox;
    Button plusbutton;
    Button minusbutton;
    Button okButton;
    TextView comments;
    SaveLoad saveload;



    public EditBookActivityTest() {
        super(com.gracehoppers.jlovas.bookwrm.EditBookActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testEdit(){
        //test to see that the information can be changed with the UI
        saveload = new SaveLoad();
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
        intenty.putExtra("bookTitle", book.getTitle());
        intenty.putExtra("bookAuthor", book.getAuthor());
        intenty.putExtra("bookQuantity", book.getQuantity());
        intenty.putExtra("bookQuality", book.getQuality());
        intenty.putExtra("bookCategory", book.getCategoryNumber());
        intenty.putExtra("bookPrivacy", book.isPrivate());
        intenty.putExtra("bookDesc", book.getDescription());
        intenty.putExtra("bookPosition", 0);
        setActivityIntent(intenty);
        EditBookActivity activity = (EditBookActivity)getActivity();


        account.getInventory().addBook(book);
        assertTrue(account.getInventory().getSize() == 1);
        //book has been created and added to inventory
        saveload.saveInFile(this.getInstrumentation().getTargetContext().getApplicationContext(), account);

        title = activity.getBookTitle();
        author = activity.getBookAuthor();
        quantity = activity.getBookQuantity();
        ratingbar = activity.getRatingBar();
        spinner = activity.getSpinner();
        checkbox = activity.getCheckBox();
        plusbutton = activity.getPlusButton();
        minusbutton = activity.getMinusButton();
        okButton = activity.getOkButton();
        comments = activity.getComments();


        //change the title and author

        activity.runOnUiThread(new Runnable() {
            public void run() {
                title.setText("Harry Slaughter");
            }
        });
        getInstrumentation().waitForIdleSync();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                author.setText("J.K.ROFLMAO");
            }
        });
        getInstrumentation().waitForIdleSync();

        //change the quantity

        activity.runOnUiThread(new Runnable() {
            public void run() {
                quantity.setText("11");
            }
        });
        getInstrumentation().waitForIdleSync();

        activity.runOnUiThread(new Runnable() {

            public void run() {
                plusbutton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        activity.runOnUiThread(new Runnable() {

            public void run() {
                minusbutton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
//quantity should be 11

        //change quality

        activity.runOnUiThread(new Runnable() {

            public void run() {
                ratingbar.setRating(4);
            }
        });
        getInstrumentation().waitForIdleSync();

        //put spinner stuff here:

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              //come back to the spinner later
            }
        });
        getInstrumentation().waitForIdleSync();

//change the privacy to private
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkbox.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();


        assertTrue(title.getText().toString().equals("Harry Slaughter"));
        assertTrue(author.getText().toString().equals("J.K.ROFLMAO"));
        assertTrue(quantity.getText().toString().equals("11"));
        assertTrue(ratingbar.getRating()==4);
        assertTrue(checkbox.isChecked());

//test to see if the comments textview takes you to the correct activity

        //following from https://developer.android.com/training/activity-testing/activity-functional-testing.html ,2015-5-11
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddCommentsScreen.class.getName(),
                        null, false);


        //code where they clicked was here
       activity.runOnUiThread(new Runnable() {

            public void run() {
                comments.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        AddCommentsScreen receiverActivity = (AddCommentsScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddCommentsScreen.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        account.getInventory().getInventory().clear();


    }




}
