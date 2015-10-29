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
        accountA.getFriends().addFriend(accountB);
        accountB.getFriends().addFriend(accountA);

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
        accountA.getFriends().addFriend(accountB);
        accountB.getFriends().addFriend(accountA);

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

}