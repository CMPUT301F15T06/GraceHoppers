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

    public ProcessTradeScreenTest() {
        super(ProcessTradeScreen.class);
    }

    public void testViewBorrowerName() {
        //ensure the text shows name of borrower
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();

        Account borrower = new Account();
        try {
            borrower.setUsername("GOGO");
        } catch (TooLongException te) {
        } catch (NoSpacesException ne) {
        }
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
        ArrayList<Book> bBooks = new ArrayList<Book>();
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

    public void testAccept() {
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button accept = activity.accept;
        //ensure the trade is not accepted before
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

    public void testDecline() {
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button decline = activity.decline;

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
        //assertNotNull("declineDialog is null", receiverActivity.dialog1);

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
        receiverActivity.finish();
    }
}
