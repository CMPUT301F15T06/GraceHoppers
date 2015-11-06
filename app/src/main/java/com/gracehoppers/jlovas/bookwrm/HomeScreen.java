package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeScreen extends Activity {
    Button addBookButton;

    private ListView inventoryList;
    private ArrayAdapter<Book> adapter;
    private Inventory userInventory = new Inventory();
    //private ArrayList<Book> inventory;
    Account account;
    private SaveLoad saveload= new SaveLoad();
    String username;
    Button profile;
    Button friend;


    //For UI testing -----------------------------------------
    public SaveLoad getSaveload(){return saveload;}
    //public ArrayList<Book> getInventory(){return inventory;}
    public ListView getInventoryList() {return inventoryList;}
    public Button getAddBookButton(){return addBookButton;}
    public Button getProfileButton(){return profile;}
    public Button getFriendButton(){return friend;}
    //-------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent i=getIntent();
        Bundle Username=i.getExtras();
        username=Username.getString("username");

        //took out the test books that used to be here

        addBookButton= (Button)findViewById(R.id.addBookButton);

        account = saveload.loadFromFile(getApplicationContext());



        //inventory = account.getInventory().getInventory();
        inventoryList = (ListView)findViewById(R.id.inventory1);

        //----for UI--------------------------------
        adapter = new BookListAdapter(this,R.layout.book_inventory_list, account.getInventory().getInventory()); //second parameter used to be inventory
        inventoryList.setAdapter(adapter);
        //------------------------------------------



        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addBIntent = new Intent(HomeScreen.this, AddBookScreen.class);
                startActivity(addBIntent);
            }
        });

        inventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                try {
                    Book book = account.getInventory().getBookByIndex(position);
                    //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                } catch (NegativeNumberException e) {
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
                } catch (TooLongException e) {
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
                }
                */
                Intent intent = new Intent(HomeScreen.this, ViewBookActivity.class);
                intent.putExtra("listPosition", position);
                intent.putExtra("flag", "Homescreen");
                startActivity(intent);
            }
        });

        profile = (Button)findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnProfile = new Intent(HomeScreen.this, ProfileScreen.class);
                //turnProfile.putExtra(ProfileScreen.Username,username);
                turnProfile.putExtra("Username",username);
                startActivity(turnProfile);
            }
        });

        friend = (Button) findViewById(R.id.friendsButton);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnFriend = new Intent(HomeScreen.this, FriendsScreen.class);
                startActivity(turnFriend);
            }
        });

        Button trade = (Button) findViewById(R.id.tradeButton);
        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnTrade = new Intent(HomeScreen.this, CreateTradeScreen.class);
                startActivity(turnTrade);
            }
        });

        Button tradeHistory = (Button) findViewById(R.id.TradeHistoryButton);
        tradeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent turnTradeHistory = new Intent(HomeScreen.this, HistoryOfTradesScreen.class);
                startActivity(turnTradeHistory);
            }
        });

        Button counter = (Button) findViewById(R.id.turnCounter);
        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnCounter = new Intent(HomeScreen.this, ProcessTradeScreen.class);
                startActivity(turnCounter);
            }
        });

    }



    @Override
    protected void onStart(){
        super.onStart();

        //inventory = account.getInventory().getInventory();
        adapter = new BookListAdapter(this,R.layout.book_inventory_list, account.getInventory().getInventory()); //second parameter used to be inventory
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //onResume() knowledge of using this coems from class presentation of activity lifecycle
    //Abram Hindle, CMPUT301 Fall 2015, September, University of Alberta
    protected void onResume(){
        super.onResume();
        account = saveload.loadFromFile(getApplicationContext());
        //inventory = account.getInventory().getInventory();
        adapter = new BookListAdapter(this,R.layout.book_inventory_list, account.getInventory().getInventory()); //second parameeter used to be inventory
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
