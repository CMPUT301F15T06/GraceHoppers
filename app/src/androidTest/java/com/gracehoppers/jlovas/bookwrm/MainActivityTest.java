package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by nlovas on 10/15/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    /*
    Tests for the UI in the MainActivity, one for testing signing up and one for testing logging in

    Much of this code is referenced directly or altered from the CMPUT 301 labs. University of Alberta:
     TA's Joshua Campbell, Shida He, Stephen Romansky, and Ian Watts, 2015-15-10
     */

    private Button signupbutton;
    private Button loginbutton;
    private EditText username;

    public MainActivityTest(){
        super(com.gracehoppers.jlovas.bookwrm.MainActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }



    public void testSignup(){ //test for going to the signup activity
        //when you call getActivity, android will start the app and the activity
        MainActivity activity = (MainActivity)getActivity();

        signupbutton = activity.getSignUpButton();



        //ensure the signup activity starts up
        //following from https://developer.android.com/training/activity-testing/activity-functional-testing.html ,2015-23-10
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(SignUpActivity.class.getName(),
                        null, false);


                //click on the signup button
        activity.runOnUiThread(new Runnable() {

            public void run() {
                signupbutton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        SignUpActivity receiverActivity = (SignUpActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                SignUpActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        receiverActivity.finish();
    }



    public void testLogIn(){
        //UI testing for logging in--INCOMPLETE!! update this once database is added

        //when you call getActivity, android will start the app and the activity
        MainActivity activity = (MainActivity)getActivity();

        loginbutton = activity.getLogInButton();

        username = activity.getEditText();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                username.setText("tester");
            }
        });
        getInstrumentation().waitForIdleSync();

        //ensure the homescreen activity starts up
        //following from https://developer.android.com/training/activity-testing/activity-functional-testing.html ,2015-23-10
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(HomeScreen.class.getName(),
                        null, false);


        //click on the login button
        activity.runOnUiThread(new Runnable() {

            public void run() {
                loginbutton.performClick();
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

        receiverActivity.finish();

    }

}
