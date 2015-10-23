package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

/**
 * Created by nlovas on 10/21/15.
 */
public class SaveLoadTest extends ActivityInstrumentationTestCase2 {

    //@Override
    public SaveLoadTest(){
        super(Account.class);
    }

    public void testSaveInFile() throws IllegalEmailException, NoSpacesException, TooLongException { //tests whether a file saved or not under certain circumstances
        //test to see if it saves
        SaveLoad saveload = new SaveLoad();
        //AndroidTestCase androidtestcase = new AndroidTestCase(); //so that we can get a context for saving/loading
        Account testa = new Account();
        testa.setUsername("JohnEgbert");
        testa.setEmail("GT@pesterchum.ca");
        testa.setCity("Sburb");

        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        //http://stackoverflow.com/questions/5544205/accessing-application-context-from-testsuite-in-setup-before-calling-getactivi, user Salsero69, 2015-23-10
        saveload.saveInFile(context, testa); //save testa into the file
        Account testa2 = new Account();
        testa2.setUsername("JadeHarley");
        testa2=saveload.loadFromFile(context,testa2);
     assertTrue(testa2.getUsername().equals(testa.getUsername()) && testa2.getEmail().equals(testa.getEmail()) && testa2.getCity().equals(testa.getCity())
                && testa2.getInventory().getSize()==testa.getInventory().getSize());



        /*testa2=saveload.loadFromFile(androidtestcase.getContext(),testa2);
        assertTrue( testa2==testa); //test2a should have tasta's info inside





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
