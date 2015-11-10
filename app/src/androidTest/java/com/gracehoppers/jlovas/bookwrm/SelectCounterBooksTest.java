package com.gracehoppers.jlovas.bookwrm;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 * Created by hongwang on 15/11/5.
 */
public class SelectCounterBooksTest extends ActivityInstrumentationTestCase2 {

    public SelectCounterBooksTest(){
        super(SelectCounterBooksActivity.class);

    }

    public void testReturn(){
        SelectCounterBooksActivity activity = (SelectCounterBooksActivity) getActivity();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(CounterTradeScreen.class.getName(),
                        null, false);

        final ListView books = activity.counterList;

        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = books.getChildAt(0);
                books.performItemClick(v,0,v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        CounterTradeScreen receiverActivity = (CounterTradeScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                CounterTradeScreen.class, receiverActivity.getClass());

        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();

    }
}
