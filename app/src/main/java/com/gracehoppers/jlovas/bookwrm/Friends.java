package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Created by ljuarezr on 10/20/15.
 */
public class Friends {
    //A user's list of friends.

    private ArrayList<Account> friends = new ArrayList<Account>();

    public Friends(){
       //return friends;
    }

    public ArrayList<Account> getFriends(){
        return friends;
    }


    public int addFriend(Account newFriend) throws AlreadyAddedException{ //Need to search the server for Account
        //3 Cases
        //Check first if friends already:
        //1. A & B already friends! Return 1
        if (friends.contains(newFriend)){
            throw new AlreadyAddedException();
            //return 1;
            //return 2;
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

    public void clear() {friends.clear();}

    //Need to run tests for this
    public Account getFriendByIndex(int i)throws NegativeNumberException, TooLongException{
        if(i <0){
            throw new NegativeNumberException();
        }else if(i>friends.size()){ //if the requested position exceeds inventory size, throw exception
            throw new TooLongException();
        }else

            return friends.get(i);
    }





}
