package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This test tests editing a book in your inventory.
 * This use case will start on the homescreen.
 * This test requires data on the phone! Make sure there are books made!
 * Requires connection to the internet/server.
 *
 * Created by jlovas on 11/29/15.
 */
public class UseCase3 extends ActivityInstrumentationTestCase2 {

    public UseCase3(){
        super(com.gracehoppers.jlovas.bookwrm.HomeScreen.class);
    }

    public void testEditBook(){

        //build intent to go to viewBookActivity
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        Intent intenty = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), HomeScreen.class);
        intenty.putExtra("username", "MockAccount");
        setActivityIntent(intenty);

        HomeScreen activity = (HomeScreen)getActivity();

        //first need a book and account
        SaveLoad saveLoad =activity.getSaveload();
        Account accountA = saveLoad.loadFromFile(context);

        //requires data in the phone - **working on assumption that there are books already made!**

        //1. User find item in inventory and views it
        //2. System requests item data from local storage
        final ListView inventoryList = activity.getInventoryList();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ViewBookActivity.class.getName(),
                        null, false);

        assertTrue(inventoryList.getChildCount() >0);

        //do the click action on a list item

        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = inventoryList.getChildAt(0);
                inventoryList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final ViewBookActivity receiverActivity = (ViewBookActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ViewBookActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        //3. System directs the owner to the item's info page (ViewBookActivity)
        //4. Owner select edit to edit this item
        //4.5 System directs the user to the edit page

        final Button editButton = receiverActivity.getEditButton();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActMon2 =
                getInstrumentation().addMonitor(EditBookActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final EditBookActivity receiver2 = (EditBookActivity)
                receiverActMon2.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActMon2.getHits());
        assertEquals("Activity is of wrong type",
                EditBookActivity.class, receiver2.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActMon2);

        //now we're in the editBookActivity! Yay!

        //5. Owner changes one or more text field and save
        final TextView titleText = receiver2.getBookTitle();
        final TextView authorText = receiver2.getBookAuthor();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                titleText.setText("Unwind");
                authorText.setText("Neal Shustermann");
            }
        });
        getInstrumentation().waitForIdleSync();

        //hit ok - in editBookActivity - to land in ViewBookActivity

        final Button okButton = receiver2.getOkButton();

        //setting up an onActivityResult thing for a finish() function call
        Intent returnIntent = new Intent();
        returnIntent.putExtra("uninum", 0);
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, returnIntent);

        // Create an ActivityMonitor that catch ChildActivity and return mock ActivityResult:
        Instrumentation.ActivityMonitor aM = getInstrumentation().addMonitor(ViewBookActivity.class.getName(), activityResult , true);


        activity.runOnUiThread(new Runnable() {
            public void run() {
                okButton.performClick();
            }
        });
        //getInstrumentation().waitForIdleSync();

        // Wait for the ActivityMonitor to be hit, Instrumentation will then return the mock ActivityResult:
        ViewBookActivity childActivity = (ViewBookActivity)getInstrumentation().waitForMonitorWithTimeout(aM, 5);

        //6. System updates item with the new data on server
        //7. System directs owner to the item's info page

        //http://stackoverflow.com/questions/13042015/testing-onactivityresult
        //Activity childActivity = this.getInstrumentation().waitForMonitorWithTimeout(am, TIME_OUT);

        String temp =receiverActivity.getBookTitle().toString();
        assertTrue(temp.equals("Unwind")); //why is temp a weird string and not what i expect?

    }

}
