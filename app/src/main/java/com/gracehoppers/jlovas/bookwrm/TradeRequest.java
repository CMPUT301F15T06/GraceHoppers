package com.gracehoppers.jlovas.bookwrm;

/**
 * Created by ljuarezr on 11/25/15.
 */

/**
 * Holds individual Trade request by sender, receiver and Status
 * @see Account, Trade, Book
 *
 * @author ljuarezr (adapted from nlovas' FriendRequest)
 *
 */
public class TradeRequest {


    //sender and receiver are usernames
    private String sender;
    private String receiver;
    private Trade trade;
    private TradeStatus status;

    public TradeRequest(){
    }
    /**
     * creates a friend request between two users
     * @param senderAccount Account
     * @param receiverAccount Account
     * @param newTrade Trade
     *
     */
    public void makeTradeRequest(Account senderAccount, String receiverAccount, Trade newTrade) {
        //Not going to check if the books are available, instead, only display the available books
        //when creating the trade
        sender = senderAccount.getUsername();
        receiver = receiverAccount;
        trade = newTrade;

    }

    public void acceptTradeRequest(Account yourAccount, Account friendAccount, Trade newTrade) {

        yourAccount.getTradeHistory().addTrade(newTrade);
        friendAccount.getTradeHistory().addTrade(newTrade);
        status = TradeStatus.ACCEPTED;
    }


    public void declineTradeRequest(Account yourAccount, Account friendAccount, Trade newTrade) {

        yourAccount.getTradeHistory().addTrade(newTrade);
        friendAccount.getTradeHistory().addTrade(newTrade);
        status = TradeStatus.DECLINED;
    }


    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    //public String getTrade() {return trade.ToString();}
}
