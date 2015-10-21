package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.mock.MockContext;

/**
 * Created by nlovas on 10/21/15.
 */
public class SaveLoadTest extends ActivityInstrumentationTestCase2 {

    //@Override
    public SaveLoadTest(){
        super(Account.class);
    }

    public void testSaveInFile() throws IllegalEmailException, NoSpacesException { //tests whether a file saved or not under certain circumstances
        //first circumstance: duplicate username and/or email
       /* SaveLoad saveload = new SaveLoad();
        AndroidTestCase androidtestcase = new AndroidTestCase(); //so that we can get a context for saving/loading
        Account testa = new Account("cmput301", "cmput301@ualberta.ca", "Edmonton");

        saveload.saveInFile(androidtestcase.getContext(), testa);
        Account testa2 = new Account("coolio","w@one.ca","grande prairie");
        testa2=saveload.loadFromFile(androidtestcase.getContext(),testa2);
        assertTrue( testa2==testa); //test2a should have tasta's info inside           */




/*
        Account testb = new Account("cmput301", "cmput301@ualberta.ca", "Edmonton");


        try {saveload.saveInFile(testb);
        } catch (AlreadyExistsException e){

            showMessage("username or email already exists"); //will write method for this

        }
        //second circumstance: wont save empty fields
        Account testc = new Account();
        testc = saveload.loadFromFile();

        assertFalse(testc==testb);



        Account testr = new Account("","","");

        try {saveload.saveInFile(testr);
        } catch (BlankFieldException e){

            showMessage("please fill all fields"); //will write method for this

        }

        assertFalse(saveload.loadFromFile() == testr);
*/
    }


}
