package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
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
    private ArrayList<Book> selectedBorrowerBooks  = new ArrayList<Book>();
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

        //mySaveLoad = new SaveLoad();
        //me = mySaveLoad.loadFromFile(getApplicationContext());
        //friend = mySaveLoad.loadFriendFromFile(getApplicationContext());

        //Toast.makeText(getApplicationContext(), "My inventory has " + me.getInventory().getSize() + " items in it!", Toast.LENGTH_SHORT).show();

        newTrade.setOwner(friend);
        newTrade.setBorrower(me);

        pos2 = getIntent().getIntExtra("aPosition", (int) Double.POSITIVE_INFINITY);
        pos = getIntent().getIntExtra("bPosition", (int) Double.POSITIVE_INFINITY);
        //Toast.makeText(getApplicationContext(), "aposition: "+pos+"friend: "+friend.getUsername(), Toast.LENGTH_SHORT).show();

        try {
            Book thisBook = me.getInventory().getBookByIndex(pos);

            boolean exist = false;

            int i = 0;
            String all = "";
            while(i < newTrade.getBorrowerBook().size()){
                if (thisBook.getTitle().toString().equals(newTrade.getBorrowerBook().get(i).getTitle())){
                    exist = true;
                }
                i++;
            }

            if (exist) {
                Toast.makeText(CreateTradeScreen.this,"Already added this book",Toast.LENGTH_SHORT).show();
            }

            if (!exist) {
                newTrade.getBorrowerBook().add(thisBook);
            }


        } catch (NegativeNumberException e) {
            e.printStackTrace();
        } catch (TooLongException e) {
            e.printStackTrace();
        }

        try {
                newTrade.setOwnerBook(friend.getInventory().getBookByIndex(pos2));
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
            if (newTrade.getOwnerBook().getTitle() != "Untitled") {
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

        //please note: onCreate is called before onStart, so I moved all of the data receivers here to avoid nullpointer exceptions -Nicole

        mySaveLoad = new SaveLoad();
        me = mySaveLoad.loadFromFile(getApplicationContext());
        friend = mySaveLoad.loadFriendFromFile(getApplicationContext());



            newTrade.setCompletion(TradeCompletion.CURRENT);
            selectedBorrowerBooks = newTrade.getBorrowerBook();
            borrowerInventoryListView = (ListView) findViewById(R.id.borrowerInventory);
            adapter = new BookListAdapter(this, R.layout.friend_list, selectedBorrowerBooks);
            borrowerInventoryListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();


            if (selectedOwnerBook.isEmpty()) {
                if (newTrade.getOwnerBook().getTitle() != "Untitled") {
                    selectedOwnerBook.add((newTrade.getOwnerBook()));
                }
            } else {
                selectedOwnerBook.set(0, newTrade.getOwnerBook());
            }
            ownerBookListView = (ListView) findViewById(R.id.ownerInventory);
            adapterO = new BookListAdapter(this, R.layout.book_inventory_list, selectedOwnerBook);
            ownerBookListView.setAdapter(adapterO);
            adapterO.notifyDataSetChanged();


            borrowerInventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Book book = selectedBorrowerBooks.get(position);

                    //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateTradeScreen.this);
                    builder.setTitle("");
                    builder.setItems(new CharSequence[]
                                    {"Delete", "Cancel"},
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    switch (which) {
                                        case 0:
                                            selectedBorrowerBooks.remove(position);
                                            ownerBookListView.setAdapter(adapterO);
                                            adapter.notifyDataSetChanged();
                                        case 1:
                                            dialog.cancel();

                                    }
                                }
                            });
                    builder.create().show();

                }
            });


            borrowerAdd = (Button) findViewById(R.id.borrowerAdd);
            borrowerAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent borrowerAddIntent = new Intent(CreateTradeScreen.this, SelectFromOwnerInventoryActivity.class);
                  //  if(getIntent().getStringExtra("flag").equals("search")){ //prevents the app from crashing from no flag extra
                  //      borrowerAddIntent.putExtra("flag", "search");
                  //  } else borrowerAddIntent.putExtra("flag","friend");
                    startActivity(borrowerAddIntent);
                    finish();
                }
            });

            ownerSelect = (Button) findViewById(R.id.selectFromFriend);
            ownerSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent borrowerAddIntent = new Intent(CreateTradeScreen.this, SelectFromBorrowerInventoryActivity.class);
                   // if(getIntent().getStringExtra("flag").equals("search")){ //prevents the app from crashing from no flag extra
                   //     borrowerAddIntent.putExtra("flag","search");
                   // } else borrowerAddIntent.putExtra("flag","friend");
                    startActivity(borrowerAddIntent);
                    finish();

                }
            });

            submitTrade = (Button) findViewById(R.id.submitButton);
            submitTrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Add trade to TradeHistory, and then clear the newTrade variable
                    //Once submit is clicked, it'll start a trade request
                    if (!newTrade.getOwnerBook().getTitle().equals("Untitled")) {
                        TradeRequest request = new TradeRequest();
                        request.makeTradeRequest(me, friend.getUsername(), newTrade);
                        //check if you have any tR
                        Thread thread = new AddTRThread(request);
                        thread.start();

                        newTrade = new Trade();
                        mySaveLoad.saveInFile(getApplicationContext(), me);
                        //Toast.makeText(getApplicationContext(), "Breakpoint, newTrade added to History", Toast.LENGTH_SHORT).show();

                        //Toast to show that the trade has been created
                        Toast.makeText(getApplicationContext(), "Trade submitted!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(CreateTradeScreen.this,"Must Choose one from Owner's Inventory",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            cancelTrade = (Button) findViewById(R.id.cancelTradeButton);
            cancelTrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newTrade = new Trade();
                    finish();
                }
            });

        //if(getIntent().getStringExtra("flag").equals("search")){
            //user has navigated here from the search activity
       //     Toast.makeText(getApplicationContext(), friend.getUsername()+"", Toast.LENGTH_SHORT).show();



       // }

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

    class AddTRThread extends Thread { //look for friend requests between x and y
        private TradeRequest request;
        private TradeRequestManager manager;

        public AddTRThread(TradeRequest request) {
            this.request = request;

        }

        @Override
        public void run() {
            //FriendRequest result;
            manager = new TradeRequestManager();
            // Log.e("made it thread","made i 2 thread");

            try {
                manager.addTradeRequest(request);
            } catch (Exception e){
                Log.e("Exception", "Caught exception adding");
            }

        }

    }

}
