package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by jlovas on 11/5/15.
 */
public class PhotoActivityTest extends ActivityInstrumentationTestCase2 {

    public PhotoActivityTest(){
        super(PhotoActivity.class);
    }

    //tests here are all going to fail because there is no functionality and I'm not really sure how
    //to test calling something like the gallery or the camera

    //test saying ok to current picture
    public void testOkChoice(){

        PhotoActivity activity = (PhotoActivity)getActivity();

        //set the image back to default
        ImageView image = activity.getImage();



        //assert this is the default image

        //credit to: Mejonzhan for comparing two images
        //http://stackoverflow.com/questions/9125229/comparing-two-drawables-in-android
        //http://stackoverflow.com/users/1043256/mejonzhan
        assertTrue(image.getDrawable().getConstantState().equals
                (activity.getResources().getDrawable(R.drawable.defaultbook).getConstantState()));

        //push ok

        final Button okButton = activity.getOkButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                okButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        //make sure ok returns us to the previous activity (add book)

        Activity difActivity = getActivity();

        //assert we made it to addBookScreen

        AddBookScreen activity2 = (AddBookScreen)getActivity(); //how do you compare two activities when it returns to previous activity with finish()??

        assertTrue(difActivity==activity2);

        //make sure the image in the imageview is the image we said ok to

        //ImageView addBookImage = activity.getThePhoto(); //how to access something when not passed through an intent??

    }

    public void testGalleryChoice(){
        //test that the image chosen from the gallery is the same as the one displayed

        PhotoActivity activity = (PhotoActivity)getActivity();

        //set the image back to default
        ImageView image = activity.getImage();



        //assert this is the default image

        //credit to: Mejonzhan for comparing two images
        //http://stackoverflow.com/questions/9125229/comparing-two-drawables-in-android
        //http://stackoverflow.com/users/1043256/mejonzhan
        assertTrue(image.getDrawable().getConstantState().equals
                (activity.getResources().getDrawable(R.drawable.defaultbook).getConstantState()));

        //push gallery button

        final Button galleryButton = activity.getGalleryButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                galleryButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        //do something involving picking an image from gallery - I have no idea how to do this

        //assert image is within size range

        //assert that the chosen image is not the default (unless they hit cancel?)
        ImageView newImage = activity.getImage();
        //assertFalse(image.getDrawable().getConstantState().equals()) image from gallery?

    }

    public void testCameraChoice(){
        PhotoActivity activity = (PhotoActivity)getActivity();

        //set the image back to default
        ImageView image = activity.getImage();



        //assert this is the default image

        //credit to: Mejonzhan for comparing two images
        //http://stackoverflow.com/questions/9125229/comparing-two-drawables-in-android
        //http://stackoverflow.com/users/1043256/mejonzhan
        assertTrue(image.getDrawable().getConstantState().equals
                (activity.getResources().getDrawable(R.drawable.defaultbook).getConstantState()));

        //push camera button

        final Button cameraButton = activity.getTakePhotoButton();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cameraButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        //do something involving taking a picture - I have no idea how to do this

        //assert image is within size range

        //assert that the chosen image is not the default (unless they hit cancel?)
        ImageView newImage = activity.getImage();
        //assertFalse(image.getDrawable().getConstantState().equals()) taken picture?
    }
}
