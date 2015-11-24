package com.gracehoppers.jlovas.bookwrm;

/**
 * holds individual friend requests by sender, receiver, and status
 * @see Account, Friends
 * @author nlovas
 */
public class FriendRequest {

    //sender and receiver are usernames
    private String sender;
    private String receiver;
    private boolean isAnswered;

    public FriendRequest() {
    }

    /**
     * creates a friend request between two users
     * @param senderAccount Account
     * @param receiverAccount Account
     * @throws AlreadyAddedException, AlreadyExistsException
     */
    public void makeRequest(Account senderAccount, String receiverAccount) throws AlreadyAddedException {
        //if(){
        // look through the server and see if a friend request between these two accounts already exists,
        //where sender = senderaccount and receiver=receiveraccount OR sender =receiveraccount and receiver = senderaccount
        //if there is one, throw new AlreadyExistsException();
       // }

        if(!senderAccount.getFriends().hasFriend(receiverAccount)) { //if youre not already friends with them
            sender = senderAccount.getUsername();
            receiver = receiverAccount;
            isAnswered = false;
            //next: store it on the server
        }else throw new AlreadyAddedException();
    }

    /**
     * puts each friend in eachother's friends list
     * @param yourAccount
     * @param friendAccount
     */
    public void acceptFriendRequest(Account yourAccount, Account friendAccount){
        try {
            yourAccount.getFriends().addFriend(friendAccount);
            friendAccount.getFriends().addFriend(yourAccount);
            isAnswered = true;
            //put some server stuff here
        }catch(AlreadyAddedException e){
            e.getStackTrace();
        }
    }

    /**
     * answers the friend request by declining it
     */
    public void declineFriendRequest(){
        isAnswered=true;
        //put some server stuff here
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }



    //public void checkForFriendRequests() could be job for the manager


}
