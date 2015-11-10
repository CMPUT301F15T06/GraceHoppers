package com.gracehoppers.jlovas.bookwrm;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by jlovas on 11/5/15.
 */
public class AddCommentsScreenTest extends ActivityInstrumentationTestCase2 {

    public AddCommentsScreenTest(){super(AddCommentsScreen.class);}

    //test adding a comment and hit ok - what else can you do?
    public void testAddingAComment(){

        Intent intent = new Intent();
        intent.putExtra("flag", "edit"); //not sure how to test the one without a flag - have other one include a flag as well?
        setActivityIntent(intent);

        AddCommentsScreen activity = (AddCommentsScreen)getActivity();

        final EditText addComment = activity.getCommentField();

        //give it some text
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addComment.setText("Wow what a lovely comment!");
            }
        });

        getInstrumentation().waitForIdleSync();


        //Coming up quite often: how do I test that my activity calls finish() and lands in parent activity?

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddBookScreen.class.getName(),
                        null, false);
        //the code where they clicked was here

            //click ok button
            final Button okButton = activity.getOkButton();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    okButton.performClick();
                }
            });

        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        ViewBookActivity receiverActivity = (ViewBookActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddBookScreen.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        //clean up our activities at the end of the test
        receiverActivity.finish();

    }

}
