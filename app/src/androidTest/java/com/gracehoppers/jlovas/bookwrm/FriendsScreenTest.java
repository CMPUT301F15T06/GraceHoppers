package com.gracehoppers.jlovas.bookwrm;

import junit.framework.TestCase;

import android.app.Activity;
import android.app.Instrumentation;
import android.net.wifi.p2p.WifiP2pManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by ljuarezr on 10/20/15.
 */
public class FriendsScreenTest extends ActivityInstrumentationTestCase2  {

    public FriendsScreenTest(){super(FriendsScreen.class);}

    //Test that we can add a friend

    public void testAddFriendNonUI() throws IllegalEmailException, NoSpacesException, TooLongException{
        //Create 2 accounts first
        Account accountA = new Account();
        Account accountB = new Account();

        accountA.setUsername("userA");
        accountB.setUsername("userB");
        accountA.setCity("YEG");
        accountB.setCity("YEG");
        accountA.setEmail("a@gmail.com");
        accountB.setEmail("b@gmail.com");

        //accountA befriends accountB
        try {
            accountA.getFriends().addFriend(accountB);
            accountB.getFriends().addFriend(accountA);
        }catch(AlreadyAddedException e){
            assertTrue(accountA.getFriends().hasFriend(accountB)&&accountB.getFriends().hasFriend(accountA));
        }


        //assert that A has B as friend and vice versa
        assertTrue(accountA.getFriends().hasFriend(accountB));
        assertTrue(accountB.getFriends().hasFriend(accountA));

    }

    public void testUnFriend() throws IllegalEmailException, NoSpacesException, TooLongException{
        //Create 2 accounts first
        Account accountA = new Account();
        Account accountB = new Account();

        accountA.setUsername("userA");
        accountB.setUsername("userB");
        accountA.setCity("YEG");
        accountB.setCity("YEG");
        accountA.setEmail("a@gmail.com");
        accountB.setEmail("b@gmail.com");

        //account befriends account
        try {
            accountA.getFriends().addFriend(accountB);
            accountB.getFriends().addFriend(accountA);
        }catch(AlreadyAddedException e){
            assertTrue(accountA.getFriends().hasFriend(accountB)&&accountB.getFriends().hasFriend(accountA));
        }


        //assert that A has B as friend and vice versa
        assertTrue(accountA.getFriends().hasFriend(accountB));
        assertTrue(accountB.getFriends().hasFriend(accountA));

        //account unfriends account
        accountA.getFriends().unFriend(accountB);
        accountB.getFriends().unFriend(accountA);

        //assert that A has B as friend and vice versa
        assertFalse(accountA.getFriends().hasFriend(accountB));
        assertFalse(accountB.getFriends().hasFriend(accountA));
    }

    public void testGetFriend() throws IllegalEmailException, NoSpacesException, TooLongException{
        //Create 2 accounts first
        Account accountA = new Account();
        Account accountB = new Account();

        accountA.setUsername("userA");
        accountB.setUsername("userB");
        accountA.setCity("YEG");
        accountB.setCity("YEG");
        accountA.setEmail("a@gmail.com");
        accountB.setEmail("b@gmail.com");

        //account befriends account
        try {
            accountA.getFriends().addFriend(accountB);
            accountB.getFriends().addFriend(accountA);
        }catch(AlreadyAddedException e){
            assertTrue(accountA.getFriends().hasFriend(accountB)&&accountB.getFriends().hasFriend(accountA));
        }

        //assert that A has B as friend and vice versa
        assertTrue(accountA.getFriends().hasFriend(accountB));
        assertTrue(accountB.getFriends().hasFriend(accountA));

        //from AccountA, get AccountB
        String friendUsername = accountB.getUsername();
        Account returnedFriend = accountA.getFriends().getFriend(friendUsername);

        assertTrue(accountB.getUsername() == returnedFriend.getUsername());
        assertTrue(accountB.getCity() == returnedFriend.getCity());
        assertTrue(accountB.getEmail() == returnedFriend.getEmail());
    }

    private Button addFriendButton;
    private EditText username;
    private ListView oldFriends;

    public void testStart() throws Exception{
        Activity activity = getActivity();
    }

    public void testAddFriend(){
        FriendsScreen activity = (FriendsScreen) getActivity();

        //reset the app to a known state
        activity.getFriendList().clear();

        //For this to work, there has to exist an Account "testFriend"
        //HOw to warranty that there exists such user?


        //add a friend using UI
        final EditText friendUsername = activity.getFriendUsername();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendUsername.setText("DemoAccount");
            }
        });

        getInstrumentation().waitForIdleSync();

        addFriendButton = activity.getAddFriendButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addFriendButton.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        //Make sure the friend was actually added
        final ListView oldFriendsList = activity.getOldFriendsList();
        Account account = (Account) oldFriendsList.getItemAtPosition(0);

        assertEquals("DemoAccount", account.getUsername());

        //ensure the Friend Profile activity starts up
        //following from  https://developer.android.com/training/activity-testing/activity-functional-testing.html
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(FriendProfileScreen.class.getName(),
                        null, false);
        //the code where they clicked was here

        //Click on the friend to go to their profile
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View v = oldFriendsList.getChildAt(0);
                oldFriendsList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        FriendProfileScreen receiverActivity = (FriendProfileScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                FriendProfileScreen.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        //clean up our activities at the end of the test
        receiverActivity.finish();

        //test that the profile screen starts up with the friend
        assertEquals("DemoAccount", ((Account) oldFriendsList.getItemAtPosition(0)).getUsername());

    }


}