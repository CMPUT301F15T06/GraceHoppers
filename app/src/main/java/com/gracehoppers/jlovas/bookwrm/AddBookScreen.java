package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddBookScreen extends ActionBarActivity {
    Book myBook;

    EditText titleText;
    EditText authorText;
    EditText quantityText;
    Spinner mySpinner;
    Button okButton;
    ImageView thePhoto;
    Button minusButton;
    Button plusButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_screen);

        titleText = (EditText)findViewById(R.id.titleText);
        authorText = (EditText)findViewById(R.id.authorText);
        quantityText = (EditText)findViewById(R.id.quantityText);
        okButton = (Button)findViewById(R.id.okButton);
        thePhoto = (ImageView)findViewById(R.id.bookImage);
        minusButton = (Button)findViewById(R.id.minusButton);
        plusButton = (Button)findViewById(R.id.plusButton);

        //I would like the photo to be clickable and offer the user the ability to choose an image from their photo gallery
        //or take a picture. on the same screen it should show the image at a big size
        //to the user so they can see what it will look like.
        // Will get this in in time, functionality first. -Jill

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cannot be clickable right away, becomes clickable through the use of plusButton

                String tempQuantityString = quantityText.getText().toString();

                int tempQuantity;
                try {
                    tempQuantity = Integer.parseInt(tempQuantityString); //catch exception!!

                    if(tempQuantity < 1){
                        //the user enters a negative number into the textfield, correct them
                        quantityText.setText("1");
                    }

                    if (tempQuantity > 1) {

                        tempQuantity--;
                        quantityText.setText(String.valueOf(tempQuantity));

                        if (tempQuantity == 1) {
                            //make the button unclickable
                            minusButton.setClickable(false);
                        }
                    } //get here if tempQuantity <1, in which we do nothing
                }catch(IllegalArgumentException e){
                    //if string is an invalid string here, clear it and start at 1 again
                    quantityText.setText("1");
                }

            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on clicking this, makes the '-' button available
                String tempQuantityString = quantityText.getText().toString();
                int tempQuantity;

                try {
                    tempQuantity = Integer.parseInt(tempQuantityString); //catch exception!!

                    if(tempQuantity < 1){
                        //the user enters a negative number into the textfield, correct them
                        quantityText.setText("1");
                    }else {


                        tempQuantity++;
                        quantityText.setText(String.valueOf(tempQuantity));

                        if (tempQuantity > 1) {
                            //make possible to click the minus button
                            minusButton.setClickable(true);
                        }
                    }//end of else
                }catch(IllegalArgumentException e){
                    //if string is an invalid string here, clear it and start at 1 again
                    quantityText.setText("1");
                }

            }
        });



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myBook = new Book(thePhoto);

                //a ton of exception catching goes here

                try{
                    myBook.setTitle(titleText.getText().toString());
                }catch(IllegalArgumentException e){
                    //titleText.setText("NO TITLE"); //test to see what we can do
                    Toast.makeText(getApplicationContext(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                    //**should add somethig here maybe to emphasize what fields meant maaaaaybe, but would have to be all
                }

                try{
                    myBook.setAuthor(authorText.getText().toString());
                }catch(IllegalArgumentException e){
                    Toast.makeText(getApplicationContext(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();

                }


                try{
                    myBook.setQuantity(quantityText.getText().toString());
                }catch(IllegalArgumentException e){
                    //need to catch if they enter letters in the quantity
                    //also catch negative or 0 quantity
                    Toast.makeText(getApplicationContext(), "Illegal quantity type, please choose a number", Toast.LENGTH_SHORT).show();

                } catch(NegativeNumberException d){
                    Toast.makeText(getApplicationContext(), "Invalid quantity type. Quantity must be positive", Toast.LENGTH_SHORT).show();


                }


                //not going to move to new activity until this works right
                //Intent addBIntent = new Intent(HomeScreen.this, AddBookScreen.class);
                //startActivity(addBIntent);
            }
        });







    } //end of onCreate function

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book_screen, menu);
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
