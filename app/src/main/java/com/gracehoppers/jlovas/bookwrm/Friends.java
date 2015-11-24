package com.gracehoppers.jlovas.bookwrm;


import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import java.util.ArrayList;

/**
 *
 * A list of Accounts that act as the friend list for the user.
 *Includes a getter and ways to add or remove a friend account,
 * check if it has a friend,
 * clear the list,
 * or look for a friend by index number
 * @see Account
 * @author ljuarezr on 10/20/15.
 */


public class Friends implements Serializable{
    //A user's list of friends.

    //transient private ArrayList<Account> friends = new ArrayList<Account>(); //basically not in use anymore



    private ArrayList<String> friendnames = new ArrayList<String>();



    public Friends(){
       //return friends;
    }

   // public ArrayList<Account> getFriends(){
   //     return friends;
   // }


    public ArrayList<String> getFriendnames() {
        return friendnames;
    }

    /**
     * Adds a new friend account into the list, but if the friend account has already been added, it throws the AlreadyAddedException
     * @param newFriend - the friend being put into the list
     * @return int - shows success
     * @throws AlreadyAddedException
     */


 /*   public int addFriend(Account newFriend) throws AlreadyAddedException{ //Need to search the server for Account
        //3 Cases

    public int addFriend(Account newFriend) {
        //search server for Account

        Account result;
        AccountManager accountManager=new AccountManager();
        result=(accountManager.getAccount(newFriend.getUsername()));

        try {
            if(result == null) {
                //username does not exist
                return 3;
            }

            else {
                //check if user is added as friend
                friends=result.getFriends().getFriends();
                for(int i=0;i<friends.size();i++) {
                    result=accountManager.getAccount(friends.get(i).getUsername());
                    if(result.getUsername()==newFriend.getUsername()) {
                        //user added as friend
                        return 1;

                    }
                }
            }
        }catch(RuntimeException e) {e.printStackTrace();}

        friends.add(newFriend);
        return 2;
    }

  /*      //3 Cases

        //Check first if friends already:
        //1. A & B already friends! Return 1
        //2. newFriend exists. Expected scenario. Return 2 (Request sent)
        //3. newFriend does NOT exists. Return 3. (Request not sent)
        if (friends.contains(newFriend)){
            throw new AlreadyAddedException();
            //return 1;
        } else { //Run this if they're not friends yet.

            //Search AccountB in the server. If existent, Case 1.

            //2. newFriend exists. Expected scenario. Return 2
            friends.add(newFriend);
            friendnames.add(newFriend.getUsername());
            return 2;
            //3. B does not exist. Cannot add ghost. Return 3

        }
    }*/


    /**
     * Adds a new friend into the list, but if the friend has already been added, it throws the AlreadyAddedException
     * @param newFriend - the friend being put into the list
     * @return int - shows success
     * @throws AlreadyAddedException
     */

    public int addFriend(Account newFriend) throws AlreadyAddedException{ //Need to search the server for Account
        //3 Cases
        //Check first if friends already:
        //1. A & B already friends! Return 1
        if (friendnames.contains(newFriend.getUsername())){
            throw new AlreadyAddedException();
            //return 1;
            //return 2;
        } else {

            //Search AccountB in the server. If existent, Case 1.

            //2. newFriend exists. Expected scenario. Return 2
            friendnames.add(newFriend.getUsername());
            //friendnames.add(newFriend.getUsername());
            return 2;
            //3. B does not exist. Cannot add ghost. Return 3

        }
    }


    /**
     * removes the friend account from the list
     * @param username - the friend account being removed
     */

    public void unFriend(String username) {
        for (int i = 0; i < friendnames.size(); i++) {
            if (friendnames.get(i).equals(username)) {
                // friends.remove(i);
                friendnames.remove(i);
            }
        }
    }

  /*  public void unFriend(Account friend){

        friends.remove(friend);


    }*/

    /**
     *Checks to see if the list contains the friend account given in the parameter
     * @param friend - the friend account being checked
     * @return boolean - true if the list does have this friend account
     */
    public boolean hasFriend(Account friend){
        //return friends.contains(friend);
        return friendnames.contains(friend.getUsername());
    }

    /**
     * checks to see if the list contains a friend with this username
     * @param username
     * @return boolean
     */
    public boolean hasFriend(String username){
        for(int i=0;i<this.getSize();i++){
            try {
                if (this.getFriendByIndex(i) == username) {
                    return true;
                }
            }catch(NegativeNumberException e){

            }catch(TooLongException e){

            }
        }
        return false;
    }

    public String getFriend(String friendUsername){  //sorry, this one cant return an Account anymore :(
        String friend = null;
        for (String candidate : friendnames){
            if (candidate == friendUsername){
                friend = candidate;
            }
        }
        return friend;
    }

    /**
     * returns the number of friends, the size of the list of accounts.
     * @return int - the size of the list
     */
    //public int getSize(){
    //    return friends.size();
   // }


    /**
     * returns the number of friends, the size of the list
     * @return int - the size of the list
     */
    public int getSize() {return friendnames.size();}

    /**
     * removes all friend accounts from the list
     */
    public void clear() {
       // friends.clear();
        friendnames.clear();
    }

    //Need to run tests for this

    /**
     * returns the friend at the index given by the parameter. Throws a TooLongException if the parameter int is longer than the list size,
     * throws NegativeNumberException if the int parameter is negative
     * @param i - the position in the list to check
     * @return Account - the account at the given position
     * @throws NegativeNumberException
     * @throws TooLongException
     */
   /* public Account getFriendByIndex(int i)throws NegativeNumberException, TooLongException{
        if(i <0){
            throw new NegativeNumberException();
        }else if(i>=friends.size()){ //if the requested position exceeds inventory size, throw exception
            throw new TooLongException();
        }else

            return friends.get(i);
    }*/


    /**
     * returns the friend at the index given by the parameter. Throws a TooLongException if the parameter int is longer than the list size,
     * throws NegativeNumberException if the int parameter is negative
     * @param i - the position in the list to check
     * @return Account - the account at the given position
     * @throws NegativeNumberException
     * @throws TooLongException
     */
    public String getFriendByIndex(int i)throws NegativeNumberException, TooLongException{  //sorry this once doesnt return an Account anymore :(
        if(i <0){
            throw new NegativeNumberException();
        }else if(i>=friendnames.size()){ //if the requested position exceeds inventory size, throw exception
            throw new TooLongException();
        }else

            return friendnames.get(i);
    }



}
