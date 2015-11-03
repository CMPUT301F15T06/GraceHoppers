package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
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

    public ViewBookActivityTest() {
        super(com.gracehoppers.jlovas.bookwrm.ViewBookActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testDeleteBook(){
//test to see if delete button process works as expected
        ViewBookActivity activity = (ViewBookActivity)getActivity();

        deleteButton = activity.getDeleteButton();

        activity.runOnUiThread(new Runnable() {

            public void run() {
                deleteButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        SingleInfo = activity.getAlertDialog(); //find the alert dialogue thats asks if youre sure

        SingleInfo.getButton(SingleInfo.BUTTON_POSITIVE); //yes (delete) button
        SingleInfo.getButton(SingleInfo.BUTTON_NEGATIVE); //cancel button

        //test that nothing happens when you click cancel


        //test that the book is deleted and were sent back to homescreen when you click ok in alertdialogue



    }


}
