package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by ljuarezr on 11/24/15.
 */
public class ViewTradeActivityTest extends  TestCase {
     /*
        Testing ViewTradeActivity. (No UI test yet).
        Create a mock trade manually, then test that it is displayed correctly
     */


    private TextView borrowerUsername, borrowerBook, ownerUsername, ownerBook, comments;
    private Button complete;



    public void testViewTrade(){

        //First create two accounts
        Account A = new Account();
        Account B = new Account();
        try{
            A.setUsername("A");
            A.setEmail("abc@gmail.com");
            A.setCity("YEG");
            B.setUsername("B");
            B.setEmail("xyz@gmail.com");
            B.setCity("YEG");
        } catch (NoSpacesException e){

        } catch (TooLongException e){

        } catch (IllegalEmailException e ) {

        }

        //Create two books to make the trade with
        Book bookA = new Book();
        bookA.setTitle("BookA");
        bookA.setAuthor("AuthorA");

        Book bookB = new Book();
        bookB.setTitle("BookB");
        bookB.setAuthor("AuthorB");
        ArrayList<Book> borrowerBookList = new ArrayList<>();
        borrowerBookList.add(bookB);

        //Set up the trade
        Trade trade = new Trade();
        trade.setOwner(A);
        trade.setBorrower(B);
        trade.setBorrowerBook(borrowerBookList);
        trade.setOwnerBook(bookA);
        trade.setOwnerComment("Test Trade");


        //Reset the application to a known state
        A.getTradeHistory().clear();
        A.getTradeHistory().addTrade(trade);
        //confirm that book was added to the TradeHistory
        assertTrue(A.getTradeHistory().getSize() == 1);

    }

    public void testComplete(){


    }


}