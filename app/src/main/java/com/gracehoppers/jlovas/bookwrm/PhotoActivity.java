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
    Button deleteButton;
    TextView fivePhotoTitleText;

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
    protected void onResume(){ //onResume
        super.onResume();

        if (getIntent().getStringExtra("flag").equals("edit")) {
            //arriving here from edit, load images if any

            Toast.makeText(getApplicationContext(),"myPhotos.hasImages ==" + myPhotos.getHasImages(), Toast.LENGTH_SHORT).show();
            if(myPhotos.getHasImages()){
                //load the images
                try {
                    Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(0), 0, myPhotos.getPhotoAtIndex(0).length);
                    Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                    photoToEdit.setImageBitmap(scaled);

                    redoButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);

                    if(myPhotos.getPhotos().size() > 1) {
                        rightButton.setEnabled(true);
                    }

                    if(myPhotos.getPhotos().size() == 5){
                        takePhotoButton.setEnabled(false);
                    }

                    imageTotalText.setText("" + 1 + "/" + myPhotos.getPhotos().size());
                    fivePhotoTitleText.setVisibility(View.INVISIBLE);

                }catch(NegativeNumberException e){
                    Toast.makeText(getApplicationContext(), "Negative Index", Toast.LENGTH_SHORT).show();
                }catch(TooLongException e){
                    Toast.makeText(getApplicationContext(), "Index too long", Toast.LENGTH_SHORT).show();
                }

            }else {
                fivePhotoTitleText.setVisibility(View.VISIBLE);
            }
            //this else is for addBookScreen's entrance to this page
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        values = new ContentValues();
        myPhotos = new Photos();
        scaler = new BitmapScaler();
        saveLoad = new SaveLoad();

        myPhotos = saveLoad.loadPhotos(getApplicationContext());

        okButton = (Button)findViewById(R.id.okAsIsButton);
        takePhotoButton = (Button)findViewById(R.id.takePhotoButton);
        photoToEdit = (ImageView)findViewById(R.id.bookImage);
        imageTotalText = (TextView)findViewById(R.id.imagetotaltextview);
        leftButton = (Button)findViewById(R.id.pictureleftbutton);
        rightButton = (Button)findViewById(R.id.picturerightbutton);
        redoButton = (Button)findViewById(R.id.retakeButton);
        deleteButton = (Button)findViewById(R.id.xButton);
        fivePhotoTitleText = (TextView)findViewById(R.id.fivePhotoTitleText);

        //redoButton should not be visible initially
        redoButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myPhotos.getPhotos().size() >0){ //NEW
                    myPhotos.setHasImages(true); //NEW
                    fivePhotoTitleText.setVisibility(View.INVISIBLE);
                }else{
                    myPhotos.setHasImages(false);
                }
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //want to delete a photo

                if(myPhotos.getPhotos().size() ==1){
                    //if only one photo, revert imageview to default
                    try {
                        myPhotos.removePhotoAtIndex(0);
                        photoToEdit.setImageResource(R.drawable.defaultbook);

                        //update ## text
                        String tmp = imageTotalText.getText().toString();
                        imageTotalText.setText("-");

                        leftButton.setEnabled(false);
                        rightButton.setEnabled(false);
                        redoButton.setVisibility(View.INVISIBLE);
                        deleteButton.setVisibility(View.INVISIBLE);
                        fivePhotoTitleText.setVisibility(View.VISIBLE);

                    }catch(NegativeNumberException e){
                        Toast.makeText(getApplicationContext(), "Index negative", Toast.LENGTH_SHORT).show();
                    }catch(TooLongException e){
                        Toast.makeText(getApplicationContext(), "Index too long", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //not just one image
                    //when someone deletes an image that is not the first one in line, they will be thrown to 1/x of whatever is left
                    //ex: on picture 3/5 and delete that one, will be put to the place of 1/4 now
                    String tmp = imageTotalText.getText().toString();
                    switch(tmp.charAt(0)){
                        case'1':
                        try {
                            myPhotos.removePhotoAtIndex(0);
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(0), 0, myPhotos.getPhotoAtIndex(0).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                            //update the text
                            imageTotalText.setText("" + 1 + "/" + myPhotos.getPhotos().size() + "");

                            if(myPhotos.getPhotos().size() ==1){
                                leftButton.setEnabled(false);
                                rightButton.setEnabled(false);
                                redoButton.setVisibility(View.INVISIBLE);
                                deleteButton.setVisibility(View.INVISIBLE);
                            }else if(myPhotos.getPhotos().size() >1){
                                rightButton.setEnabled(true);
                                takePhotoButton.setEnabled(true);
                            }
                            count=0;

                            break;
                        }catch(NegativeNumberException e){
                            Toast.makeText(getApplicationContext(), "Negative Index Number", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index too long", Toast.LENGTH_SHORT).show();
                        }

                        case '2':
                            try {
                                myPhotos.removePhotoAtIndex(1);
                                Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(0), 0, myPhotos.getPhotoAtIndex(0).length);
                                Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                                photoToEdit.setImageBitmap(scaled);

                                //update the text
                                imageTotalText.setText("" + 1 + "/" + myPhotos.getPhotos().size() + "");

                                if(myPhotos.getPhotos().size() ==1){
                                    rightButton.setEnabled(false);
                                    leftButton.setEnabled(false);
                                    redoButton.setVisibility(View.INVISIBLE);
                                    deleteButton.setVisibility(View.INVISIBLE);
                                }
                                else if(myPhotos.getPhotos().size() >1){
                                        rightButton.setEnabled(true);
                                        leftButton.setEnabled(false);
                                        takePhotoButton.setEnabled(true);
                                }

                                count=0;

                                break;
                            }catch(NegativeNumberException e){
                                Toast.makeText(getApplicationContext(), "Negative Index Number", Toast.LENGTH_SHORT).show();
                            }catch(TooLongException e){
                                Toast.makeText(getApplicationContext(), "Index too long", Toast.LENGTH_SHORT).show();
                            }

                        case '3':
                            try {
                                myPhotos.removePhotoAtIndex(2);
                                Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(0), 0, myPhotos.getPhotoAtIndex(0).length);
                                Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                                photoToEdit.setImageBitmap(scaled);

                                //update the text
                                imageTotalText.setText("" + 1 + "/" + myPhotos.getPhotos().size() + "");
                                leftButton.setEnabled(false);

                                if(myPhotos.getPhotos().size() ==1){
                                    rightButton.setEnabled(false);
                                    leftButton.setEnabled(false);
                                    redoButton.setVisibility(View.INVISIBLE);
                                    deleteButton.setVisibility(View.INVISIBLE);
                                }
                                else if(myPhotos.getPhotos().size() >1){
                                    rightButton.setEnabled(true);
                                    leftButton.setEnabled(false);
                                    takePhotoButton.setEnabled(true);
                                }

                                count=0;

                                break;
                            }catch(NegativeNumberException e){
                                Toast.makeText(getApplicationContext(), "Negative Index Number", Toast.LENGTH_SHORT).show();
                            }catch(TooLongException e){
                                Toast.makeText(getApplicationContext(), "Index too long", Toast.LENGTH_SHORT).show();
                            }
                        case '4':
                            try {
                                myPhotos.removePhotoAtIndex(2);
                                Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(0), 0, myPhotos.getPhotoAtIndex(0).length);
                                Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                                photoToEdit.setImageBitmap(scaled);

                                //update the text
                                imageTotalText.setText("" + 1 + "/" + myPhotos.getPhotos().size() + "");
                                leftButton.setEnabled(false);

                                if(myPhotos.getPhotos().size() ==1){
                                    rightButton.setEnabled(false);
                                    leftButton.setEnabled(false);
                                    redoButton.setVisibility(View.INVISIBLE);
                                    deleteButton.setVisibility(View.INVISIBLE);
                                }
                                else if(myPhotos.getPhotos().size() >1){
                                    rightButton.setEnabled(true);
                                    leftButton.setEnabled(false);
                                    takePhotoButton.setEnabled(true);
                                }

                                count=0;

                                break;
                            }catch(NegativeNumberException e){
                                Toast.makeText(getApplicationContext(), "Negative Index Number", Toast.LENGTH_SHORT).show();
                            }catch(TooLongException e){
                                Toast.makeText(getApplicationContext(), "Index too long", Toast.LENGTH_SHORT).show();
                            }
                        case '5':
                            try {
                                myPhotos.removePhotoAtIndex(3);
                                Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(0), 0, myPhotos.getPhotoAtIndex(0).length);
                                Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                                photoToEdit.setImageBitmap(scaled);

                                //update the text
                                imageTotalText.setText("" + 1 + "/" + myPhotos.getPhotos().size() + "");
                                leftButton.setEnabled(false);

                                if(myPhotos.getPhotos().size() ==1){
                                    rightButton.setEnabled(false);
                                    leftButton.setEnabled(false);
                                    redoButton.setVisibility(View.INVISIBLE);
                                    deleteButton.setVisibility(View.INVISIBLE);
                                }
                                else if(myPhotos.getPhotos().size() >1){
                                    rightButton.setEnabled(true);
                                    leftButton.setEnabled(false);
                                    takePhotoButton.setEnabled(true);
                                }
                                count=0;

                                break;
                            }catch(NegativeNumberException e){
                                Toast.makeText(getApplicationContext(), "Negative Index Number", Toast.LENGTH_SHORT).show();
                            }catch(TooLongException e){
                                Toast.makeText(getApplicationContext(), "Index too long", Toast.LENGTH_SHORT).show();
                            }
                    }

                }
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

                            rightButton.setEnabled(true);
                            break;
                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }

                    case '3':
                        count--;
                        imageTotalText.setText("" + 2 +"/" +myPhotos.getPhotos().size() +"");
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                            rightButton.setEnabled(true);
                            break;
                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }

                    case '4':
                        count--;
                        imageTotalText.setText("" + 3 +"/" +myPhotos.getPhotos().size() +"");
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                            rightButton.setEnabled(true);
                            break;
                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }

                    case '5':
                        count--;
                        imageTotalText.setText("" + 4 + "/" + myPhotos.getPhotos().size() + "");
                        rightButton.setEnabled(true);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(myPhotos.getPhotoAtIndex(count), 0, myPhotos.getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            photoToEdit.setImageBitmap(scaled);

                            rightButton.setEnabled(true);
                            break;
                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }

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

    //function for calling and returning stuff from the camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //this one is for taking a brand new picture
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

                options.inSampleSize = 8;


                Bitmap original = BitmapFactory.decodeFile(imageurl, options);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                original.compress(Bitmap.CompressFormat.PNG, 100, bytes); //very important line: bytes is what you want!!

                myPhotos.addPhoto(bytes.toByteArray());

                Bitmap changed = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

                Bitmap scaled = scaler.scaleToFitWidth(changed, 500);

                //put into the Photos object
                //myPhotos.addPhoto(changed); //scaled
                Toast.makeText(getApplicationContext(), "Photos taken1: " + myPhotos.getPhotos().size(), Toast.LENGTH_SHORT).show();
                photoToEdit.setImageBitmap(scaled);

                imageTotalText.setText("" + 1 +"/" +myPhotos.getPhotos().size() +"");

                //String tmp = imageTotalText.getText().toString();
                //if(Character.getNumericValue(tmp.charAt(0)) >1){
                    leftButton.setEnabled(false);
                //}

                if(myPhotos.getPhotos().size() >1){
                    rightButton.setEnabled(true);
                }

                if(myPhotos.getPhotos().size() ==5) {
                    takePhotoButton.setEnabled(false);
                }

                redoButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                fivePhotoTitleText.setVisibility(View.INVISIBLE);
                count=0;

                myPhotos.setHasImages(true); //NEW

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
                Toast.makeText(getApplicationContext(), "Photos taken2: " + myPhotos.getPhotos().size(), Toast.LENGTH_SHORT).show();
                photoToEdit.setImageBitmap(scaled);


                if (myPhotos.getPhotos().size() > 1 && index!=myPhotos.getPhotos().size()) {
                    rightButton.setEnabled(true);
                }

                redoButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
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

}

