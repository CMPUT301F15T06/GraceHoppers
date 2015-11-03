package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.ArrayList;

public class AddBookScreen extends ActionBarActivity {
    SaveLoad mySaveLoad;
    Account me;
    Book myBook;
    int spinValue; //might be a better way to do this, but eh
    String madeComments = "None";

    EditText titleText;
    EditText authorText;
    EditText quantityText;
    Spinner mySpinner;
    Button okButton;
    ImageView thePhoto;
    Button minusButton;
    Button plusButton;
    RatingBar stars;
    TextView comments;
    CheckBox privateCheckBox;

    //For UI testing -----------------------------------------
    private ArrayList<Book> books = new ArrayList<Book>();
    public ArrayList<Book> getBooks(){return books;}

    public String getMadeComments() {return madeComments;}

    public EditText getTitleText() {return titleText;}

    public EditText getAuthorText() {return authorText;}

    public EditText getQuantityText() {return quantityText;}

    public Spinner getMySpinner() {return mySpinner;}

    public Button getOkButton() {return okButton;}

    public ImageView getThePhoto() {return thePhoto;}

    public Button getMinusButton() {return minusButton;}

    public Button getPlusButton() {return plusButton;}

    public RatingBar getStars() {return stars;}

    public TextView getComments() {return comments;}

    public CheckBox getPrivateCheckBox() {return privateCheckBox;}
    //-------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_screen);

        mySaveLoad = new SaveLoad();
        me = mySaveLoad.loadFromFile(getApplicationContext());

        titleText = (EditText)findViewById(R.id.titleText);
        authorText = (EditText)findViewById(R.id.authorText);
        quantityText = (EditText)findViewById(R.id.quantityText);
        okButton = (Button)findViewById(R.id.EditBookButton);
        thePhoto = (ImageView)findViewById(R.id.bookImage);
        minusButton = (Button)findViewById(R.id.minusButton);
        plusButton = (Button)findViewById(R.id.plusButton);
        stars = (RatingBar)findViewById(R.id.ratingBar);
        mySpinner = (Spinner)findViewById(R.id.categoryDropdown);
        comments = (TextView)findViewById(R.id.descriptionText);
        privateCheckBox = (CheckBox)findViewById(R.id.privateListingCheckbox);
        stars.setNumStars(5);



        //spinner/dropdown clicklistener -automatically set to 0/none on creation
        //credit to Mike James for this code/note to use this way of doing this:
        //Mike James, http://www.i-programmer.info/programming/android/6388-android-adventures-spinners-and-pickers.html?start=1, Oct 28, 2015
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

        mySpinner.setOnItemSelectedListener(onSpinner);


        //I would like the photo to be clickable and offer the user the ability to choose an image from their photo gallery
        //or take a picture. on the same screen it should show the image at a big size
        //to the user so they can see what it will look like.
        // Will get this in in time, functionality first. -Jill

        //makes quantity go down
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cannot be clickable right away, becomes clickable through the use of plusButton

                String tempQuantityString = quantityText.getText().toString();

                int tempQuantity;
                try {
                    tempQuantity = Integer.parseInt(tempQuantityString); //catch exception!!

                    if (tempQuantity < 1) {
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
                } catch (IllegalArgumentException e) {
                    //if string is an invalid string here, clear it and start at 1 again
                    quantityText.setText("1");
                }

            }
        });

        //makes quantity go up
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


        //button for creating the book
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //messing around, will tidy up after
                thePhoto.buildDrawingCache();
                Bitmap bMap = thePhoto.getDrawingCache();
                myBook = new Book(bMap);

                //a ton of exception catching goes here
                try{
                    myBook.setTitle(titleText.getText().toString());
                    myBook.setAuthor(authorText.getText().toString());
                    myBook.setQuantity(quantityText.getText().toString());

                    //OPTIONAL THINGS
                    //rating doesn't seem to be optional in the UI but it is... will come back to fix that later if I can
                    myBook.setQuality(stars.getRating());
                    myBook.setCategory(spinValue);
                    myBook.setDescription(madeComments);

                    //set private/public
                    if(privateCheckBox.isChecked()){
                        //set as private in Book
                        myBook.setIsPrivate(true);
                    }else{
                        myBook.setIsPrivate(false);
                    }

                    me.getInventory().addBook(myBook);
                    books.add(myBook); //For UI testing

                    //save into Gson and end the activity
                    mySaveLoad.saveInFile(getApplicationContext(), me);
                    Toast.makeText(getApplicationContext(), "Successfully added book to inventory", Toast.LENGTH_SHORT).show();
                    finish();
                }catch(IllegalArgumentException e){
                    //titleText.setText("NO TITLE"); //test to see what we can do
                    Toast.makeText(getApplicationContext(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                    //**should add something here maybe to emphasize what fields meant maaaaaybe, but would have to be all
                }catch(NegativeNumberException d) {
                    Toast.makeText(getApplicationContext(), "Invalid quantity type. Quantity must be positive", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //text button for adding comments - made this as text because a button would take up space/be clunky
        comments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(), "You wanted to add a comment!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddBookScreen.this, AddCommentsScreen.class);
                intent.putExtra("flag","add");
                startActivityForResult(intent, 0);
            }
        });
    } //end of onCreate function

    //catch comments made in another activity - need this for comments, passes the result to madeComments
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 0)
                if (resultCode == AddCommentsScreen.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity. -DONE! :D
                    madeComments = data.getStringExtra("COMMENTS");
                    //Toast.makeText(getApplicationContext(), "Got " + madeComments +" from child.", Toast.LENGTH_SHORT).show();
                }
    }




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
