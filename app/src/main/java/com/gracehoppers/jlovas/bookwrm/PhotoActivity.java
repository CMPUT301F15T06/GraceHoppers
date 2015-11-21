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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
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

    ImageView photoToEdit;
    TextView imageTotalText;
    Button leftButton;
    Button rightButton;
    Button redoButton;

    BitmapScaler scaler;


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final static int REDO_PHOTO = 12345;
    Photos myPhotos;
    SaveLoad saveLoad;

    ContentValues values;
    Uri imageUri;
    int count=0;

    //for UI testing ---------------------------------------------------
    public ImageView getImage(){ return photoToEdit;};
    public Button getOkButton(){return okButton;}

    public Button getTakePhotoButton(){return takePhotoButton;}
    // -----------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        values = new ContentValues();
        myPhotos = new Photos();
        scaler = new BitmapScaler();
        saveLoad = new SaveLoad();

        okButton = (Button)findViewById(R.id.okAsIsButton);
        takePhotoButton = (Button)findViewById(R.id.takePhotoButton);
        photoToEdit = (ImageView)findViewById(R.id.bookImage);
        imageTotalText = (TextView)findViewById(R.id.imagetotaltextview);
        leftButton = (Button)findViewById(R.id.pictureleftbutton);
        rightButton = (Button)findViewById(R.id.picturerightbutton);
        redoButton = (Button)findViewById(R.id.retakeButton);

        //redoButton should not be visible initially
        redoButton.setVisibility(View.GONE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                //result.putExtra("Object", myPhotos);
                setResult(PhotoActivity.RESULT_OK, result);
                saveLoad.savePhotos(getApplicationContext(), myPhotos);
                finish();
            }
        });

        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete the previous picture (ONLY when you actually take one) and replace the previous
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REDO_PHOTO);
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


        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //moves the photo left
                //notes: the images are appearing from most recently taken to least recently taken, hence the way I have getAtIndex'd

                String tmp = imageTotalText.getText().toString();

                switch(tmp.charAt(0)){
                    case '2':
                        count--;
                        imageTotalText.setText("" + 1 + "/" + myPhotos.getPhotos().size() + "");
                        rightButton.setEnabled(true);
                        leftButton.setEnabled(false);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case '3':
                        count--;
                        imageTotalText.setText("" + 2 +"/" +myPhotos.getPhotos().size() +"");
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case '4':
                        count--;
                        imageTotalText.setText("" + 3 +"/" +myPhotos.getPhotos().size() +"");
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case '5':
                        count--;
                        imageTotalText.setText("" + 4 +"/" +myPhotos.getPhotos().size() +"");
                        rightButton.setEnabled(true);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //moves the photo right
                //notes: the images are appearing from most recently taken to least recently taken, hence the way I have getAtIndex'd

                String tmp = imageTotalText.getText().toString();

                switch(tmp.charAt(0)){
                    case '4':
                        count++;
                        imageTotalText.setText("" + 5 + "/" + myPhotos.getPhotos().size() + "");
                        if(Character.getNumericValue(tmp.charAt(0)+1) == (char)myPhotos.getPhotos().size()) rightButton.setEnabled(false);
                        rightButton.setEnabled(false);

                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case '3':
                        count++;
                        imageTotalText.setText("" + 4 +"/" +myPhotos.getPhotos().size() +"");
                        if(Character.getNumericValue(tmp.charAt(0)+1) == (char)myPhotos.getPhotos().size()) rightButton.setEnabled(false);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case '2':
                        count++;
                        imageTotalText.setText("" + 3 + "/" + myPhotos.getPhotos().size() + "");
                        if(Character.getNumericValue(tmp.charAt(0)+1) == (char)myPhotos.getPhotos().size()) rightButton.setEnabled(false);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case '1':
                        count++;
                        imageTotalText.setText("" + 2 + "/" + myPhotos.getPhotos().size() + "");
                        if(Character.getNumericValue(tmp.charAt(0)+1) == (char)myPhotos.getPhotos().size()) rightButton.setEnabled(false);
                        leftButton.setEnabled(true);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        });



    }//end of onCreate


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
                original.compress(Bitmap.CompressFormat.PNG, 100, bytes); //very important line: bytes is what you want!!

                myPhotos.addPhoto(bytes.toByteArray());

                Bitmap changed = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

                Toast.makeText(getApplicationContext(), "Bytes before is: " + imageBitmap.getByteCount() +
                     " Bytes of the image after is: " + changed.getByteCount(), Toast.LENGTH_SHORT).show();

                Bitmap scaled = scaler.scaleToFitWidth(changed, 500);

                //put into the Photos object
                //myPhotos.addPhoto(changed); //scaled
                Toast.makeText(getApplicationContext(), "Photos taken: " + myPhotos.getPhotos().size(), Toast.LENGTH_SHORT).show();
                photoToEdit.setImageBitmap(scaled);

                imageTotalText.setText("" + 1 +"/" +myPhotos.getPhotos().size() +""); //that 1 is gonna be wrong sometimes

                if(myPhotos.getPhotos().size() >1){
                    rightButton.setEnabled(true);
                }

                if(myPhotos.getPhotos().size() ==5) {
                    takePhotoButton.setEnabled(false);
                }

                redoButton.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                //should never happen after UI stuff has been made properly
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Too many photos added, cannot add the photo", Toast.LENGTH_SHORT).show();
            }
                }else if (requestCode == REDO_PHOTO && resultCode == RESULT_OK) {
            //delete the previous photo and replace with currently taken one

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
                original.compress(Bitmap.CompressFormat.PNG, 100, bytes); //very important line: bytes is what you want!!

                //DELETE PREVIOUS PHOTO
                String tmp= imageTotalText.getText().toString();
                int index = Character.getNumericValue(tmp.charAt(0));

                myPhotos.swapPhotoAtIndex(index-1, bytes.toByteArray());

                Bitmap changed = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

                Toast.makeText(getApplicationContext(), "Bytes before is: " + imageBitmap.getByteCount() +
                        " Bytes of the image after is: " + changed.getByteCount(), Toast.LENGTH_SHORT).show();

                Bitmap scaled = scaler.scaleToFitWidth(changed, 500);

                //put into the Photos object
                //myPhotos.addPhoto(changed); //scaled
                Toast.makeText(getApplicationContext(), "Photos taken: " + myPhotos.getPhotos().size(), Toast.LENGTH_SHORT).show();
                photoToEdit.setImageBitmap(scaled);


                if (myPhotos.getPhotos().size() > 1) {
                    rightButton.setEnabled(true);
                }



                redoButton.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Redo a picture result found!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                //should never happen after UI stuff has been made properly
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Too many photos added, cannot add the photo", Toast.LENGTH_SHORT).show();


            }

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

