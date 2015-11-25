package com.gracehoppers.jlovas.bookwrm;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by nlovas on 11/24/15.
 */
public class FriendsTest extends ActivityInstrumentationTestCase2 {

    /*
    Tests to see if Friends behaves the way it should
     */

    //@Override
    public FriendsTest(){
        super(Friends.class);
    }

    public void testCreateFriends(){
        //test to see that you can create an empty friendlist:
        Friends friends = new Friends();
        assertTrue(friends.getSize()==0);

    }

    public void testAddRemoveFriends(){
        //test to see that you can add and remove friends
        Account test = new Account();
        Friends friends = new Friends();
        try {
            test.setEmail("cool@ualberta.ca");
            test.setCity("Edmonton");
            test.setUsername("itsCOOLGUY");
        }catch(IllegalEmailException e){
            assertFalse(test.getEmail().equals("cool@ualberta.ca"));
        }catch(TooLongException e){
            assertFalse(test.getEmail().equals("cool@ualberta.ca"));
        }catch(NoSpacesException e){
            assertFalse(test.getEmail().equals("cool@ualberta.ca"));
        }

        try{
            friends.addFriend(test);
        }catch(AlreadyAddedException e){
            assertFalse(friends.hasFriend(test));
        }
     assertTrue(friends.hasFriend(test)); //both versions of hasFriend
        assertTrue(friends.hasFriend("itsCOOLGUY"));

        friends.unFriend("itsCOOLGUY");
        assertTrue(!friends.hasFriend(test));
        assertTrue(!friends.hasFriend("itsCOOLGUY"));

        assertTrue(friends.getSize()==0);

    }

    public void testSpecificAddRemove(){
        //tests to see if you can retrieve a specific friend

        Account test = new Account();
        Friends friends = new Friends();
        try {
            test.setEmail("cool@ualberta.ca");
            test.setCity("Edmonton");
            test.setUsername("itsCOOLGUY");
        }catch(IllegalEmailException e){
            assertFalse(test.getEmail().equals("cool@ualberta.ca"));
        }catch(TooLongException e){
            assertFalse(test.getEmail().equals("cool@ualberta.ca"));
        }catch(NoSpacesException e){
            assertFalse(test.getEmail().equals("cool@ualberta.ca"));
        }

        try{
            friends.addFriend(test);
        }catch(AlreadyAddedException e){
            assertFalse(friends.hasFriend(test));
        }
        assertTrue(friends.hasFriend(test)); //both versions of hasFriend
        assertTrue(friends.hasFriend("itsCOOLGUY"));

        String username = friends.getFriend("itsCOOLGUY");
        assertTrue(username.equals("itsCOOLGUY"));

        String username2 = new String();
        try {
            username2 = friends.getFriendByIndex(0);
        }catch(TooLongException e){
            assertFalse(username2.equals("itsCOOLGUY"));
        }catch(NegativeNumberException e){
            assertFalse(username2.equals("itsCOOLGUY"));
        }

        friends.clear();
        assertTrue(friends.getSize()==0);

    }

}
