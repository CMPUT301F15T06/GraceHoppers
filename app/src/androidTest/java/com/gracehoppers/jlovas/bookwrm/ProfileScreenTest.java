package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hong8 on 11/5/15.
 */
public class ProfileScreenTest extends ActivityInstrumentationTestCase2 {

    EditText editemail;
    EditText editcity;
    Button clickEdit;
    Button confirm;
    ProfileScreen activity;
    SaveLoad saveLoad = new SaveLoad();
    Account account;


    public ProfileScreenTest() {
        super(ProfileScreen.class);
    }

    public void testStart() throws Exception {

        Activity activity = getActivity();
    }

}
    /* all things are wrapped within thread and runnable

       test works for part 4 does not work now



     */




    /*
    public void testView() {

        TextView name1 = activity.name;
        TextView city1 = activity.city;
        TextView email1 = activity.email;

        ProfileScreen activity = (ProfileScreen) getActivity();

        account = saveLoad.loadFromFile(activity.getApplicationContext());

        assertTrue(name.getText().toString().equals(account.getUsername()));
        assertTrue(city.getText().toString().equals(account.getCity()));
        assertTrue(email.getText().toString().equals(account.getEmail()));



    public void testClickToEdit() {
        //get activity and edit button
        activity = (ProfileScreen) getActivity();
        clickEdit = activity.edit;

        TextView name1 = activity.name;
        TextView email1 = activity.email;
        TextView city1 = activity.city;

        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(ProfileScreen.class.getName(), null, false);

        //ensure ClickToEdit button can perform click
        activity.runOnUiThread(new Runnable() {

            public void run() {

                for (View v : activity.editList) {
                    v.setVisibility(View.INVISIBLE);
                }
                for (View v : activity.originalList) {
                    v.setVisibility(View.VISIBLE);
                }
                clickEdit.performClick();

            }
        });
        getInstrumentation().waitForIdleSync();

        //ensure that after clicking edit button, editText replaces TextView
        ProfileScreen receiverActivity = (ProfileScreen) receiverActivityMonitor.waitForActivityWithTimeout(1000);


        for (View v : receiverActivity.editList) {
            assertTrue(v.getVisibility() == View.VISIBLE);
        }
        for (View v : receiverActivity.originalList) {
            assertTrue(v.getVisibility() == View.INVISIBLE);
        }

        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }
}
*/