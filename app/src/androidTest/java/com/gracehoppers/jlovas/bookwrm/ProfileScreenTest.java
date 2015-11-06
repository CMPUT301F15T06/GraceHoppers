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
    TextView name;
    TextView email;
    TextView city;
    Button clickEdit;
    Button confirm;
    ProfileScreen activity;


    public ProfileScreenTest(){
        super(ProfileScreen.class);
    }

    public void testStart() throws Exception {

        Activity activity = getActivity();
    }

    /*
    public void testSetVisible(){
        activity = (ProfileScreen)getActivity();
        final ArrayList<View> visibles = new ArrayList<View>();
        name.setVisibility(View.INVISIBLE);
        visibles.add(name);

        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(ProfileScreen.class.getName(), null, false);

        //ensure ClickToEdit button can perform click
        activity.runOnUiThread(new Runnable() {

            public void run() {
                activity.set_visible(visibles);
            }
        });
        
        getInstrumentation().waitForIdleSync();

        //ensure that after clicking edit button, editText replaces TextView
        ProfileScreen receiverActivity = (ProfileScreen) receiverActivityMonitor.waitForActivityWithTimeout(1000);

        assertTrue(receiverActivity.name.getVisibility()== View.VISIBLE);

        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }

    public void testSetInvisible(){
        activity = (ProfileScreen)getActivity();
        final ArrayList<View> visibles = new ArrayList<View>();
        visibles.add(name);
        name.setVisibility(View.VISIBLE);

        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(ProfileScreen.class.getName(), null, false);

        //ensure ClickToEdit button can perform click
        activity.runOnUiThread(new Runnable() {

            public void run() {
                activity.set_invisible(visibles);
            }
        });

        getInstrumentation().waitForIdleSync();

        //ensure that after clicking edit button, editText replaces TextView
        ProfileScreen receiverActivity = (ProfileScreen) receiverActivityMonitor.waitForActivityWithTimeout(1000);

        assertTrue(receiverActivity.name.getVisibility()== View.INVISIBLE);

        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }

    public void testClickToEdit(){
        //get activity and edit button
        activity = (ProfileScreen) getActivity();
        clickEdit = activity.edit;

        name=activity.name;
        email=activity.email;
        city=activity.city;


        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(ProfileScreen.class.getName(), null, false);

        //ensure ClickToEdit button can perform click
        activity.runOnUiThread(new Runnable() {

            public void run() {

                for (View v:activity.editList){
                    v.setVisibility(View.INVISIBLE);
                }
                for (View v:activity.originalList){
                    v.setVisibility(View.VISIBLE);
                }
                clickEdit.performClick();

            }
        });
        getInstrumentation().waitForIdleSync();

        //ensure that after clicking edit button, editText replaces TextView
        ProfileScreen receiverActivity = (ProfileScreen) receiverActivityMonitor.waitForActivityWithTimeout(1000);


        for(View v :receiverActivity.editList){
            assertTrue(v.getVisibility() == View.VISIBLE);
        }
        for(View v:receiverActivity.originalList){
            assertTrue(v.getVisibility()== View.INVISIBLE);
        }

        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }*/

    public void testEdit(){
        activity = (ProfileScreen)getActivity();

        //Set up ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(ProfileScreen.class.getName(), null, false);

        editcity=activity.editcity;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editcity.setText("Edmonton");
            }
        });
        getInstrumentation().waitForIdleSync();

        editemail=activity.editemail;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editemail.setText("well@well.com");
            }
        });
        getInstrumentation().waitForIdleSync();

        ProfileScreen receiverActivity = (ProfileScreen) receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertEquals("Edmonton", receiverActivity.account.getCity());
        assertEquals("well@well.com", receiverActivity.account.getEmail());

        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }

}
