package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by chen1 on 11/5/15.
 */
public class CreateTradeScreenTest extends ActivityInstrumentationTestCase2 {

    public CreateTradeScreenTest(){
        super(ProcessTradeScreen.class);
    }

    public void testStart() throws Exception {

        Activity activity = getActivity();
    }


    public void testAdd(){
        CreateTradeScreen activity = (CreateTradeScreen) getActivity();

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(SelectFromBorrowerInventoryActivity.class.getName(),
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

    public void testSelect(){
        CreateTradeScreen activity = (CreateTradeScreen) getActivity();

        //Set up ActivityMonitor
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(SelectFromOwnerInventoryActivity.class.getName(),
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

}
