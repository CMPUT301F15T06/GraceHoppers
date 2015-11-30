package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by chen1 on 11/5/15.
 */
public class CreateTradeScreenTest extends ActivityInstrumentationTestCase2 {

    /*
       this test is for use case:
       1. create a trade
       2. add, select and sumbit

     */

    public Account owner = new Account();
    public Account borrower = new Account();
    public Book book1 = new Book();
    public Book book2 = new Book();
    public ArrayList<Book> bBooks = new ArrayList<Book>();
    public Trade trade = new Trade();
    SaveLoad saveLoad = new SaveLoad();
    int pos= 0;
    private TradeRequests traderequests =new TradeRequests();
    private TradeRequestManager trmanager = new TradeRequestManager();
    private TradeRequest tradeRequest = new TradeRequest();

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

    public CreateTradeScreenTest(){
        super(CreateTradeScreen.class);
    }

    public void testStart() throws Exception {

        Activity activity = getActivity();
    }


    public void testAdd(){
        CreateTradeScreen activity = (CreateTradeScreen) getActivity();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(SelectFromOwnerInventoryActivity.class.getName(),
                        null, false);

        final Button borrowerAdd = activity.borrowerAdd;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                borrowerAdd.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final SelectFromOwnerInventoryActivity receiverActivity = (SelectFromOwnerInventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                SelectFromOwnerInventoryActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

    public void testSelect(){
        CreateTradeScreen activity = (CreateTradeScreen) getActivity();

        //Set up ActivityMonitor
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(SelectFromBorrowerInventoryActivity.class.getName(),
                        null, false);

        final Button ownerSelect = activity.ownerSelect;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ownerSelect.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final SelectFromBorrowerInventoryActivity receiverActivity = (SelectFromBorrowerInventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                SelectFromBorrowerInventoryActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

    public void testSubmit(){
        CreateTradeScreen activity = (CreateTradeScreen) getActivity();

        final Button submit = activity.submitTrade;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                submit.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        //check the submission is successfully performed
        assertTrue(activity.submission);

    }


    public void testCancel(){
        CreateTradeScreen activity = (CreateTradeScreen) getActivity();

        final Button cancel = activity.cancelTrade;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cancel.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        //check the cancellation is successfully performed
        assertTrue(activity.cancellation);

    }
/*
    public void testSubmitFailWhenNoOwnerBook() throws Exception {
        CreateTradeScreen activity = (CreateTradeScreen) getActivity();
        final Button submit = activity.submitTrade;
        setUp();

        owner = saveLoad.loadFromFile(activity.getApplicationContext());

        traderequests = trmanager.findTradeRequests(owner.getUsername());

        trade = tradeRequest.getTrade();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                submit.performClick();

            }
        });

        getInstrumentation().waitForIdleSync();
        assertNull(trade);

    }
*/

}
