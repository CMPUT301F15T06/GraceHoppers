package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
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
    public Trade trade1 = new Trade();
    public TradeHistory tradeHistory = new TradeHistory();
    SaveLoad saveLoad = new SaveLoad();
    int pos= 0;
    private TradeRequests traderequests =new TradeRequests();
    private TradeRequestManager trmanager = new TradeRequestManager();
    private TradeRequest tradeRequest = new TradeRequest();

    /*this test includes user case for:
     1. accept a trade and decline a trade;
     2. email both parties
     */

    public ProcessTradeScreenTest() {
        super(ProcessTradeScreen.class);
    }

    protected void setUp() throws Exception {
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


    //test "trade is accepted in server"
    public void testAcceptWithServer() throws Exception {
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button accept = activity.accept;
        setUp();

        owner = saveLoad.loadFromFile(activity.getApplicationContext());

        pos = activity.position;

        traderequests = trmanager.findTradeRequests(owner.getUsername());
        tradeRequest = traderequests.get(pos);

        trade = tradeRequest.getTrade();
        trade.setAccepted(false);

        activity.trade = trade;

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

        getInstrumentation().removeMonitor(receiverActivityMonitor);

        //trmanager.deleteTR(tradeRequest);

    }

    //test "trade is declined in server"
    public void testDeclineWithServer() throws Exception {
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button decline = activity.decline;
        setUp();

        owner = saveLoad.loadFromFile(activity.getApplicationContext());

        pos = activity.position;

        traderequests = trmanager.findTradeRequests(owner.getUsername());
        tradeRequest = traderequests.get(pos);

        trade = tradeRequest.getTrade();
        trade.setDeclined(false);

        activity.trade = trade;

        assertFalse(activity.trade.getDeclined());

        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(CounterTradeScreen.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                decline.performClick();

            }
        });

        getInstrumentation().waitForIdleSync();

        assertTrue(activity.trade.getDeclined());

        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }

    public void testDeclineWithoutServer() throws Exception {

        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button decline = activity.decline;

        Trade trade2 = new Trade();

        trade2.setDeclined(false);
        activity.trade = trade2;

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

        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }

    public void testAcceptWithoutServer() throws Exception {

        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button accept = activity.accept;

        trade1 = new Trade();

        trade1.setAccepted(false);
        activity.trade = trade1;

        //ensure the trade is not declined before
        assertFalse(activity.trade.getAccepted());

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(CounterTradeScreen.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                accept.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        //ensure the trade declines after click decline
        assertTrue(activity.trade.getAccepted());

        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }

    public void testSendEmail() throws Exception {
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

}


    //since we do not need to test for UI , below are unnecessary





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

