package com.gracehoppers.jlovas.bookwrm;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by nlovas on 10/15/15.
 */
public class testAccount extends ActivityInstrumentationTestCase2{


        @Override
        public CreateAccountTest(){
            super(Account.class);
        }

        public void testSaveAccount(){ //tests whether a file saved or not under certain circumstances
            //first circumstance: duplicate username and/or email
            //DataModel dataModel =new DataModel();
            Account testa = new Account("cmput301", "cmput301@ualberta.ca", "Edmonton");

            dataModel.save(testa);

            assertTrue(dataModel.load() == testa);

            Account testb = new Account("cmput301", "cmput301@ualberta.ca", "Edmonton");


            try {dataModel.save(testb);
            } catch (AlreadyExistsException e){

                showMessage("username or email already exists"); //will write method for this

            }
            //second circumstance: wont save empty fields
            Account testc = new Account();
            testc = loadFromFile();

            assertFalse(testc==testb);



            Account testr = new Account("","","");

            try {dataModel.save(testr);
            } catch (BlankFieldException e){

                showMessage("please fill all fields"); //will write method for this

            }

            assertfalse(dataModel.load() == testr);

        }
    
    }


