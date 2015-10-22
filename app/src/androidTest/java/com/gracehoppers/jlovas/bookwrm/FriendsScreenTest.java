package com.gracehoppers.jlovas.bookwrm;

import junit.framework.TestCase;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;


/**
 * Created by ljuarezr on 10/20/15.
 */
public class FriendsScreenTest extends ActivityInstrumentationTestCase2  {

    public FriendsScreenTest(){super(FriendsScreen.class);}

    //Test that we can add a friend

    public void testAddFriend() throws IllegalEmailException, NoSpacesException, TooLongException{
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
        accountA.getFriends().addFriend(accountB);
        accountB.getFriends().addFriend(accountA);

        //assert that A has B as friend and vice versa
        assertTrue(accountA.getFriends().hasFriend(accountB));
        assertTrue(accountB.getFriends().hasFriend(accountA));




    }


}