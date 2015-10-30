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
    //if a trade is accepted or declined, it will be in history of trade list
    //otherwise, it will be in current trade list
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

}
