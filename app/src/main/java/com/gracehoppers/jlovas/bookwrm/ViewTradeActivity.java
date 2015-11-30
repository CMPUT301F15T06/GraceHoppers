package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * Activity for viewing a trade's borrower and Owner  basic information (username),
 * and the books up for trading, along with the comments provided.
 * <p>
 * Contains textViews borrowerUsername, borrowerBook, ownerUsername, ownerBook and comments,
 * as well as a completeButton.
 *
 * @see Inventory, Book
 */

public class ViewTradeActivity extends ActionBarActivity {
    /*
   When a user clicks on a trade in TradeHistory, they can view its details,
   and mark it as complete.

   PLEASE NOTE: If you are writing an intent to go here, please set it with a flag

    */

    int pos;

    private SaveLoad saveload = new SaveLoad();
    Account account;

    Button counter;
    Button complete;

    private TextView borrowerUsername;
    private TextView borrowerBook;

    private TextView ownerUsername;
    private TextView ownerBook;

    private TextView comments;
    private TextView status;

    private Trade trade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trade);
        //Toast.makeText(getApplicationContext(), "Breakpoint", Toast.LENGTH_SHORT).show();

        counter = (Button) findViewById(R.id.counter);
        complete = (Button) findViewById(R.id.completeButton);

        borrowerUsername = (TextView) findViewById(R.id.borrowerUsername);
        borrowerBook = (TextView)  findViewById(R.id.borrowerBook);

        ownerUsername = (TextView) findViewById(R.id.ownerUsername);
        ownerBook = (TextView)  findViewById(R.id.ownerBook);

        comments = (TextView) findViewById(R.id.comments);

        status = (TextView)findViewById(R.id.status);

        account = saveload.loadFriendFromFile(getApplicationContext());
        //For testing purposes. DELETE AFTER
        //Create a test trade and add it to the list
        Account B = new Account();
        try{
            B.setUsername("B");
            B.setEmail("xyz@gmail.com");
            B.setCity("YEG");
        } catch (NoSpacesException e){

        } catch (TooLongException e){

        } catch (IllegalEmailException e ) {

        }


        Log.e("HistoryOfTrades size: ", String.valueOf(account.getTradeHistory().getSize()));


        //if (getIntent().getStringExtra("flag").equals("HistoryOfTrades")){

            pos = getIntent().getIntExtra("listPosition", 0);

            account = saveload.loadFromFile(ViewTradeActivity.this);
            Toast.makeText(getApplicationContext(), "Displaying item " + pos, Toast.LENGTH_SHORT).show();

            try {
                trade = account.getTradeHistory().getTradeByIndex(pos);
            } catch (NegativeNumberException e) {
                Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
            } catch (TooLongException e){
                Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
            }

            //If the trade is already completed, make the complete Button invisible
            if (trade.getCompletion().equals("COMPLETE")){
                complete.setVisibility(View.INVISIBLE);
            }

            //Display the trade information through the TextViews
            borrowerUsername.setText("Borrower:  "+ trade.getBorrower().getUsername());
            String bookTitles ="";

            for(Book b: trade.getBorrowerBook()){
                bookTitles= bookTitles + b.getTitle() +"\n";
            }

            borrowerBook.setText("Borrower Books:  " + bookTitles);

            ownerUsername.setText("Owner:  "+ trade.getOwner().getUsername());
            ownerBook.setText("Owner Book:  "+ trade.getOwnerBook().getTitle());

            comments.setText(trade.getOwnerComment());

            counter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewTradeActivity.this, CounterTradeScreen.class);
                    intent.putExtra("listPosition", pos);
                    startActivity(intent);
                }
            });

            if(trade.getAccepted() ){
                status.setText("Status: Accepted");
            }else if(trade.getDeclined()){
                status.setText("Status: Declined");
            }

        //} else {
        //    Toast.makeText(getApplicationContext(), "Else taken", Toast.LENGTH_SHORT).show();

       // }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_trade, menu);
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

    public void complete(){
        //mark the trade as complete
        trade.setCompletion(TradeCompletion.COMPLETE);
        //make the button invisible
        complete.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Trade marked as COMPLETE.", Toast.LENGTH_SHORT).show();
    }
}
