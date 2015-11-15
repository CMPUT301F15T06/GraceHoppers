package com.gracehoppers.jlovas.bookwrm;

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

public class Friends{
    //A user's list of friends.

    private ArrayList<Account> friends = new ArrayList<Account>();

    public Friends(){
       //return friends;
    }

    public ArrayList<Account> getFriends(){
        return friends;
    }

    /**
     * Adds a new friend account into the list, but if the friend account has already been added, it throws the AlreadyAddedException
     * @param newFriend - the friend being put into the list
     * @return int - shows success
     * @throws AlreadyAddedException
     */

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

    /**
     * removes the friend account from the list
     * @param friend - the friend account being removed
     */
    public void unFriend(Account friend){
        friends.remove(friend);
    }

    /**
     *Checks to see if the list contains the friend account given in the parameter
     * @param friend - the friend account being checked
     * @return boolean - true if the list does have this friend account
     */
    public boolean hasFriend(Account friend){
        return friends.contains(friend);
    }

    /**
     * checks to see if the list contains a friend with this username
     * @param username
     * @return boolean
     */
    public boolean hasFriend(String username){
        for(int i=0;i<this.getSize();i++){
            try {
                if (this.getFriendByIndex(i).getUsername() == username) {
                    return true;
                }
            }catch(NegativeNumberException e){

            }catch(TooLongException e){

            }
        }
        return false;
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

    /**
     * returns the number of friends, the size of the list of accounts.
     * @return int - the size of the list
     */
    public int getSize(){
        return friends.size();
    }

    /**
     * removes all friend accounts from the list
     */
    public void clear() {friends.clear();}

    //Need to run tests for this

    /**
     * returns the friend at the index given by the parameter. Throws a TooLongException if the parameter int is longer than the list size,
     * throws NegativeNumberException if the int parameter is negative
     * @param i - the position in the list to check
     * @return Account - the account at the given position
     * @throws NegativeNumberException
     * @throws TooLongException
     */
    public Account getFriendByIndex(int i)throws NegativeNumberException, TooLongException{
        if(i <0){
            throw new NegativeNumberException();
        }else if(i>=friends.size()){ //if the requested position exceeds inventory size, throw exception
            throw new TooLongException();
        }else

            return friends.get(i);
    }





}
