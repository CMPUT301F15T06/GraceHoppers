package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Tests for the AddBookScreen activity/class
 *
 *
 *
 */
public class AddBookScreenTest extends ActivityInstrumentationTestCase2 {

    public AddBookScreenTest() {
        super(AddBookScreen.class);
    }

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testAddBook(){
        //when you call getActivity, android will start the app
        AddBookScreen activity = (AddBookScreen) getActivity();

        //reset the app to a known state
        activity.getBooks().clear();

        //add a book using UI

        //fill the title field
        final EditText titleText = activity.getTitleText();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleText.setText("Fangirl");
            }
        });
        getInstrumentation().waitForIdleSync();

        //fill the author field
        final EditText authorText = activity.getAuthorText();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                authorText.setText("Rainbow Rowell");
            }
        });
        getInstrumentation().waitForIdleSync();

        //select the quantity (2)
        final EditText quantityText = activity.getQuantityText();
        final Button plusButton = activity.getPlusButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                plusButton.performClick();
                //quantityText.setText("2");
            }
        });
        getInstrumentation().waitForIdleSync();

        //select the quality of the book
        final RatingBar stars = activity.getStars();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //stars.performHapticFeedback(5);
                stars.setRating(5);
            }
        });
        getInstrumentation().waitForIdleSync();


        //make the book private, just to test it
        final CheckBox isPrivate = activity.getPrivateCheckBox();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isPrivate.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        //select a category
        final Spinner spinner = activity.getMySpinner();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Not sure how to do it here
                //spinner.performClick();
                //spinner.performItemClick(v,5,  )
            }
        });
        getInstrumentation().waitForIdleSync();

        //Comment section in another activity

        //all fields ready, ok
        final Button okButton = activity.getOkButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                okButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        //At this point, the book should be added to the list

        ArrayList<Book> books = activity.getBooks();

        assertEquals("Fangirl", books.get(0).getTitle());
    }

}
