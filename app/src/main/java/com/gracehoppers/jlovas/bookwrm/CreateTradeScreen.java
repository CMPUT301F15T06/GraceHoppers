package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * This activity begins a trade with the friend whose page you are currently on.
 * It allows for the user to pick many of their own items and choose one of the
 * friend's items to trade with.
 *
 * @author Hong Chen, Hong Wang
 *
 * @see Trade, TradeHistory
 */

public class CreateTradeScreen extends Activity {

    private ListView borrowerInventoryListView;
    private ListView ownerBookListView;
    private Account me;
    private Account friend;
    private ArrayAdapter<Book> adapter;
    private ArrayAdapter<Book> adapterO;
    private ArrayList<Book> selectedBorrowerBooks;
    private ArrayList<Book> selectedOwnerBook = new ArrayList<Book>();
    private static Trade newTrade = new Trade();
    private SaveLoad mySaveLoad;
    Button borrowerAdd;
    Button ownerSelect;
    Button submitTrade;
    Button cancelTrade;
    private int pos;
    private int pos2;

    @Override
    protected void onStart(){
        super.onStart();

        mySaveLoad = new SaveLoad();
        me = mySaveLoad.loadFromFile(getApplicationContext());
        friend = mySaveLoad.loadFriendFromFile(getApplicationContext());

        Toast.makeText(getApplicationContext(), "My inventory has " + me.getInventory().getSize() + " items in it!", Toast.LENGTH_SHORT).show();

        pos = getIntent().getIntExtra("aPosition", (int) Double.POSITIVE_INFINITY);
        pos2 = getIntent().getIntExtra("bPosition", (int) Double.POSITIVE_INFINITY);

        try {
            newTrade.getBorrowerBook().add(friend.getInventory().getBookByIndex(pos));
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        } catch (TooLongException e) {
            e.printStackTrace();
        }

        try {
            newTrade.setOwnerBook(me.getInventory().getBookByIndex(pos2));
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        } catch (TooLongException e) {
            e.printStackTrace();
        }


        selectedBorrowerBooks = newTrade.getBorrowerBook();
        borrowerInventoryListView = (ListView)findViewById(R.id.borrowerInventory);
        adapter = new BookListAdapter(this,R.layout.friend_list, selectedBorrowerBooks);
        borrowerInventoryListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (selectedOwnerBook.isEmpty()){
            if (newTrade.getOwnerBook().getTitle() != null) {
                selectedOwnerBook.add((newTrade.getOwnerBook()));
            }
        }else {
            selectedOwnerBook.set(0, newTrade.getOwnerBook());
        }
        ownerBookListView = (ListView)findViewById(R.id.ownerInventory);
        adapterO = new BookListAdapter(this,R.layout.book_inventory_list, selectedOwnerBook);
        ownerBookListView.setAdapter(adapterO);
        adapterO.notifyDataSetChanged();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trade_screen);

        newTrade.setCompletion(TradeCompletion.CURRENT);
        selectedBorrowerBooks = newTrade.getBorrowerBook();
        borrowerInventoryListView = (ListView)findViewById(R.id.borrowerInventory);
        adapter = new BookListAdapter(this,R.layout.friend_list, selectedBorrowerBooks);
        borrowerInventoryListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        if (selectedOwnerBook.isEmpty()){
            if (newTrade.getOwnerBook().getTitle() != null) {
                selectedOwnerBook.add((newTrade.getOwnerBook()));
            }
        }else {
            selectedOwnerBook.set(0, newTrade.getOwnerBook());
        }
        ownerBookListView = (ListView)findViewById(R.id.ownerInventory);
        adapterO = new BookListAdapter(this,R.layout.book_inventory_list, selectedOwnerBook);
        ownerBookListView.setAdapter(adapterO);
        adapterO.notifyDataSetChanged();

        borrowerInventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = selectedBorrowerBooks.get(position);

                //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog = new AlertDialog.Builder(CreateTradeScreen.this).create();
                alertDialog.setMessage("");
                alertDialog.setCanceledOnTouchOutside(false);

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();

            }
        });


        borrowerAdd = (Button)findViewById(R.id.borrowerAdd);
        borrowerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent borrowerAddIntent = new Intent(CreateTradeScreen.this, SelectFromBorrowerInventoryActivity.class);
                startActivity(borrowerAddIntent);
                finish();
            }
        });

        ownerSelect = (Button)findViewById(R.id.selectFromFriend);
        ownerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent borrowerAddIntent = new Intent(CreateTradeScreen.this, SelectFromOwnerInventoryActivity.class);
                startActivity(borrowerAddIntent);
                finish();

            }
        });

        submitTrade =  (Button)findViewById(R.id.submitButton);
        submitTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add trade to TradeHistory, and then clear the newTrade variable
                //Not sure if we are right by adding to trade history.
                //Need to get the trade requests started!!

                me.getTradeHistory().addTrade(newTrade);
                newTrade = new Trade();
                mySaveLoad.saveInFile(getApplicationContext(), me);
                //Toast.makeText(getApplicationContext(), "Breakpoint, newTrade added to History", Toast.LENGTH_SHORT).show();

                //Toast to show that the trade has been created
                Toast.makeText(getApplicationContext(), "Trade submitted!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        cancelTrade = (Button)findViewById(R.id.cancelTradeButton);
        cancelTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTrade = new Trade();
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_trade_screen, menu);
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


    /**
     * This method will call the activity to send the email to notify the other user of the trade request.
     * Did some research and it said that making it automated is not as simple as just creating a new
     * intent to go to the other application
     *
     * US04.07.01
     * As an owner, if I accept a trade both parties will be emailed all trade and item information
     * relevant to the trade, as well owner comments for how to continue on with the trade.
     *
     * From the user story, I think this email should be sent after the owner accepts the trade!!
     *
     * @throws android.content.ActivityNotFoundException
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        newTrade = new Trade();
        finish();
    }



}
