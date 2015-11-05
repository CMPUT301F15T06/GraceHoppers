package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoActivity extends ActionBarActivity {
    Button okButton;
    Button takePhotoButton;
    Button galleryButton;
    ImageView photoToEdit;


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
                Toast.makeText(getApplicationContext(), "Wanted to take a photo!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Want to choose from image gallery!", Toast.LENGTH_SHORT).show();
                finish();
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
}
