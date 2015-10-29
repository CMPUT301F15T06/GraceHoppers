package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;

import java.util.Stack;

/**
 * trade holds the information about the trade:
 * -who it is between
 * -items up for trade
 * -uhhh some other stuff
 */
public class Trade {
    private TradeStatus status;
    //is there a need for trade and createTrade to be separate?
    private int tradeID;
    private Account borrower;
    private Account owner;
    private Book borrowerItem;
    private Book ownerItem;


    public Trade(Account borrower, Account owner, TradeStatus status){
        this.borrower = borrower;
        this.owner = owner;
        this.status = status;

    }


    public void createTrade(Book borrowerItemFromInventory, Book ownerItemFromInventory){
        this.borrowerItem = borrowerItemFromInventory;
        this.ownerItem = ownerItemFromInventory;
    }

    public void changeStatus(TradeStatus newStatus){
        this.status = newStatus;
    }

    public TradeStatus getStatus(){
        return status;
    }

}
