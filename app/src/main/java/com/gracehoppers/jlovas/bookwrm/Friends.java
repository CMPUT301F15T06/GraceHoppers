package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Created by ljuarezr on 10/20/15.
 */
public class Friends {
    //List of friends for a specific account
    //Each account should have exactly one Friends instance associated with it
    //Should we call the constructor in the SignUp/CreateAccount???

    private ArrayList<Account> friends = new ArrayList<Account>();

    public ArrayList<Account> Friends(){
       return friends;
    }

    public Friends getFriends(){
        return this;
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
