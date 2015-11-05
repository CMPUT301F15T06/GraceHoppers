package com.gracehoppers.jlovas.bookwrm;

import android.provider.ContactsContract;
import android.test.ActivityInstrumentationTestCase2;

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
        //make sure the current picture is the default

        //make sure ok returns us to the previous activity (add book)

        //make sure the image in the imageview is the image we said ok to
    }

    public void testGalleryChoice(){
        //test that the image chosen from the gallery is the same as the
    }

    public void testCameraChoice(){

    }
}
