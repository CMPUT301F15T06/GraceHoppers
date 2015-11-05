package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * trade holds the information about the trade:
 * -who it is between
 * -items up for trade
 * -uhhh some other stuff
 */
public class Trade {
    private Boolean accepted;
    private Boolean declined;

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    //if a trade is accepted or declined, it will be in history of trade list
    //otherwise, it will be in current trade list
    private TradeStatus status;
    private Account owner;
    private Account borrower;
    private Book ownerBook = new Book();  //can be 1
    private ArrayList<Book> borrowerBook = new ArrayList<Book>(); //can be 0 or more
    private String ownerComment;

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setDeclined(Boolean declined) {
        this.declined = declined;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
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

    public void addToBorrowerBook(Book newBook){
        this.borrowerBook.add(newBook);
    }

    //Override the toString() method to show the borrower, and the books traded.
    @Override
    public String toString(){

        //3 Cases : Accepted, Declined and Countered

        String myBook = getOwnerBook().getTitle().toString();
        ArrayList<Book> friendBooks = getBorrowerBook();
        String friendBookStr = "";
        String friend = getBorrower().getUsername();
        String status = getStatus().toString();
        String finalView = "";
        for (Book book: friendBooks) {
            friendBookStr = friendBookStr + book.getTitle() + " '";
        }

        //ACCEPTED CASE
        if (status == "ACCEPTED"){
            finalView = status + ": Traded my" + myBook + "for " + friend + "'s " +
                    friendBookStr ;
        }

        //DECLINED CASE
        if (status == "DECLINED"){
            finalView = status + ": Refused to trade my " + myBook + "for " + friend + "'s " +
                    friendBookStr ;
        }


        //Confused about the Counter Trade (where is the counter book stored?)
        //COUNTER CASE
        /*
        if (status == "COUNTERED"){
            finalView = status + ": Refused to trade my " + myBook + "for " + friend + "'s " +
                    friendBookStr + " but took my";
        }
        */

        //Does this mean that not all variables are set? Confused again :/
        //INPROCESS CASE


        return finalView;
    }


}
