package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * Once the user is signed in or signed up, they are taken to this screen.
 * <p>
 * The Homescreen is the central screen with the most options available to the user.
 * The user sees their book inventory on this screen and has options to:
 * <ul>
 *     <li>View their profile</li>
 *     <li>Add a new book </li>
 *     <li>View a book in their inventory, if any </li>
 *     <li>Look up friends </li>
 *     <li>View their trade history</li>
 *     <li>Search friend inventories</li>
 * </ul>
 * Currently the options to trade and countertrade are on the homescreen and will be moved for part 5
 * @see Account, Inventory
 * @author jlovas
 */
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
    Button options;
    ImageView friendRequests;
    PhotoDownloads pD;
    final ConnectionCheck connectionCheck=new ConnectionCheck();

    FriendRequestManager frmanager;

    ImageView tradeRequests;
    TradeRequestManager trmanager;
    TradeRequest tradeRequest;
    private static Trade newTrade = new Trade();


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

        pD = (PhotoDownloads)getApplicationContext();

        Intent i=getIntent();
        Bundle Username=i.getExtras();
        username=Username.getString("username");

        //took out the test books that used to be here

        addBookButton= (Button)findViewById(R.id.addBookButton);


        account = saveload.loadFromFile(getApplicationContext());
        //tradeRequest=saveload.loadTrade(getApplicationContext());

        //check connection
        if(connectionCheck.checkConnection(HomeScreen.this) && account.getNeedUpdate()) {
            UniqueNumber uninum;
            UniqueNumberGenerator ung;

            try {
                Inventory inventory = account.getInventory();
                Book myBook = inventory.getBookByIndex((inventory.getSize()) - 1);

                //update account
                ung = new UniqueNumberGenerator();
                uninum = ung.getUniqueNumber(); //!!!!!   this requires a connection. ung has a thread within its class!
                myBook.setUniquenum(uninum);

                Thread yourthread = new UpdateAThread(account); //update the server to have this book
                yourthread.start();

                account.setNeedUpdate(false);
            }catch(NegativeNumberException e){

            }catch(TooLongException e){

            }
        }

        /*if(connectionCheck.checkConnection(HomeScreen.this) && tradeRequest.getNeedUpdate()){
            Thread thread = new AddTRThread(tradeRequest);
            thread.start();

            //newTrade = new Trade();
            tradeRequest.setNeedUpdate(false);
        }
*/

        inventoryList = (ListView)findViewById(R.id.inventory1);

        //----for UI--------------------------------
        //adapter = new BookListAdapter(this,R.layout.book_inventory_list, account.getInventory().getInventory());
        //inventoryList.setAdapter(adapter);
        ArrayList<Book> inventory=account.getInventory().getInventory();
        ArrayList<Book> inventoryCopy=new ArrayList<>();
        if(account.getNeedUpdate()){
            int size=inventory.size();
            for(int j=0;j<size-1;j++){
                inventoryCopy.add(inventory.get(j));
            }
            adapter = new BookListAdapter(this,R.layout.book_inventory_list, inventoryCopy);
        }
        else {
            adapter = new BookListAdapter(this, R.layout.book_inventory_list, inventory);
        }
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //------------------------------------------

        //test code
        //Toast.makeText(getApplicationContext(), "Friends has " + account.getFriends().getSize() + " people in it!", Toast.LENGTH_SHORT).show();


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

        Button tradeHistory = (Button) findViewById(R.id.TradeHistoryButton);
        tradeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent turnTradeHistory = new Intent(HomeScreen.this, HistoryOfTradesScreen.class);
                startActivity(turnTradeHistory);
            }
        });

        Button search = (Button) findViewById(R.id.searchbutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, SearchFriendInventories.class);
                startActivity(intent);
            }
        });

        friendRequests= (ImageView)findViewById(R.id.frimageView);
        friendRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for testing purposes ---------------------------------
                //FriendRequest friendrequest = new FriendRequest();
/*
                try {

                   // Account demoAccount=new Account();
                    //demoAccount.setUsername("DemoAccount");
                   // demoAccount.setEmail("demo@gmail.com");
                    //demoAccount.setCity("demoville");

                    //friendrequest.makeRequest(demoAccount, account.getUsername());
                    //Toast.makeText(getApplicationContext(), friendrequest.getReceiver(), Toast.LENGTH_SHORT).show();
                }catch(AlreadyAddedException e){
                    Toast.makeText(getApplicationContext(), "Already friends with this user", Toast.LENGTH_SHORT).show();
                } catch (NoSpacesException e) {
                    e.printStackTrace();  //remove this
                } catch (TooLongException e) {
                    e.printStackTrace(); //remove this
                } catch (IllegalEmailException e) {
                    e.printStackTrace(); //remove this
                }
*/
                //Thread thread=new AddFRThread(friendrequest);
                //thread.start();
                //Log.e("Button press","buton pressed");
                //Thread thread = new SearchFRThread(account.getUsername(),"shadownvvvater");
                //thread.start();
                //end line of test code --------------------------------

                if(friendRequests.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.greenenvelope).getConstantState())){
                    //http://stackoverflow.com/questions/9125229/comparing-two-drawables-in-android, user Mejonzhan, 2015-19-11
                    //if the envelope is green
                    Intent FRintent = new Intent(HomeScreen.this, ViewFRActivity.class);
                    startActivity(FRintent);
                }

                else{
                    Toast.makeText(getApplicationContext(), "No current friend requests", Toast.LENGTH_SHORT).show();
                }


            }
        });


        tradeRequests= (ImageView)findViewById(R.id.trimageView);
        tradeRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("died in", "traderequests image view clicked");

                if(tradeRequests.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.greenenvelope).getConstantState())){
                    //http://stackoverflow.com/questions/9125229/comparing-two-drawables-in-android, user Mejonzhan, 2015-19-11
                    //if the envelope is green
                   // Toast.makeText(getApplicationContext(), "Breakpoint", Toast.LENGTH_SHORT).show();
                    Intent tradeIntent = new Intent(HomeScreen.this, ViewTradeRequest.class);
                    startActivity(tradeIntent);
                }

                else{
                    Toast.makeText(getApplicationContext(), "No current trade requests", Toast.LENGTH_SHORT).show();
                }


            }
        });

        options = (Button)findViewById(R.id.optionsButton);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(getApplicationContext(), "Enabled is: " + pD.getEnabled(), Toast.LENGTH_SHORT).show();

        if(connectionCheck.checkConnection(HomeScreen.this) && account.getNeedUpdate()) {
            UniqueNumber uninum;
            UniqueNumberGenerator ung;

            try {
                Inventory inventory = account.getInventory();
                //Book myBook = inventory.getBookByIndex((inventory.getSize()) - 1);
                Book myBook = inventory.getBookByIndex((inventory.getSize()) - 1);

                //update account
                ung = new UniqueNumberGenerator();
                uninum = ung.getUniqueNumber(); //!!!!!   this requires a connection. ung has a thread within its class!
                myBook.setUniquenum(uninum);

                Thread yourthread = new UpdateAThread(account); //update the server to have this book
                yourthread.start();

                account.setNeedUpdate(false);
            }catch(NegativeNumberException e){

            }catch(TooLongException e){

            }


        }
        //inventory = account.getInventory().getInventory();
        //saveload.saveInFile(getApplicationContext(),account);
        ArrayList<Book> inventory=account.getInventory().getInventory();
        ArrayList<Book> inventoryCopy=new ArrayList<>();
        if(account.getNeedUpdate()){
            int size=inventory.size();
            for(int i=0;i<size-1;i++){
                inventoryCopy.add(inventory.get(i));
            }
            adapter = new BookListAdapter(this,R.layout.book_inventory_list, inventoryCopy);
        }
        else {
            adapter = new BookListAdapter(this, R.layout.book_inventory_list, inventory);
        }
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    //onResume() knowledge of using this coems from class presentation of activity lifecycle
    //Abram Hindle, CMPUT301 Fall 2015, September, University of Alberta
    protected void onResume(){
        super.onResume();
        account = saveload.loadFromFile(getApplicationContext());

        if(connectionCheck.checkConnection(HomeScreen.this) && account.getNeedUpdate()) {
            UniqueNumber uninum;
            UniqueNumberGenerator ung;

            try {
                Inventory inventory = account.getInventory();
                Book myBook = inventory.getBookByIndex((inventory.getSize()) - 1);

                //update account
                ung = new UniqueNumberGenerator();
                uninum = ung.getUniqueNumber(); //!!!!!   this requires a connection. ung has a thread within its class!
                myBook.setUniquenum(uninum);
                

                Thread yourthread = new UpdateAThread(account); //update the server to have this book
                yourthread.start();

                account.setNeedUpdate(false);
            }catch(NegativeNumberException e){

            }catch(TooLongException e){

            }


        }

        ArrayList<Book> inventory=account.getInventory().getInventory();
        ArrayList<Book> inventoryCopy=new ArrayList<>();
        if(account.getNeedUpdate()){
            int size=inventory.size();
            for(int i=0;i<size-1;i++){
                inventoryCopy.add(inventory.get(i));
            }
            adapter = new BookListAdapter(this,R.layout.book_inventory_list, inventoryCopy);
        }
        else {
            adapter = new BookListAdapter(this, R.layout.book_inventory_list, inventory);
        }
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Toast.makeText(getApplicationContext(), "Inventory:  " + account.getInventory().getSize() , Toast.LENGTH_SHORT).show();

        if(connectionCheck.checkConnection(HomeScreen.this)) {
            //check if you have any FR
            Thread thread = new FindFRThread(account.getUsername());
            thread.start();

            //check if you have any TR
            Thread thread2 = new FindTRThread(account.getUsername());
            thread2.start();
        }
        else{
            Toast.makeText(getApplicationContext(), "No connection" , Toast.LENGTH_SHORT).show();
        }
    }








    //delete this after testing!! -----------------------------------------------------------------------




    class FindFRThread extends Thread { //find any unanswered FR's for this user
        private String user1;


        public FindFRThread(String u1) {
            this.user1 = u1;

        }

        @Override
        public void run() {
            //FriendRequest result;
            frmanager=new FriendRequestManager();
            // Log.e("made it thread","made i 2 thread");
            //result=(frmanager.getFriendRequest(search.getUsername()));
           FriendRequests result = new FriendRequests();
            try {
                result = frmanager.findFriendRequests(user1);
            }catch(IOException e){
                Toast.makeText(getApplicationContext(), "caught an exception :C", Toast.LENGTH_SHORT).show();
            }
            if(!result.isEmpty()) {
                runOnUiThread(ChangeEnvelopeColorG);
                Log.e("true!!","you have a FR for you");
            } else {
                runOnUiThread(ChangeEnvelopeColorB);
                Log.e("false!!","false");
            }
/*
            try { //get rid of this part later/change it
                if(result != null) {
                    //a friendrequest exists
                    HomeScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "found a friend request", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else {
                    //add account
                    Thread thread=new AddThread(search);
                    thread.start();
                }

            }catch(RuntimeException e) {e.printStackTrace();}
        */}

    }


    private Runnable ChangeEnvelopeColorG=new Runnable() {
        @Override
        public void run() {
            //change the envelope green
            friendRequests.setImageResource(R.drawable.greenenvelope);

        }
    };

    private Runnable ChangeEnvelopeColorB=new Runnable() {
        @Override
        public void run() {
            //change the envelope black
            friendRequests.setImageResource(R.drawable.blackenvelope);

        }
    };
    //---------------------------------------------------------------------------------------------------


    class UpdateAThread extends Thread { //for updating account on the server
        private Account account;

        public UpdateAThread(Account account) {
            this.account = account;
        }

        @Override
        public void run() {

            AccountManager accountManager = new AccountManager();
            accountManager.updateAccount(account);
            account.setNeedUpdate(false);

        }

    }

    class FindTRThread extends Thread { //find any unanswered TR's for this user
        private String user1;


        public FindTRThread(String u1) {
            this.user1 = u1;

        }

        @Override
        public void run() {
            //tradeRequest result;
            trmanager=new TradeRequestManager();
            // Log.e("made it thread","made i 2 thread");
            //result=(frmanager.getFriendRequest(search.getUsername()));
            TradeRequests result = new TradeRequests();
            try {
                result = trmanager.findTradeRequests(user1);
            }catch(IOException e){
                Toast.makeText(getApplicationContext(), "caught an exception :C", Toast.LENGTH_SHORT).show();
            }
            if(!result.isEmpty()) {
                runOnUiThread(ChangeEnvelopeColorGreen);
                Log.e("true!!","you have a TR for you");
            } else {
                runOnUiThread(ChangeEnvelopeColorBlack);
                Log.e("false!!","false");
            }
        }

    }


    private Runnable ChangeEnvelopeColorGreen=new Runnable() {
        @Override
        public void run() {
            //change the envelope green
            tradeRequests.setImageResource(R.drawable.greenenvelope);

        }
    };

    private Runnable ChangeEnvelopeColorBlack=new Runnable() {
        @Override
        public void run() {
            //change the envelope black
            tradeRequests.setImageResource(R.drawable.blackenvelope);

        }
    };

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
    //---------------------------------------------------------------------------------------------------

}
