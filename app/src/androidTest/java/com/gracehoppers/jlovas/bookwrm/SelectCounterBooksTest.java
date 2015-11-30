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

    int pos;

    /*
    this test include user cases : select counterBooks and edit

     */

    public SelectCounterBooksTest(){
        super(SelectCounterBooksActivity.class);

    }

    public void testSelectCorrectBook(){
        SelectCounterBooksActivity activity = (SelectCounterBooksActivity) getActivity();

        pos = activity.realpos;

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

        //assert return to right page

        CounterTradeScreen receiverActivity = (CounterTradeScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                CounterTradeScreen.class, receiverActivity.getClass());

        getInstrumentation().removeMonitor(receiverActivityMonitor);


        //assert user can select from borrower's inventory
        //and assert it is the correct book that user chooses
        assertEquals(receiverActivity.counterTrade.getBorrowerBook().get(pos).getTitle(), activity.bookList.get(pos).getTitle());

        receiverActivity.finish();

    }
}
