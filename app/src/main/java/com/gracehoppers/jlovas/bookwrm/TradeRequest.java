package com.gracehoppers.jlovas.bookwrm;



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
    private boolean isAnswered;

    public TradeHistory tradeHistory;
    private boolean needUpdate;


    public Trade getTrade() {
        return trade;
    }

    public TradeRequest(){
        needUpdate=false;
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
        isAnswered = false;

    }

    public void acceptTradeRequest(Account yourAccount, Account friendAccount, Trade newTrade) {
        AccountManager manager = new AccountManager();

        tradeHistory= yourAccount.getTradeHistory();
        tradeHistory.addTrade(newTrade);
        yourAccount.setTradeHistory(tradeHistory);
        manager.updateAccount(yourAccount);


        tradeHistory=friendAccount.getTradeHistory();
        tradeHistory.addTrade(newTrade);
        friendAccount.setTradeHistory(tradeHistory);
        manager.updateAccount(friendAccount);

        /*
        yourAccount.getTradeHistory().addTrade(newTrade);
        friendAccount.getTradeHistory().addTrade(newTrade);*/
        isAnswered = true;
    }


    public void declineTradeRequest(Account yourAccount, Account friendAccount, Trade newTrade) {

        yourAccount.getTradeHistory().addTrade(newTrade);
        friendAccount.getTradeHistory().addTrade(newTrade);
        isAnswered = true;
    }


    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getOwnerBook(){
        int number = trade.getOwnerBook().getUniquenum().getNumber();
        return toString().valueOf(number);}

    public String getBorrowerBook(){
        String bookstr = "";
         for (Book book : trade.getBorrowerBook()){
             bookstr = bookstr + toString().valueOf(book.getUniquenum().getNumber()) ;
         }
        return bookstr;
    }

    public void setNeedUpdate(boolean update) {
        needUpdate=update;
    }

    public boolean getNeedUpdate(){
        return needUpdate;
    }

}
