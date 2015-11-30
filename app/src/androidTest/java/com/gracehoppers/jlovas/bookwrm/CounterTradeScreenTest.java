package com.gracehoppers.jlovas.bookwrm;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hongwang on 15/11/5.
 */
public class CounterTradeScreenTest extends ActivityInstrumentationTestCase2{
    Account owner = new Account();
    Book aBook = new Book();
    Trade newTrade = new Trade();
    Trade trade = new Trade();
    SaveLoad saveLoad = new SaveLoad();
    TradeHistory tradeHistory =new TradeHistory();
    int position=0;

    /*
    this test includes user case for:
    1. create couterTrade
    2. initialize counterTrade with ownerBook from former trade
     */

    public CounterTradeScreenTest(){
        super(CounterTradeScreen.class);
    }

    protected void setUp()throws Exception{
        super.setUp();

        try {
            owner.setUsername("owner");
        } catch (TooLongException te) {
        } catch (NoSpacesException ne) {
        }

        aBook.setTitle("GoodBook");
        newTrade.setOwner(owner);
        newTrade.setOwnerBook(aBook);
        tradeHistory.addTrade(newTrade);
        owner.setTradeHistory(tradeHistory);

    }

    public void testInitializeOwnerBook() throws Exception{
        final CounterTradeScreen activity = (CounterTradeScreen) getActivity();
        TextView ownerText = activity.ownerText;
        setUp();

        owner = saveLoad.loadFromFile(activity.getApplicationContext());

        position = activity.pos;

        trade = owner.getTradeHistory().getTradeByIndex(position);

        assertEquals(trade.getOwnerBook().getTitle(), ownerText.getText().toString());

        assertEquals(activity.counterTrade.getOwnerBook().getTitle(), trade.getOwnerBook().getTitle());

    }

    public void testSelectFromBorrower(){
        CounterTradeScreen activity = (CounterTradeScreen) getActivity();
        final Button add = activity.add;

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(SelectCounterBooksActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                add.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        SelectCounterBooksActivity receiverActivity = (SelectCounterBooksActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                SelectCounterBooksActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
        receiverActivity.finish();
    }
    //Button for Submit and Cancel haven't completely finish

}
