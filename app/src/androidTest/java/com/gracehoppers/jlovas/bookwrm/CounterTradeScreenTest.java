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

    public CounterTradeScreenTest(){
        super(CounterTradeScreen.class);
    }

    public void testInitializeOwnerBook(){
        CounterTradeScreen activity = (CounterTradeScreen) getActivity();
        TextView ownerText = activity.ownerText;

        //ensure the textView shows OwnerBook's Title
        Account owner = new Account();

        Book aBook = new Book();
        aBook.setTitle("goodBook");

        Trade newTrade = new Trade();
        newTrade.setOwner(owner);
        newTrade.setOwnerBook(aBook);

        activity.oldTrade = newTrade;
        assertEquals("goodBook", ownerText.getText().toString());

    }

    public void testAddButton(){
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

}
