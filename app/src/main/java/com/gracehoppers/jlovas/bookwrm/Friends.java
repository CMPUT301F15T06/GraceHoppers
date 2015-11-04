package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Created by ljuarezr on 10/20/15.
 */
public class Friends {
    //A user's list of friends.

    private ArrayList<Account> friends = new ArrayList<Account>();

    public ArrayList<Account> Friends(){
       return friends;
    }

    public Friends getFriends(){
        return this;
    }

    public int addFriend(Account newFriend){ //Need to search the server for Account
        //3 Cases
        //Check first if friends already:
        //1. A & B already friends! Return 1
        if (friends.contains(newFriend)){
            return 1;
        } else {

            //Search AccountB in the server. If existent, Case 1.

            //2. newFriend exists. Expected scenario. Return 2
            friends.add(newFriend);
            return 2;
            //3. B does not exist. Cannot add ghost. Return 3

        }
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

    public int getSize(){
        return friends.size();
    }






}
