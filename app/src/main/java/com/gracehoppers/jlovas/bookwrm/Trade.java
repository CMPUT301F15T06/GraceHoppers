package com.gracehoppers.jlovas.bookwrm;
/**
 * Created by chen1, ljuarezr on 10/19/15.
 */
import java.util.ArrayList;

/**
 * Trade is a class that represents a trade between a borrower and an owner.
 * Contains Booleans accpted and declined, an instance of the TradeStatus,
 * an owner's book and a list of the borrower's books. As well as both the
 * owner and the borrower accounts, and a string of comments on the trade.
 * Getters and Setters for these fields.
 * @see Account, TradeStatus, Book, TradeCompletion
 */
public class Trade {
    //initialize trade to be unaccepted and undeclined

    private Boolean accepted=Boolean.FALSE;
    private Boolean declined=Boolean.FALSE;

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    //if a trade is accepted or declined, it will be in history of trade list
    private TradeCompletion completion;
    private TradeStatus status;
    private Account owner;
    private Account borrower;
    private Book ownerBook= new Book();  //can be 1
    private ArrayList<Book> borrowerBook = new ArrayList<Book>(); //can be 0 or more
    private String ownerComment;

    public void setCompletion(TradeCompletion completion) {this.completion = completion;}

    public String getCompletion(){return this.completion.toString();}

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
        String completion = getCompletion().toString();
        String finalView = completion + "; " + status;
        for (Book book: friendBooks) {
            friendBookStr = friendBookStr + book.getTitle() + " '";
        }

        //ACCEPTED CASE
        if (status == "ACCEPTED"){
            finalView = finalView + ": Traded my" + myBook + "for " + friend + "'s " +
                    friendBookStr ;
        }

        //DECLINED CASE
        if (status == "DECLINED"){
            finalView = finalView + ": Refused to trade my " + myBook + "for " + friend + "'s " +
                    friendBookStr ;
        }


        //COUNTER CASE
        //Confused about the Counter Trade (where is the counter book stored?)
        /*
        if (status == "COUNTERED"){
            finalView = status + ": Refused to trade my " + myBook + "for " + friend + "'s " +
                    friendBookStr + " but took my";
        }
        */

        return finalView;
    }


}
