package com.gracehoppers.jlovas.bookwrm;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * PhotoActivity allows the user to look at the image listed for their book.
 * <p>
 *     They can choose to accept the image, take one with their camera, or choose from their phone's
 *     photo gallery. The image is displayed large on this screen for the user to see clearly.
 *     There is not a lot of functionality on this page because adding an image has not yet
 *     been implemented.
 * </p>
 *
 * @author Jillian Lovas
 *
 * @see AddBookScreen, EditBookActivity, Book
 *
 */
public class PhotoActivity extends ActionBarActivity {
    Button okButton;
    Button takePhotoButton;
    Button galleryButton;
    ImageView photoToEdit;
    BitmapScaler scaler;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final static int SELECT_PHOTO = 12345;
    Photos myPhotos;

    ContentValues values;
    Uri imageUri;
    Bitmap scaledBitmap;

    //for UI testing ---------------------------------------------------
    public ImageView getImage(){ return photoToEdit;};
    public Button getOkButton(){return okButton;}
    public Button getGalleryButton(){ return galleryButton;}
    public Button getTakePhotoButton(){return takePhotoButton;}
    // -----------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        values = new ContentValues();
        myPhotos = new Photos();
        scaler = new BitmapScaler();

        okButton = (Button)findViewById(R.id.okAsIsButton);
        takePhotoButton = (Button)findViewById(R.id.takePhotoButton);
        galleryButton = (Button)findViewById(R.id.chooseFromGalleryButton);
        photoToEdit = (ImageView)findViewById(R.id.bookImage);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Picked ok!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //credit to: Antrromet, http://stackoverflow.com/questions/10377783/low-picture-image-quality-when-capture-from-camera, Nov 13, 2015
                //http://stackoverflow.com/users/451951/antrromet
                //for help with grabbing the full-sized image from a camera photo
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);


                //Toast.makeText(getApplicationContext(), "Want to choose from image gallery!", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //function for calling and returning stuff from the camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data"); //this method grabs a small thumbnail only - need?


            //something needs to happen here to rotate the image to the right orientation
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                //photoToEdit.setImageBitmap(imageBitmap); //full image now, not a thumbnail
                String imageurl = getRealPathFromURI(imageUri); //grab's the file path to the image... I think

                //attempting to compress the image size
                //setting options to squash this beast down
                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inSampleSize = 8; //test value, needs powers of 2


                Bitmap original = BitmapFactory.decodeFile(imageurl, options);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                if(original.compress(Bitmap.CompressFormat.PNG, 100, bytes)){
                    Toast.makeText(getApplicationContext(), "Did it!", Toast.LENGTH_SHORT).show();
                }
                Bitmap changed = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

                    Toast.makeText(getApplicationContext(), "Bytes before is: " + imageBitmap.getByteCount() + " Bytes of the image after is: " + changed.getByteCount(), Toast.LENGTH_SHORT).show();

                Bitmap scaled = scaler.scaleToFitWidth(changed, 500);

                //put into the Photos object
                myPhotos.addPhoto(changed); //scaled
                photoToEdit.setImageBitmap(scaled);


            } catch (Exception e) {
                //should never happen after UI stuff has been made properly
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Too many photos added, cannot add the photo", Toast.LENGTH_SHORT).show();
            }



        }
        else if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK){
            //do stuff with gallery choice!


            //come back to this: http://stackoverflow.com/questions/30004658/pick-an-image-from-gallery-in-android-studio idk if any good
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            //imageView.setImageBitmap(bitmap);

        }

    }

    //credit to: Antrromet, http://stackoverflow.com/questions/10377783/low-picture-image-quality-when-capture-from-camera, Nov 13, 2015
    //http://stackoverflow.com/users/451951/antrromet
    //for help with grabbing the full-sized image from a camera photo
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    ////////////use this?????
    // Decodes image and scales it to reduce memory consumption
    public Bitmap decodeMyFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
}

