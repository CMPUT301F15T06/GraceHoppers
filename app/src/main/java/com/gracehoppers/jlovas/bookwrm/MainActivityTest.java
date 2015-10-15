package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by nlovas on 10/15/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    /*
    Tests for the UI in the MainActivity

    Much of this code is referenced directly or altered from the CMPUT 301 labs. University of Alberta:
     TA's Joshua Campbell, Shida He, Stephen Romansky, and Ian Watts, 2015-15-10
     */

    public MainActivityTest(){
        super(com.gracehoppers.jlovas.bookwrm.MainActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testSignup(){ //test for going to the signup activity
        //when you call getActivity, android will start the app and the activity
        MainActivity activity = (MainActivity)getActivity();

        // reset the app to a known state:
        //delete any current accounts/items it has
        activity.getBookList().clear();
        activity.getAccount.clear(); //may replace this method with a better one

    }
}
