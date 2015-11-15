package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    //tests that the Homescreen UI behaves as expected with items in it
    public void testHomeScreen() throws NegativeNumberException, TooLongException{



        //pass intent to it for the getExtra stuff!
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        Intent intent = new Intent();
        intent.putExtra("username", "hello");
        setActivityIntent(intent);
        HomeScreen activity = (HomeScreen) getActivity();


        //test tapping a book entry
        //requires a book to be made first
        SaveLoad saveLoad =activity.getSaveload();
        Account accountA = saveLoad.loadFromFile(context);

        //reset the app to a known state
        //activity.getInventory().clear();

        //ArrayList<Book> inventory = activity.getInventory();

        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");
        Book book = new Book(testImage); //has a value at default so im not going to bother giving it any
        accountA.getInventory().addBook(book);

        final ListView inventoryList = activity.getInventoryList();

        final ArrayAdapter<Book> adapter = (ArrayAdapter) inventoryList.getAdapter();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        getInstrumentation().waitForIdleSync();

        //click on the book - ensure the viewBookActivity starts up

        //The following is from: https://developer.android.com/training/activity-testing/activity-functional-testing.html, Oct 14, 15, Nov 3, 15

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

        //test that the bookscreen starts up with the right book in it
        //just opened the activity from above
        //is this the book we clicked?


        final TextView bookTitle = receiverActivity.getBookTitle();
        final TextView bookAuthor = receiverActivity.getBookAuthor();
        final TextView bookcat = receiverActivity.getCategory();
        final TextView bookDes = receiverActivity.getDescription();
        final TextView bookquan = receiverActivity.getQuantity();

        //make sure this is the book
        Log.e("Title", "Title of bookTitle book is: " + bookTitle.getText().toString());
        Log.e("Title", "Title of inventory book is: " + accountA.getInventory().getBookByIndex(0).getTitle());
        assertTrue(bookTitle.getText().toString().equals(accountA.getInventory().getBookByIndex(0).getTitle()));
        assertTrue(bookAuthor.getText().toString().equals(accountA.getInventory().getBookByIndex(0).getAuthor()));
        assertTrue(bookcat.getText().toString().equals(accountA.getInventory().getBookByIndex(0).getCategory()));
        assertTrue(bookquan.getText().toString().equals(String.valueOf(accountA.getInventory().getBookByIndex(0).getQuantity())));
        assertTrue(bookDes.getText().toString().equals(accountA.getInventory().getBookByIndex(0).getDescription()));

    }

    //test navigation to addBookScreen
    public void testNavigateToAddBook(){

        HomeScreen activity = (HomeScreen)getActivity();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddBookScreen.class.getName(),
                        null, false);


        final Button addBookButton = activity.getAddBookButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addBookButton.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final AddBookScreen receiverActivity = (AddBookScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddBookScreen.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();


    }

    //test navigation to FriendsScreen
    public void testNavigateToFriends(){

        HomeScreen activity = (HomeScreen)getActivity();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(FriendsScreen.class.getName(),
                        null, false);


        final Button friendButton = activity.getFriendButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendButton.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final FriendsScreen receiverActivity = (FriendsScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                FriendsScreen.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();


    }

    //test navigation to Profile Screen
    public void testNavigateToProfile(){

        HomeScreen activity = (HomeScreen)getActivity();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ProfileScreen.class.getName(),
                        null, false);


        final Button profileButton = activity.getProfileButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                profileButton.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final ProfileScreen receiverActivity = (ProfileScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ProfileScreen.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

}
