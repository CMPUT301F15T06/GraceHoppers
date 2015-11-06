package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by nlovas on 10/23/15.
 */
public class SignUpActivityTest extends ActivityInstrumentationTestCase2 {

     /*
    Tests for the UI in the SignUpActivity

    Much of this code is referenced directly or altered from the CMPUT 301 labs. University of Alberta:
     TA's Joshua Campbell, Shida He, Stephen Romansky, and Ian Watts, 2015-15-10
     */
     public SignUpActivityTest(){
         super(com.gracehoppers.jlovas.bookwrm.SignUpActivity.class);
     }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testSignUp(){
        //tests that the signUp UI behaves as expected
        SignUpActivity activity = (SignUpActivity) getActivity();

        //reset the app to a known state
        activity.getAccounts().clear();

        //add an account using UI
        //fill city field
        final EditText cityText = activity.getCityText();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cityText.setText("YEG");
            }
        });

        getInstrumentation().waitForIdleSync();

        //fill email field
        final EditText emailText = activity.getEmailText();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emailText.setText("xyz@gmail.com");
            }
        });
        getInstrumentation().waitForIdleSync();

        //fill username field
        final EditText usernameText = activity.getUsernameText();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                usernameText.setText("testAccount");
            }
        });
        getInstrumentation().waitForIdleSync();

        final Button signButton = activity.getSignButton();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(HomeScreen.class.getName(),
                        null, false);
        //the code where they clicked was here

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                signButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        HomeScreen receiverActivity = (HomeScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomeScreen.class, receiverActivity.getClass());


        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        //clean up our activities at the end of the test
        receiverActivity.finish();


        //Make sure that the account was actually added
        ArrayList<Account> accounts = activity.getAccounts();
        assertEquals("testAccount", accounts.get(0).getUsername());

    }




}
