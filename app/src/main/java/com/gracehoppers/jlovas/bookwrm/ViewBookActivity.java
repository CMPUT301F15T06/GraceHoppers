package com.gracehoppers.jlovas.bookwrm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 *Activity for viewing a book, including all of its attributes.
 * <p>
 * Contains textViews bookTitle, bookAuthor, category, quantity, privacy
 * and description, as well as a RatingBar to represent the quality of the
 * Book. These fields have getters and setters.
 * Contains Edit and Delete button for that specific Book object.
 * @see Inventory, Book
 */
public class ViewBookActivity extends ActionBarActivity {



    /*
    When a user clicks on a book in their inventory, they can view its details, choose to edit it, or delete it.

    PLEASE NOTE: If you are writing an intent to go here, please set it with a flag

     */

    TextView bookTitle;
    TextView bookAuthor;
    TextView category;
    TextView quantity;
    TextView privacy;
    TextView description;
    RatingBar rating;
    Button deleteButton;
    Button editButton;
    ImageView bookImage;
    //put photo stuff here
    Button leftButton;
    Button rightButton;
    TextView imageTotalText;
    Account account, myFriend, fetchPhotos, cloner;
    Book tempBook, cloneBook;
    int pos;
    int posBook;
    int posFriend;
    private SaveLoad saveload = new SaveLoad();
    BitmapScaler scaler;
    TextView disabledPhotoTitleText;
    int count=0;

    UniqueNumberGenerator ung;
    UniqueNumber uninum;


    PhotoDownloads pD;

    // for UI testing --------------------------------------------------
    public Button getDeleteButton(){return deleteButton;}
    public Button getEditButton(){return editButton;}
    AlertDialog SingleInfo;
    public AlertDialog getAlertDialog(){return SingleInfo;}
    Button okButton;
    Button cancelButton;
    public TextView getBookTitle(){return bookTitle;}
    public TextView getBookAuthor(){return bookAuthor;}
    public TextView getCategory(){return category;}
    public TextView getQuantity(){return quantity;}
    public TextView getPrivacy(){return privacy;}
    public TextView getDescription(){return description;}
    //------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        //global
        pD = (PhotoDownloads)getApplicationContext();


        //set up UI parts-------------------------------------------------
        bookTitle= (TextView)findViewById(R.id.BookTitletextView);
        bookAuthor= (TextView)findViewById(R.id.authortextView);
        category= (TextView)findViewById(R.id.categorytypetextview);
        quantity= (TextView)findViewById(R.id.quantitynumtextview);
        privacy= (TextView)findViewById(R.id.privatebooktextview);
        description= (TextView)findViewById(R.id.commentsdesctextview);
        rating= (RatingBar)findViewById(R.id.qualityrating);
        rating.setNumStars(5);
        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable(); //Mirek Michalak, http://stackoverflow.com/questions/2446270/android-ratingbar-change-star-colors,2015-1-11
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP); //make filled stars yellow
        deleteButton= (Button)findViewById(R.id.deleteBookbutton);
        editButton= (Button)findViewById(R.id.EditBookButton);
        bookImage = (ImageView)findViewById(R.id.bookImage);
        leftButton = (Button)findViewById(R.id.pictureleftbutton);
        rightButton = (Button)findViewById(R.id.picturerightbutton);
        imageTotalText = (TextView)findViewById(R.id.imagetotaltextview);
        disabledPhotoTitleText = (TextView)findViewById(R.id.disabledPhotoTitleText);
        //----------------------------------------------------------------

        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //moves the photo left
                //notes: the images are appearing from most recently taken to least recently taken, hence the way I have getAtIndex'd

                String tmp = imageTotalText.getText().toString();

                switch(tmp.charAt(0)){
                    case '2':
                        count--;
                        imageTotalText.setText("" + 1 + "/" + tempBook.getPhotos().getPhotos().size() + "");
                        rightButton.setEnabled(true);
                        leftButton.setEnabled(false);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(tempBook.getPhotos().getPhotoAtIndex(count), 0, tempBook.getPhotos().getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            bookImage.setImageBitmap(scaled);

                            rightButton.setEnabled(true);
                            break;
                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }

                    case '3':
                        count--;
                        imageTotalText.setText("" + 2 +"/" +tempBook.getPhotos().getPhotos().size() +"");
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(tempBook.getPhotos().getPhotoAtIndex(count), 0, tempBook.getPhotos().getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            bookImage.setImageBitmap(scaled);

                            rightButton.setEnabled(true);
                            break;
                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }

                    case '4':
                        count--;
                        imageTotalText.setText("" + 3 +"/" +tempBook.getPhotos().getPhotos().size() +"");
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(tempBook.getPhotos().getPhotoAtIndex(count), 0, tempBook.getPhotos().getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            bookImage.setImageBitmap(scaled);

                            rightButton.setEnabled(true);
                            break;
                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }

                    case '5':
                        count--;
                        imageTotalText.setText("" + 4 +"/" +tempBook.getPhotos().getPhotos().size() +"");
                        rightButton.setEnabled(true);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(tempBook.getPhotos().getPhotoAtIndex(count), 0, tempBook.getPhotos().getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            bookImage.setImageBitmap(scaled);

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
                        imageTotalText.setText("" + 5 + "/" + tempBook.getPhotos().getPhotos().size() + "");
                        if(Character.getNumericValue(tmp.charAt(0)+1) == (char)tempBook.getPhotos().getPhotos().size()) rightButton.setEnabled(false);
                        rightButton.setEnabled(false);

                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(tempBook.getPhotos().getPhotoAtIndex(count), 0, tempBook.getPhotos().getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            bookImage.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case '3':
                        count++;
                        imageTotalText.setText("" + 4 +"/" +tempBook.getPhotos().getPhotos().size() +"");
                        if(Character.getNumericValue(tmp.charAt(0)+1) == (char)tempBook.getPhotos().getPhotos().size()) rightButton.setEnabled(false);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(tempBook.getPhotos().getPhotoAtIndex(count), 0, tempBook.getPhotos().getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            bookImage.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case '2':
                        count++;
                        imageTotalText.setText("" + 3 + "/" + tempBook.getPhotos().getPhotos().size() + "");
                        if(Character.getNumericValue(tmp.charAt(0)+1) == (char)tempBook.getPhotos().getPhotos().size()) rightButton.setEnabled(false);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(tempBook.getPhotos().getPhotoAtIndex(count), 0, tempBook.getPhotos().getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            bookImage.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case '1':
                        count++;
                        imageTotalText.setText("" + 2 + "/" + tempBook.getPhotos().getPhotos().size() + "");
                        if(Character.getNumericValue(tmp.charAt(0)+1) == (char)tempBook.getPhotos().getPhotos().size()) rightButton.setEnabled(false);
                        leftButton.setEnabled(true);
                        try {
                            Bitmap changed = BitmapFactory.decodeByteArray(tempBook.getPhotos().getPhotoAtIndex(count), 0, tempBook.getPhotos().getPhotoAtIndex(count).length);
                            Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                            bookImage.setImageBitmap(scaled);

                        }catch(NegativeNumberException e) {
                            Toast.makeText(getApplicationContext(), "Negative index number exception", Toast.LENGTH_SHORT).show();
                        }catch(TooLongException e){
                            Toast.makeText(getApplicationContext(), "Index number too long", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        });

        //credit to AndroidElasticSearch for an example of how to long-click
        bookImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (tempBook.getPhotos().getHasImages()) {
                    PhotoThread thread = new PhotoThread(myFriend.getUsername());

                    thread.start();

                    Toast.makeText(getApplicationContext(), "Fetching photos", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "No images for this image", Toast.LENGTH_SHORT).show();
                    return true;
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ask the user if they're sure they want to delete this book
                if(deleteButton.getText().toString().equals("Delete")) {
                    openDialog();
                }else{
                    CloneThread thread = new CloneThread();
                    thread.start();


                    Toast.makeText(getApplicationContext(), "Cloned book now added to user inventory", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("flag").equals("Homescreen")) {
            pos = getIntent().getIntExtra("listPosition", 0);

            disabledPhotoTitleText.setVisibility(View.INVISIBLE);
            bookImage.setLongClickable(false);

            account = saveload.loadFromFile(ViewBookActivity.this);

            try {
                tempBook = account.getInventory().getBookByIndex(pos);
            } catch (NegativeNumberException e) {
                Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
            } catch (TooLongException e) {
                Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
            }

            bookTitle.setText(tempBook.getTitle());
            bookAuthor.setText(tempBook.getAuthor());
            category.setText(tempBook.getCategory());
            quantity.setText(tempBook.getQuantity() + "");
            description.setText(tempBook.getDescription());
            rating.setRating((float) tempBook.getQuality());

            if (tempBook.isPrivate()) {
                privacy.setText("Private Book");
            } else privacy.setText("Public Book");

            //put photo stuff here
            //attempting to look at photos
            if(tempBook.getPhotos().getHasImages()) {
                try {
                    Bitmap changed = BitmapFactory.decodeStream(new ByteArrayInputStream(tempBook.getPhotos().getPhotoAtIndex(0)));
                    Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                    bookImage.setImageBitmap(scaled);
                    if(tempBook.getPhotos().getPhotos().size() >1)
                        rightButton.setEnabled(true);
                    imageTotalText.setText("" + 1 + "/" + tempBook.getPhotos().getPhotos().size() + "");
                    disabledPhotoTitleText.setVisibility(View.INVISIBLE);
                    //come back to this later if you can and adjust how this image pops up
                } catch (NegativeNumberException e) {
                    Toast.makeText(getApplicationContext(), "Negative index", Toast.LENGTH_SHORT).show();
                } catch (TooLongException e) {
                    Toast.makeText(getApplicationContext(), "Index is too long", Toast.LENGTH_SHORT).show();
                }
            }





            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewBookActivity.this, EditBookActivity.class);


                    //pass the book info by intent
                    intent.putExtra("bookTitle", tempBook.getTitle());
                    intent.putExtra("bookAuthor", tempBook.getAuthor());
                    intent.putExtra("bookQuantity", tempBook.getQuantity());
                    intent.putExtra("bookQuality", tempBook.getQuality());
                    intent.putExtra("bookCategory", tempBook.getCategoryNumber());
                    intent.putExtra("bookPrivacy", tempBook.isPrivate());
                    intent.putExtra("bookDesc", tempBook.getDescription());
                    intent.putExtra("bookPosition", pos);
                    //put photo stuff here...if it cant be passed by intent, pass the inventory index position and use gson instead of using the above!
                    startActivity(intent);


                }
            });

        }  else if (getIntent().getStringExtra("flag").equals("Search")) {
        //********************************************************************************************************
            //the item is being viewed from a searched query, offer the option to trade here
            posBook = getIntent().getIntExtra("listPosition", 0);

            account = saveload.loadFromFile(ViewBookActivity.this);
            myFriend = saveload.loadFriendFromFile(getApplicationContext()); //loads the stored friend

            try {
                //find the book by a different position than the friend's position
                tempBook = myFriend.getInventory().getBookByIndex(posBook); //used to be friendBook, but causes problems with navigatin pictures when thee is a tempBook and a friendBook
            } catch (NegativeNumberException e) {
                Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
            } catch (TooLongException e) {
                Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
            }

            bookTitle.setText(tempBook.getTitle());
            bookAuthor.setText(tempBook.getAuthor());
            category.setText(tempBook.getCategory());
            quantity.setText(tempBook.getQuantity() + "");
            description.setText(tempBook.getDescription());
            rating.setRating((float) tempBook.getQuality());

            //put photo stuff here
            //attempting to look at photos
            if(tempBook.getPhotos().getHasImages()) {
                try {
                    Bitmap changed = BitmapFactory.decodeStream(new ByteArrayInputStream(tempBook.getPhotos().getPhotoAtIndex(0)));
                    Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                    bookImage.setImageBitmap(scaled);
                    if(tempBook.getPhotos().getPhotos().size() >1)
                        rightButton.setEnabled(true);
                    imageTotalText.setText("" + 1 + "/" + tempBook.getPhotos().getPhotos().size() + "");
                    //come back to this later if you can and adjust how this image pops up
                } catch (NegativeNumberException e) {
                    Toast.makeText(getApplicationContext(), "Negative index", Toast.LENGTH_SHORT).show();
                } catch (TooLongException e) {
                    Toast.makeText(getApplicationContext(), "Index is too long", Toast.LENGTH_SHORT).show();
                }
            }

            if (tempBook.isPrivate()) {
                privacy.setText("Private Book");
            } else privacy.setText("Public Book");

        //repurpose the edit and delete button into the clone and trade buttons
            deleteButton.setText("Clone");
            editButton.setText("Trade");







        }

        else { //***********************************************************************************
            //the item is a friend's - do not want to offer edit and delete

            posBook = getIntent().getIntExtra("listPosition", 0);

            if(pD.getEnabled()){
                bookImage.setLongClickable(false);
            }

            account = saveload.loadFromFile(ViewBookActivity.this);
            myFriend = saveload.loadFriendFromFile(getApplicationContext()); //loads the stored friend
/*
            try {
                //find the friend by a certain position
                myFriend = account.getFriends().getFriendByIndex(posFriend); //***BUG if you pick the second item in the list!
            } catch (NegativeNumberException e) {
                Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
            } catch (TooLongException e) {
                Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
            }*/

            try {
                //find the book by a different position than the friend's position
                tempBook = myFriend.getInventory().getBookByIndex(posBook); //used to be friendBook, but causes problems with navigatin pictures when thee is a tempBook and a friendBook
            } catch (NegativeNumberException e) {
                Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
            } catch (TooLongException e) {
                Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
            }

            bookTitle.setText(tempBook.getTitle());
            bookAuthor.setText(tempBook.getAuthor());
            category.setText(tempBook.getCategory());
            quantity.setText(tempBook.getQuantity() + "");
            description.setText(tempBook.getDescription());
            rating.setRating((float) tempBook.getQuality());


            //photo part - check with global on whether or not to show it and adjust as appropriate
            if(pD.getEnabled()){ //make sure you don't show disabled
                if (tempBook.getPhotos().getHasImages()) {
                    try {
                        bookImage.setEnabled(false);
                        Bitmap changed = BitmapFactory.decodeStream(new ByteArrayInputStream(tempBook.getPhotos().getPhotoAtIndex(0)));
                        Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                        bookImage.setImageBitmap(scaled);
                        if (tempBook.getPhotos().getPhotos().size() > 1)
                            rightButton.setEnabled(true);
                        imageTotalText.setText("" + 1 + "/" + tempBook.getPhotos().getPhotos().size() + "");

                        //come back to this later if you can and adjust how this image pops up
                    } catch (NegativeNumberException e) {
                        Toast.makeText(getApplicationContext(), "Negative index", Toast.LENGTH_SHORT).show();
                    } catch (TooLongException e) {
                        Toast.makeText(getApplicationContext(), "Index is too long", Toast.LENGTH_SHORT).show();
                    }
                }disabledPhotoTitleText.setVisibility(View.INVISIBLE);

            }//end of global variable
            else{
                Toast.makeText(getApplicationContext(), "Disabled photos", Toast.LENGTH_SHORT).show();
                bookImage.setEnabled(true);
            }


            if (tempBook.isPrivate()) {
                privacy.setText("Private Book");
            } else privacy.setText("Public Book");

            //repurpose delete button
            //ViewGroup parentView = (ViewGroup) deleteButton.getParent();
            //parentView.removeView(deleteButton);

            deleteButton.setText("Clone");

            ViewGroup parentView2 = (ViewGroup) editButton.getParent();
            parentView2.removeView(editButton);
        }
    }





    public void openDialog() {
        //asks the user if they're sure they want to delete the book.
        //http://stackoverflow.com/questions/31151306/how-to-show-a-dialog-with-android-studio heloisasim 2015-31-10
        AlertDialog.Builder singleInfo=new AlertDialog.Builder(this);
        singleInfo.setTitle("Delete Book");
        singleInfo.setMessage("Are you sure you want to delete this book?");
        singleInfo.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               //user wants to delete the book
                deleteBook();
                dialog.dismiss();
                finish();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                     // user does not want to delete the book, do nothing
                       dialog.dismiss();
                    }

                });
        //AlertDialog SingleInfo=singleInfo.create();
        SingleInfo=singleInfo.create();
        SingleInfo.show();
    }

    public void deleteBook(){
//deletes the book and saves the change with gson
       // Log.e("Got to method", "Made it to deleteBook");
        account.getInventory().deleteBook(tempBook);
        assertFalse(account.getInventory().hasBook(tempBook));
        //Log.e("Got to method", "assertion succeeded");
        saveload.saveInFile(getApplicationContext(), account);

        Thread yourthread = new UpdateAThread(account); //update the server to delete this book
        yourthread.start();

        Toast.makeText(getApplicationContext(),"Book deleted",Toast.LENGTH_SHORT).show();

    }

    /**
     * This thread will update the account on the server
     *
     * @see Thread
     * @author nlovas
     */
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

    /**
     * This thread will copy the book to the server and update your inventory to include it.
     *
     * @see Thread
     * @author jlovas
     */
    class CloneThread extends Thread {


        public CloneThread() {

        }

        @Override
        public void run() {

            cloneBook = tempBook;

            //copy over everything except the unique number
            ViewBookActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ung = new UniqueNumberGenerator();
                    uninum = ung.getUniqueNumber(); //!!!!!   this requires a connection. ung has a thread within its class!
                    cloneBook.setUniquenum(uninum);
                    account.getInventory().addBook(cloneBook);
                    saveload.saveInFile(getApplicationContext(), account);
                    UpdateAThread thread = new UpdateAThread(account);
                    thread.start();
                }
            });
        }
    }

        /**
        * * This thread will fetch the photos requested by the user to display.
        *
        * @see Thread
        * @author jlovas
        */
            class PhotoThread extends Thread {
            private String a;

                public PhotoThread(String a){

                    fetchPhotos = new Account();
                    this.a = a;
                }

                @Override
                public void run(){

                AccountManager accountManager = new AccountManager();
                fetchPhotos = accountManager.getAccount(a);
                int u;

                if(fetchPhotos !=null) {
                    try {
                        u = tempBook.getUniquenum().getNumber();

                        for(int i=0; i < fetchPhotos.getInventory().getSize(); i++){
                            if(u == fetchPhotos.getInventory().getBookByIndex(i).getUniquenum().getNumber()){

                            //congrats! youve found the right book
                            //now need to set the photos to the imageview, update stuff
                            ViewBookActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        bookImage.setEnabled(false);
                                        Bitmap changed = BitmapFactory.decodeStream(new ByteArrayInputStream(tempBook.getPhotos().getPhotoAtIndex(0)));
                                        Bitmap scaled = scaler.scaleToFitWidth(changed, 500);
                                        bookImage.setImageBitmap(scaled);
                                        if (tempBook.getPhotos().getPhotos().size() > 1)
                                            rightButton.setEnabled(true);
                                        imageTotalText.setText("" + 1 + "/" + tempBook.getPhotos().getPhotos().size() + "");
                                        disabledPhotoTitleText.setVisibility(View.INVISIBLE);
                                        //come back to this later if you can and adjust how this image pops up
                                    } catch (NegativeNumberException e) {
                                        Toast.makeText(getApplicationContext(), "Negative index", Toast.LENGTH_SHORT).show();
                                    } catch (TooLongException e) {
                                        Toast.makeText(getApplicationContext(), "Index is too long", Toast.LENGTH_SHORT).show();
                                    }


                                    Toast.makeText(getApplicationContext(), "Photos added", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }catch (NegativeNumberException e){

                }catch (TooLongException e){

                }
                    //only do this if nothing found - shouldnt happen

            }//if null:

        }


    }

}
