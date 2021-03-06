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

/**
 * EditBookActivity allows for the user to modify the information in one of their own books they
 * had made.
 *
 * @Author Nicole Lovas
 *
 * @See AddBookScreen, Book, ViewBookActivity
 */
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
    Photos editPhotos;
    TextView plusTitleText;

    // for UI testing-------------------------------------------
    public EditText getBookTitle(){return bookTitle;}
    public EditText getBookAuthor(){return bookAuthor;}
    public EditText getBookQuantity(){return bookQuantity;}
    public RatingBar getRatingBar(){return bookRating;}
    public Spinner getSpinner(){return bookCategory;}
    public CheckBox getCheckBox(){return privateCheckBox;}
    public TextView getComments(){return bookDescText;}
    public Button getMinusButton(){return minusButton;}
    public Button getPlusButton(){return plusButton;}
    public Button getOkButton(){return okButton;}

   //------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        //load the account
        saveload = new SaveLoad();
        tempAccount = saveload.loadFromFile(EditBookActivity.this);
        editPhotos = new Photos();

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
        plusTitleText = (TextView)findViewById(R.id.plusTitleText);
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

        bookRating.setIsIndicator(true);
        bookRating.setRating((float) quality);
        bookRating.setIsIndicator(false);

        //update symbol based on images
        try {
            if (tempAccount.getInventory().getBookByIndex(pos).getPhotos().getPhotos().size() > 0) {
                plusTitleText.setText("✔");
            }
        }catch(NegativeNumberException e){

        }catch (TooLongException e){

        }

        bookPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch activity on result much like addBookScreen ahh!
                //except you have to load the photos into it AHHH!
                Intent pIntent = new Intent(EditBookActivity.this, PhotoActivity.class);
                pIntent.putExtra("flag", "edit");
                try {
                    saveload.savePhotos(getApplicationContext(), tempAccount.getInventory().getBookByIndex(pos).getPhotos());
                    startActivityForResult(pIntent, 64);
                } catch (NegativeNumberException e) {

                } catch (TooLongException e) {

                }
            }
        });


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

        bookPhoto.buildDrawingCache();
        Bitmap bMap = bookPhoto.getDrawingCache();
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

                    if(editPhotos.getHasImages()) {
                        tempAccount.getInventory().getBookByIndex(pos).getPhotos().setPhotoset(editPhotos);
                        tempAccount.getInventory().getBookByIndex(pos).getPhotos().setHasImages(true);
                    }

                    if(privateCheckBox.isChecked()){

                        tempAccount.getInventory().getBookByIndex(pos).setIsPrivate(true);
                    }else {
                        tempAccount.getInventory().getBookByIndex(pos).setIsPrivate(false);
                    }
                    saveload.saveInFile(getApplicationContext(),tempAccount);

                    Thread yourthread = new UpdateAThread(tempAccount); //update the server to have the edited book
                    yourthread.start();


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
                    //this may possibly trip if there is a negative inventory index number somehow
                    Toast.makeText(getApplicationContext(), "Invalid quantity type. Quantity must be positive", Toast.LENGTH_SHORT).show();
                }catch(TooLongException e){
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
                    }




            }
        });

    }

    /**
     * This method will run when a user has clicked to edit comments (see method above) and returns
     * the results to the parent function on result. Also used when editing pictures
     *
     * @param requestCode the code to tell which is returning
     * @param resultCode checks that the result is ok
     * @param data the data returning from the child activity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //grabs the information on the description if the user edits the description
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if (resultCode == AddCommentsScreen.RESULT_OK) {
                description = data.getStringExtra("COMMENTS");
                //Toast.makeText(getApplicationContext(), "Got " + description +" from child.", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode ==64){
            //returning from photoactivity

                editPhotos = saveload.loadPhotos(getApplicationContext());
                if(!editPhotos.getHasImages()){
                    plusTitleText.setText("+");
                }
                Toast.makeText(getApplicationContext(), "Returning from editting photos!", Toast.LENGTH_SHORT).show();

        }else{
            //the user just hit back, so clear?
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

    class UpdateAThread extends Thread { //for updating account on the server
        private Account account;

        public UpdateAThread(Account account) {
            this.account = account;
        }

        @Override
        public void run() {

            AccountManager accountManager = new AccountManager();
            accountManager.updateAccount(account);

        }

    }
}
