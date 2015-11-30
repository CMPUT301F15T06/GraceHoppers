package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * This test addresses adding a new item into inventory.
 * Starts on the Homescreen, as indicated by Wiki usecase.
 * Requires connection to the internet/server.
 *
 * Created by jlovas on 11/29/15.
 */
public class UseCase1 extends ActivityInstrumentationTestCase2 {
    private Button addButton;
    private ImageView photo;
    private ImageView photoAct;
    private Button okPhotoButton;
    SaveLoad sl;


    public UseCase1(){
        super(com.gracehoppers.jlovas.bookwrm.HomeScreen.class);
    }

    public void testAddNewItemToInventory() throws NoSpacesException, IllegalEmailException, TooLongException, NegativeNumberException{
        sl = new SaveLoad();
        Intent intenty = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), HomeScreen.class);

        intenty.putExtra("username", "MockAccount");
        setActivityIntent(intenty);

        Account account = new Account();
        account.setUsername("MockAccount");
        account.setEmail("shadownater@hotmail.com");
        account.setCity("GP");

        sl.saveInFile(getActivity().getApplicationContext(), account);

        HomeScreen activity = (HomeScreen)getActivity();
        addButton = activity.getAddBookButton();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(AddBookScreen.class.getName(),
                        null, false);

        //1. Owner choose Add Item option
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final AddBookScreen receiverActivity = (AddBookScreen)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                AddBookScreen.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        //getInstrumentation().removeMonitor(receiverActivityMonitor);

        //2. System prompts the owner for name, author, quantity, quality, category, share preferences, and photos


        //fill the title field
        final EditText titleText = receiverActivity.getTitleText();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleText.setText("Fangirl");
            }
        });
        getInstrumentation().waitForIdleSync();

        //fill the author field
        final EditText authorText = receiverActivity.getAuthorText();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                authorText.setText("Rainbow Rowell");
            }
        });
        getInstrumentation().waitForIdleSync();

        //select the quantity (2)
        final EditText quantityText = receiverActivity.getQuantityText();
        final Button plusButton = receiverActivity.getPlusButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                plusButton.performClick();
                //quantityText.setText("2");
            }
        });
        getInstrumentation().waitForIdleSync();

        //select the quality of the book
        final RatingBar stars = receiverActivity.getStars();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //stars.performHapticFeedback(5);
                stars.setRating(5);
            }
        });
        getInstrumentation().waitForIdleSync();


        //make the book private, just to test it
        final CheckBox isPrivate = receiverActivity.getPrivateCheckBox();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isPrivate.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        //select a category
        final Spinner spinner = receiverActivity.getMySpinner();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Not sure how to do it here
                //spinner.performClick();
                //spinner.performItemClick(v,5,  )
            }
        });
        getInstrumentation().waitForIdleSync();

        //http://stackoverflow.com/questions/13042015/testing-onactivityresult
        Intent returnIntent = new Intent();
        returnIntent.putExtra("flag", 1000); //is this request or result code??? should be requestCode - so why isnt it working??
        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, returnIntent);

        //Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(AddBookScreen.class.getName(), activityResult , true);

        //3. Owner may add photograph of added item
        Instrumentation.ActivityMonitor receiverActivityMonitor2 =
                getInstrumentation().addMonitor(PhotoActivity.class.getName(),
                        activityResult, false); //changed from false



/*Not working, will not be testing taking a photo in this case
        //Push photo button
        photo = receiverActivity.getPhotoButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                photo.performClick();
            }
        });
        //AddBookScreen what = (AddBookScreen)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        //getInstrumentation().waitForIdleSync();

        // Validate that ReceiverActivity is started
        final PhotoActivity receiverActivity2 = (PhotoActivity)
                receiverActivityMonitor2.waitForActivityWithTimeout(1000);
        assertNotNull("ReceiverActivity is null", receiverActivity2);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor2.getHits());
        assertEquals("Activity is of wrong type",
                PhotoActivity.class, receiverActivity2.getClass());

        //take a picture - can't actually take a photo (different application)
        //so i am going to fake it and give it a fake photo - default image



        photoAct = receiverActivity2.getImage();
        Bitmap test = ((BitmapDrawable) photoAct.getDrawable()).getBitmap();


        assertTrue(test != null);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assertTrue(test.compress(Bitmap.CompressFormat.PNG, 100, bytes));

        Photos testPhotos = new Photos();
        testPhotos.addPhoto(bytes.toByteArray());
        sl.savePhotos(receiverActivity2.getApplicationContext(), testPhotos);

        //say ok
        okPhotoButton = receiverActivity2.getOkButton();



        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                okPhotoButton.performClick();

            }
        });
        getInstrumentation().waitForIdleSync();

        // Wait for the ActivityMonitor to be hit, Instrumentation will then return the mock ActivityResult:
        PhotoActivity childActivity = (PhotoActivity) getInstrumentation().waitForMonitorWithTimeout(receiverActivityMonitor2, 5);


        //by here I want to have loaded my image into myPhotos and then I should be able to load them from that gson
        //see commented out code below

        //account = sl.loadFromFile(receiverActivity.getApplicationContext());


        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor2);
*/
        //4. System store photograph of item onto server
        //5. Owner Add the item
        //6. System stores the item on the server - not sure how to do this with a MockAccountManager if the action is in the UI
        //so the mock stuff is being stored in MockAccount
        final Button okButton = receiverActivity.getOkButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                okButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        //At this point, the book should be added to the list

        account = sl.loadFromFile(receiverActivity.getApplicationContext());
        //assertTrue(account.getInventory().getBookByIndex(0).getPhotos().getHasImages());



        //assertTrue(books.get(0).getPhotos().getPhotoAtIndex(0) == testPhotos.getPhotoAtIndex(0));
        assertTrue(account.getInventory().getBookByIndex(0).getTitle().equals("Fangirl"));


        //7. System directs owner to inventory page
    }



    //im really not sure how to use this in my test - how do i get it to access this when it is being called from the UI?
    public class MockAccountManager extends AccountManager{

        private static final String URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t06/mockaccount/";
        private static final String TAG = "MockAccountSearch";
        Gson gson;
    }

}
