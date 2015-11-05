package com.gracehoppers.jlovas.bookwrm;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jlovas on 11/5/15.
 */
public class FriendProfileScreenTest extends ActivityInstrumentationTestCase2{

    public FriendProfileScreenTest(){super(FriendProfileScreen.class);}

    //test the display of the friend's information
    public void testDisplayFriendInfo() throws AlreadyAddedException, TooLongException, NegativeNumberException{

        //pass intent to it for the getExtra stuff!
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        Intent intent = new Intent();
        intent.putExtra("listPosition", 0);
        setActivityIntent(intent);
        FriendProfileScreen activity = (FriendProfileScreen) getActivity();

        Account friendAccount = activity.getMyFriend();

        SaveLoad saveLoad = activity.getSaveLoad();
        Account testAccount = saveLoad.loadFromFile(context);

        final TextView friendName = activity.getFriendName();
        final TextView friendCity = activity.getFriendCity();
        final TextView friendEmail = activity.getFriendEmail();

        //make sure name and such are all the same
        assertTrue(friendName.getText().toString().equals(friendAccount.getUsername()));
        assertTrue(friendCity.getText().toString().equals(friendAccount.getCity()));
        assertTrue(friendEmail.getText().toString().equals(friendAccount.getEmail()));

        //now check the item displayed - is it the friend's item?

        final ListView friendInvListView = activity.getFriendInventoryListView();

        //following from  https://developer.android.com/training/activity-testing/activity-functional-testing.html
        // Set up an ActivityMonitor

        //no need for the putExtra? passes without it so sure

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ViewBookActivity.class.getName(),
                        null, false);
        //the code where they clicked was here

        //Click on the friend's item to view the book
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View v = friendInvListView.getChildAt(0);
                friendInvListView.performItemClick(v, 0, v.getId());
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
                ViewBookActivity.class, receiverActivity.getClass());

        final TextView bookName = receiverActivity.getBookTitle();
        final TextView bookAuthor = receiverActivity.getBookAuthor();
        final TextView category = receiverActivity.getCategory();
        final TextView quantity = receiverActivity.getQuantity();
        final TextView desc = receiverActivity.getDescription();

        //assert that the chosen book from the friend is the right book

        assertTrue(bookName.getText().toString().equals(friendAccount.getInventory().getBookByIndex(0).getTitle()));
        assertTrue(bookAuthor.getText().toString().equals(friendAccount.getInventory().getBookByIndex(0).getAuthor()));
        assertTrue(category.getText().toString().equals(friendAccount.getInventory().getBookByIndex(0).getCategory()));
        assertTrue(quantity.getText().toString().equals(String.valueOf(friendAccount.getInventory().getBookByIndex(0).getQuantity())));
        assertTrue(desc.getText().toString().equals(friendAccount.getInventory().getBookByIndex(0).getDescription()));

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        //clean up our activities at the end of the test
        receiverActivity.finish();

    }

    //test unfriending friend
    public void testUnfriend(){
        //pass intent to it for the getExtra stuff!
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        Intent intent = new Intent();
        intent.putExtra("listPosition", 0);
        setActivityIntent(intent);
        FriendProfileScreen activity = (FriendProfileScreen) getActivity();

        Account friendAccount = activity.getMyFriend();

        SaveLoad saveLoad = activity.getSaveLoad();
        Account testAccount = saveLoad.loadFromFile(context);

        //click unfriend button
        final Button unFriendButton = activity.getUnFriendButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                unFriendButton.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        //check and see if friend is still your friend

        assertFalse(testAccount.getFriends().hasFriend(friendAccount));

    }
    
}
