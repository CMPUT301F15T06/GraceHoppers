package com.gracehoppers.jlovas.bookwrm;

<<<<<<< HEAD
import android.app.Activity;
import android.net.wifi.WifiConfiguration;

import java.util.Stack;
=======
import java.util.ArrayList;
>>>>>>> 8df81569358950010f26c1b1e01009e2d25f33c1

/**
 * trade holds the information about the trade:
 * -who it is between
 * -items up for trade
 * -uhhh some other stuff
 */
public class Trade {
<<<<<<< HEAD
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
=======
    private Boolean accepted;
    private Boolean declined;
    //if a trade is accepted or declined, it will be in history of trade list
    //otherwise, it will be in current trade list
    private Account owner;
    private Account borrower;
    private Book ownerBook;  //can be 1
    private ArrayList<Book> borrowerBook; //can be 0 or more
    private String ownerComment;

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setDeclined(Boolean declined) {
        this.declined = declined;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
>>>>>>> 8df81569358950010f26c1b1e01009e2d25f33c1
    }

    public void setBorrower(Account borrower) {
        this.borrower = borrower;
    }

    public void setOwnerBook(Book ownerBook) {
        this.ownerBook = ownerBook;
    }

    public void setBorrowerBook(ArrayList<Book> borrowerBook) {
        this.borrowerBook = borrowerBook;
    }

    public void setOwnerComment(String ownerComment) {
        this.ownerComment = ownerComment;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Boolean getDeclined() {
        return declined;
    }

    public Account getOwner() {
        return owner;
    }

    public Account getBorrower() {
        return borrower;
    }

    public Book getOwnerBook() {
        return ownerBook;
    }

    public ArrayList<Book> getBorrowerBook() {
        return borrowerBook;
    }

    public String getOwnerComment() {
        return ownerComment;
    }
}
