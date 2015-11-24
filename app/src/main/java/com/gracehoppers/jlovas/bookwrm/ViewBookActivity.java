package com.gracehoppers.jlovas.bookwrm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
    //put photo stuff here
    Account account, myFriend;
    Book tempBook, friendBook;
    int pos;
    int posBook;
    int posFriend;
    private SaveLoad saveload = new SaveLoad();

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
        //----------------------------------------------------------------





    }

    @Override
    public void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("flag").equals("Homescreen")) {
            pos = getIntent().getIntExtra("listPosition", 0);


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

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ask the user if they're sure they want to delete this book
                    openDialog();
                }
            });


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
        } else { //***********************************************************************************
            //the item is a friend's - do not want to offer edit and delete
/*
            posBook = getIntent().getIntExtra("listPosition", 0);
            posFriend = getIntent().getIntExtra("position2",0);

            account = saveload.loadFromFile(ViewBookActivity.this);

            try {
                //find the friend by a certain position
                myFriend = account.getFriends().getFriendByIndex(posFriend); //***BUG if you pick the second item in the list!
            } catch (NegativeNumberException e) {
                Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
            } catch (TooLongException e) {
                Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
            }

            try {
                //find the book by a different position than the friend's position
                friendBook = myFriend.getInventory().getBookByIndex(posBook);
            } catch (NegativeNumberException e) {
                Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
            } catch (TooLongException e) {
                Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
            }

            bookTitle.setText(friendBook.getTitle());
            bookAuthor.setText(friendBook.getAuthor());
            category.setText(friendBook.getCategory());
            quantity.setText(friendBook.getQuantity() + "");
            description.setText(friendBook.getDescription());
            rating.setRating((float) friendBook.getQuality());

            if (friendBook.isPrivate()) {
                privacy.setText("Private Book");
            } else privacy.setText("Public Book");
            //put photo stuff here



            //remove the delete button, edit button changes to trade button
            ViewGroup parentView = (ViewGroup) deleteButton.getParent();
            parentView.removeView(deleteButton);

            ViewGroup parentView2 = (ViewGroup) editButton.getParent();
            parentView.removeView(editButton);
*/ //FIX THE ABOVE!!!!!
            /*
            Button tradeButton = editButton;

            tradeButton.setText("Trade");

            tradeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewBookActivity.this, CreateTradeScreen.class);

                    /*
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
    */
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_book, menu);
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

        Toast.makeText(getApplicationContext(),"Book deleted",Toast.LENGTH_SHORT).show();

    }

}
