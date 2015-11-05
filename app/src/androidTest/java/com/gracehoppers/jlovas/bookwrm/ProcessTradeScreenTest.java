package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;

/**
 * Created by hong8 on 11/5/15.
 */
public class ProcessTradeScreenTest extends ActivityInstrumentationTestCase2{

    public ProcessTradeScreenTest(){
        super(ProcessTradeScreen.class);
    }

    public void testStart() throws Exception {

        Activity activity = getActivity();
    }

    public void testAccept(){
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button accept = activity.accept;


        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(ProfileScreen.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                accept.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        ProcessTradeScreen receiverActivity = (ProcessTradeScreen) receiverActivityMonitor.waitForActivityWithTimeout(1000);

        assertTrue(receiverActivity.trade.getAccepted());
        assertNotNull("acceptDialog is null", receiverActivity.dialog);

        //remove Monitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }

    public void testDecline(){
        ProcessTradeScreen activity = (ProcessTradeScreen) getActivity();
        final Button decline = activity.decline;

        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(ProfileScreen.class.getName(), null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                decline.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();

        ProcessTradeScreen receiverActivity = (ProcessTradeScreen) receiverActivityMonitor.waitForActivityWithTimeout(1000);

        assertTrue(receiverActivity.trade.getDeclined());
        assertNotNull("declineDialog is null", receiverActivity.dialog1);

        //remove Monitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }
}
