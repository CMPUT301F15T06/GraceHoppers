package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by nlovas on 11/5/15.
 */
public class EditBookActivityTest extends ActivityInstrumentationTestCase2 {
    /*
    UI tests for the edit book screen
     */

    Book book;
    Account account;
    EditText title;
    EditText author;
    EditText quantity;
    RatingBar ratingbar;
    Spinner spinner;
    CheckBox checkbox;
    Button plusbutton;
    Button minusbutton;
    Button okButton;
    TextView comments;



    public EditBookActivityTest(Class activityClass) {
        super(com.gracehoppers.jlovas.bookwrm.EditBookActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testEdit(){
        //test to see that the information can be changed with the UI




    }




}
