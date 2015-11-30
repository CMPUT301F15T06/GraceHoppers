package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

/**
 * This test tests if a user can clone an item.
 * We begin the test on ViewBookActivity on another user's page.
 * Requires data on the phone. Have a friend.
 * Requires connection to the internet/server.
 *
 * Created by jlovas on 11/30/15.
 */
public class UseCase10 extends ActivityInstrumentationTestCase2 {

    public UseCase10(){
        super(com.gracehoppers.jlovas.bookwrm.ViewBookActivity.class);
    }

    public void testCloneBook(){

        //build intent to go to viewBookActivity
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        Intent intenty = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), ViewBookActivity.class);
        intenty.putExtra("flag", "search");
        intenty.putExtra("listPosition", 0);
        setActivityIntent(intenty);

        ViewBookActivity activity = (ViewBookActivity)getActivity();

        //first need a book and account
        SaveLoad saveLoad = new SaveLoad();
        Account accountA = saveLoad.loadFromFile(context); //requires the server and data in order to run

        //server has gone down, can no longer work on project :c

        //1. The user clicks the button on a friend's item
        //2. If yes, the system creates a new item in the user's inventory that is the same as the friend's item.


    }
}
