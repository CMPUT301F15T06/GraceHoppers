package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
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
    String title;
    String author;
    int quantity;
    double quality;
    boolean isprivate;
    String description;
    int categorynum;
    String category;
    Book tempBook;
    SaveLoad saveload;
    Account tempAccount;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        //load the account
        saveload = new SaveLoad();
        tempAccount = saveload.loadFromFile(EditBookActivity.this);


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
        title = getIntent().getStringExtra("bookTitle");
        author = getIntent().getStringExtra("bookAuthor");
        quantity = getIntent().getIntExtra("bookQuantity", 1);
        quality = getIntent().getDoubleExtra("bookQuality", 0);
        categorynum = getIntent().getIntExtra("bookCategory", 0);
        isprivate = getIntent().getBooleanExtra("bookPrivacy", false);
        description = getIntent().getStringExtra("bookDesc");
        pos = getIntent().getIntExtra("bookPosition", 0);
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


        bookCategory.setSelection(categorynum);

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

        bookDescText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send the user to the comments activity if they want to edit the description
                Intent intent = new Intent(EditBookActivity.this, AddCommentsScreen.class);
                intent.putExtra("bookDescription", description);
                intent.putExtra("flag", "edit");
                startActivityForResult(intent, 0);
            }
        });

        if(isprivate){
            privateCheckBox.setChecked(true);
        }

        //add code here for showing the book's current image


    //now, to save all of the user's edits (and check to make sure they are still valid)-----------------------------


        tempBook = new Book(); //use the book class' exceptions to ensure changes are valid


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send changes back to previous activity

                try {
                   // title = bookTitle.getText().toString();
                   // author = bookTitle.getText().toString();
                   // quality = bookRating.getRating();

                  //  if(privateCheckBox.isChecked()){

                    //    tempBook.setIsPrivate(true);
                    //}else {
                    //    tempBook.setIsPrivate(false);
                   // }

                    //do i even need to set the default values???? theyre only needed for setting the defaults
                    //tempBook.setTitle(bookTitle.getText().toString());
                    //tempBook.setAuthor(bookAuthor.getText().toString());
                    //tempBook.setQuantity(bookQuantity.getText().toString());
                   // quantity = tempBook.getQuantity(); // returns an int (setter only accepts a string)
                    //tempBook.setQuality(bookRating.getRating());
                   // tempBook.setCategory(spinValue);
                   // category = tempBook.getCategory(); //category in string form
                    //tempBook.setDescription(description);
                   // description = tempBook.getDescription(); //in case its blank, it will set description to "none"

                    tempAccount.getInventory().getBookByIndex(pos).setTitle(bookTitle.getText().toString());
                    tempAccount.getInventory().getBookByIndex(pos).setAuthor(bookAuthor.getText().toString());
                    tempAccount.getInventory().getBookByIndex(pos).setQuantity(bookQuantity.getText().toString());
                    tempAccount.getInventory().getBookByIndex(pos).setQuality(bookRating.getRating());
                    tempAccount.getInventory().getBookByIndex(pos).setCategory(spinValue);
                    tempAccount.getInventory().getBookByIndex(pos).setDescription(description);
                    if(privateCheckBox.isChecked()){

                        tempAccount.getInventory().getBookByIndex(pos).setIsPrivate(true);
                    }else {
                        tempAccount.getInventory().getBookByIndex(pos).setIsPrivate(false);
                    }
                    saveload.saveInFile(getApplicationContext(),tempAccount);

                    Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                    //credit to Reto Meler for the result method of doing this:
                    //Reto Meler, http://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android, Oct 28, 2015
                    //http://stackoverflow.com/users/822/reto-meier
                    Intent result = new Intent();
                    //result.putExtra("", comments.getText().toString());
                    setResult(AddCommentsScreen.RESULT_OK, result);
                    finish();

                } catch(IllegalArgumentException e){
                    Toast.makeText(getApplicationContext(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                } catch (NegativeNumberException x){
                    Toast.makeText(getApplicationContext(), "Invalid quantity type. Quantity must be positive", Toast.LENGTH_SHORT).show();
                }




            }
        });





    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //grabs the information on the description if the user edits the description
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0)
            if (resultCode == AddCommentsScreen.RESULT_OK) {
                description = data.getStringExtra("COMMENTS");
                //Toast.makeText(getApplicationContext(), "Got " + description +" from child.", Toast.LENGTH_SHORT).show();
            }
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
