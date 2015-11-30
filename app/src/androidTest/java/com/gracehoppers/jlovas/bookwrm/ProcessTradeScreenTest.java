package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by hong8 on 11/5/15.
 */
public class ProcessTradeScreenTest extends ActivityInstrumentationTestCase2 {

    public Account owner = new Account();
    public Account borrower = new Account();
    public Book book1 = new Book();
    public Book book2 = new Book();
    public ArrayList<Book> bBooks = new ArrayList<Book>();
    public Trade trade = new Trade();
    public TradeHistory tradeHistory= new TradeHistory();

    /*this test includes user case for:
     1. accept a trade and decline a trade;
     2. email both parties
     */

    public ProcessTradeScreenTest() {
        super(ProcessTradeScreen.class);
    }

    protected void setUp() throws Exception{
        super.setUp();

        setActivityInitialTouchMode(true);

        try {
            borrower.setUsername("borrower");
            owner.setUsername("owner");
        } catch (TooLongException te) {
        } catch (NoSpacesException ne) {
        }
        book1.setTitle("BookA");
        book2.setTitle("BookB");
        bBooks.add(book2);
        trade.setBorrowerBook(bBooks);
        trade.setOwnerBook(book1);
    }
/*
    public void testAccept() throws Exception {
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button accept = activity.accept;
        //ensure the trade is not accepted before
        setUp();

        activity.trade = trade;
        activity.account = owner;
        assertFalse(activity.trade.getAccepted());

        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(CounterTradeScreen.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                accept.performClick();

            }
        });

        getInstrumentation().waitForIdleSync();

        assertTrue(activity.trade.getAccepted());
        //assertNotNull("acceptDialog is null", receiverActivity.dialog);

        //remove Monitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }

    public void testSendEmail() throws Exception{
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button accept = activity.accept;
        setUp();

        activity.trade = trade;
        activity.account = owner;
        assertFalse(activity.sent);

        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(CounterTradeScreen.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                accept.performClick();

            }
        });

        getInstrumentation().waitForIdleSync();

        assertTrue(activity.sent);

        //remove Monitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }

    public void testDecline() throws Exception {
        setUp();
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button decline = activity.decline;

        activity.trade = trade;
        activity.account = owner;

        //ensure the trade is not declined before
        assertFalse(activity.trade.getDeclined());

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(CounterTradeScreen.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                decline.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        //ensure the trade declines after click decline
        assertTrue(activity.trade.getDeclined());

    }



        //assertNotNull("declineDialog is null", receiverActivity.dialog1);
/*
        // Validate that ReceiverActivity is started
        CounterTradeScreen receiverActivity = (CounterTradeScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                CounterTradeScreen.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
        receiverActivity.finish();   */
    }

    //email function can't implement before loading account information
    /*
    public void testViewBorrowerName() {
        //ensure the text shows name of borrower

        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();

        Account borrower = new Account();
        try {
            borrower.setUsername("GOGO");
        } catch (TooLongException te) {
        } catch (NoSpacesException ne) {
        }
        activity.trade= new Trade();
        activity.trade.setBorrower(borrower);

        Thread thread = new FindTRThread(account.getUsername());
        thread.start();

        assertEquals("Borrower Name:\n" + "GOGO", activity.bName.getText().toString());
    }

    public void testViewBorrowerBooks() {
        //ensure the text shows the name of borrowerBook
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();

        Book book1 = new Book();
        book1.setTitle("book1");
        Book book2 = new Book();
        book2.setTitle("book2");

        bBooks.add(book1);
        bBooks.add(book2);

        Trade aTrade = new Trade();
        aTrade.setBorrowerBook(bBooks);

        activity.trade=aTrade;

        String titles= "" ;
        for(Book b: bBooks){
            titles= titles + b.getTitle() +"\n";
        }
        String text = "Borrower Books:\n" + titles;
        assertEquals(text, activity.bBook.getText().toString());

    }

    public void testViewOwnerBook() {
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();

        Book aBook = new Book();
        aBook.setTitle("goodBook");

        Trade newTrade = new Trade();
        newTrade.setOwnerBook(aBook);
        activity.trade = newTrade;

        assertEquals("Owner Book:\n"+"goodBook", activity.oBook.getText().toString());

    }
**/

