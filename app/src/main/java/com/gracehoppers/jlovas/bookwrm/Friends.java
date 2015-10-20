package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Created by ljuarezr on 10/20/15.
 */
public class Friends {
    //List of friends for a specific account
    //Each account should have exactly one Friends instance associated with it
    //Should we call the constructor in the SignUp/CreateAccount???

    private Account user;
    private ArrayList<Account> friends = new ArrayList<Account>();

    //NEED a user argument for the constructor. If no argument,
    // who do we assign the list to?
    public ArrayList<Account> Friends(Account user){
       return friends;
    }

    public ArrayList<Account> getFriends(Account user){
        return this.friends;
    }

    public void addFriend(Account friend){
        friends.add(friend);
    }

    public void unFriend(Account friend){
        friends.remove(friend);
    }

    public boolean hasFriend(Account friend){
        return friends.contains(friend);
    }

    public Account getFriend(String friendUsername){
        Account friend = null;
        for (Account candidate : friends){
            if (candidate.getUsername() == friendUsername){
                friend = candidate;
            }
        }
        return friend;
    }








}
