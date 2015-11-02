package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditBookActivity extends ActionBarActivity {

    /*
    The screen which allows the user to edit a book
     */

    EditText bookTitle;
    EditText bookAuthor;
    EditText bookQuantity;
    RatingBar bookRating;
    Spinner bookCategory;
    Button minusButton;
    Button plusButton;
    ImageView bookPhoto;
    CheckBox privateCheckBox;
    TextView bookDescText;
    Button okButton;
    int spinValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        //set the UI parts---------------------------------
        bookDescText= (TextView)findViewById(R.id.descriptionText);
        bookTitle= (EditText)findViewById(R.id.titleText);
        bookAuthor= (EditText)findViewById(R.id.authorText);
        bookQuantity= (EditText)findViewById(R.id.quantityText);
        bookRating= (RatingBar)findViewById(R.id.ratingBar);
        bookRating.setNumStars(5);
        bookCategory= (Spinner)findViewById(R.id.categoryDropdown);
        minusButton= (Button)findViewById(R.id.minusButton);
        plusButton= (Button)findViewById(R.id.plusButton);
        bookPhoto= (ImageView)findViewById(R.id.bookImage);
        privateCheckBox= (CheckBox)findViewById(R.id.privateListingCheckbox);
        okButton= (Button)findViewById(R.id.EditBookButton);
    //--------------------------------------------------------------

        //obtain book info from the intent (if photos cant be passed by intent, replace all of these with gson)
        String title = getIntent().getStringExtra("bookTitle");
        String author = getIntent().getStringExtra("bookAuthor");
        int quantity = getIntent().getIntExtra("bookQuantity", 1);
        double quality = getIntent().getDoubleExtra("bookQuality", 0);
        int category = getIntent().getIntExtra("bookCategory", 0);
        boolean isprivate = getIntent().getBooleanExtra("bookPrivacy", false);
        String description = getIntent().getStringExtra("bookDesc");
        //photo stuff here
        //----------------------------------------------------------

        //set the info in the editable sections to the book's:
        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookQuantity.setText(quantity + "");
        //Toast.makeText(getApplicationContext(), quality + "", Toast.LENGTH_SHORT).show();
        bookRating.setIsIndicator(true);
        bookRating.setRating((float) quality);
        bookRating.setIsIndicator(false);



        //makes quantity go down
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cannot be clickable right away, becomes clickable through the use of plusButton

                String tempQuantityString = bookQuantity.getText().toString();

                int tempQuantity;
                try {
                    tempQuantity = Integer.parseInt(tempQuantityString); //catch exception!!

                    if (tempQuantity < 1) {
                        //the user enters a negative number into the textfield, correct them
                        bookQuantity.setText("1");
                    }

                    if (tempQuantity > 1) {
                        tempQuantity--;
                        bookQuantity.setText(String.valueOf(tempQuantity));

                        if (tempQuantity == 1) {
                            //make the button unclickable
                            minusButton.setClickable(false);
                        }
                    } //get here if tempQuantity <1, in which we do nothing
                } catch (IllegalArgumentException e) {
                    //if string is an invalid string here, clear it and start at 1 again
                    bookQuantity.setText("1");
                }

            }
        });

        //makes quantity go up
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on clicking this, makes the '-' button available
                String tempQuantityString = bookQuantity.getText().toString();
                int tempQuantity;

                try {
                    tempQuantity = Integer.parseInt(tempQuantityString); //catch exception!!

                    if (tempQuantity < 1) {
                        //the user enters a negative number into the textfield, correct them
                        bookQuantity.setText("1");
                    } else {


                        tempQuantity++;
                        bookQuantity.setText(String.valueOf(tempQuantity));

                        if (tempQuantity > 1) {
                            //make possible to click the minus button
                            minusButton.setClickable(true);
                        }
                    }//end of else
                } catch (IllegalArgumentException e) {
                    //if string is an invalid string here, clear it and start at 1 again
                    bookQuantity.setText("1");
                }

            }
        });


        bookCategory.setSelection(category);

        AdapterView.OnItemSelectedListener onSpinner = new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                //assign to 'global' for sending to Book on ok button press
                spinValue = position;
            }

            @Override
            public void onNothingSelected(
                    AdapterView<?>  parent) {
            }
        };

        bookCategory.setOnItemSelectedListener(onSpinner);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_book, menu);
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
