package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

/**
 * Created by jlovas on 10/17/15.****going to come back to this, going to write demoAccount in first
 */
public class HomeScreenTest extends ActivityInstrumentationTestCase2 {

    public HomeScreenTest() {
        super(com.gracehoppers.jlovas.bookwrm.HomeScreen.class);
    }

    //tests for the homescreen can go here
    //Much of this code is referenced directly or altered from the CMPUT 301 labs. University of Alberta:
    //TA's Joshua Campbell, Shida He, Stephen Romansky, and Ian Watts, 2015-15-10

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testHomeScreen(){

        //tests that the Homescreen UI behaves as expected
        HomeScreen activity = (HomeScreen) getActivity();

        //reset the app to a known state
        activity.getInventory().clear();

        //test tapping a book entry
        //requires a book to be made first
        Account accountA = new Account();
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");
        Book book = new Book(testImage); //has a value at defaul so im not going to boher giving it any
        accountA.getInventory().addBook(book);

        final ListView inventoryList = activity.getInventoryList();
        //Book gotBook = (Book) inventoryList.getItemAtPosition(0);

        //click on the book - ensure the viewBookActivity starts up

        //The following is from: https://developer.android.com/training/activity-testing/activity-functional-testing.html, Oct 14, 15, Nov 3, 15

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ViewBookActivity.class.getName(),
                        null, false);
        //do the click action

        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = inventoryList.getChildAt(0);
                inventoryList.performItemClick(v, 0,v.getId());
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

        //test that the bookscreen starts up with the right book in it
        //just opened the activity from above
        //is this the book we clicked?

        //nikki needs to push her UI tests for viewBookScreen so i dont make a collision
        //return to this after
        /*
        titleField = receiverActivity.getTitleField();
        //make sure editTweetActivity gets tweet selected from the original
        receiverActivity.runOnUiThread(new Runnable() {
            public void run() {
                //want to grab what is clicked? How?
                receiverActivity.getEditTweetField();
            }
        });
        getInstrumentation().waitForIdleSync();

*/
    }

    public void testNavigateToAddBook(){
        //test navigation to addBookScreen




    }

    public void testNavigateToFriends(){
        //test navigation to FriendsScreen

    }

    public void testNavigateToTrades(){
        //test navigation to TradesScreen


    }

    public  void testNavigateToProfile(){
        //test navigation to Profile Screen
    }

}
