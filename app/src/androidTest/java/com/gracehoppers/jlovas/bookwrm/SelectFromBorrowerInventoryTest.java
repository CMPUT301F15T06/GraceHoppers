package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by chen1 on 11/5/15.
 */
public class SelectFromBorrowerInventoryTest extends ActivityInstrumentationTestCase2 {

    public SelectFromBorrowerInventoryTest() {
        super(SelectFromBorrowerInventoryActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testGoingBackToTrade(){
        SelectFromBorrowerInventoryActivity activity = (SelectFromBorrowerInventoryActivity) getActivity();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(CreateTradeScreen.class.getName(),
                        null, false);

        final ListView getMyInventory = activity.getMyInventory();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = getMyInventory.getChildAt(0);
                getMyInventory.performItemClick(v,0,v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        CreateTradeScreen receiverActivity = (CreateTradeScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                CreateTradeScreen.class, receiverActivity.getClass());

        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }

}