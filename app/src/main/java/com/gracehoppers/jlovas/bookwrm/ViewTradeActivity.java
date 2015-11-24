package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 *Activity for viewing a trade's borrower and Owner  basic information (username),
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
    int posTrade;

    private SaveLoad saveload = new SaveLoad();
    Account account;

    Button completeButton;

    private TextView borrowerUsername;
    private TextView borrowerBook;

    private TextView ownerUsername;
    private TextView ownerBook;

    private TextView comments;

    private Trade trade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trade);

        completeButton = (Button) findViewById(R.id.completeButton);

        borrowerUsername = (TextView) findViewById(R.id.borrowerUsername);
        borrowerBook = (TextView)  findViewById(R.id.borrowerBook);

        ownerUsername = (TextView) findViewById(R.id.ownerUsername);
        ownerBook = (TextView)  findViewById(R.id.ownerBook);

        if (getIntent().getStringExtra("flag").equals("HistoryOfTrades")){
            pos = getIntent().getIntExtra("intPosition", 0);

            account = saveload.loadFromFile(ViewTradeActivity.this);

            try {
                trade = account.getTradeHistory().getTradeByIndex(pos);
            } catch (NegativeNumberException e) {
                Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
            } catch (TooLongException e){
                Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
            }

            //Display the trade information through the TextViews
            borrowerUsername.setText(trade.getBorrower().getUsername());
            borrowerBook.setText(trade.getBorrowerBook().toString());

            ownerUsername.setText(trade.getOwner().getUsername());
            ownerBook.setText(trade.getOwnerBook().getTitle());

            comments.setText(trade.getOwnerComment());

            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trade.setCompletion(TradeCompletion.COMPLETE);
                    Toast.makeText(getApplicationContext(), "Trade marked as COMPLETE.", Toast.LENGTH_SHORT).show();
                }
            });
        }


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
}
