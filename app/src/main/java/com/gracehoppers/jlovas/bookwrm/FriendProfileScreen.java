package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Intent;
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

import java.util.ArrayList;

/**
 * This activity is to display the chosen friend from the list of friends that your account has made.
 *
 * @author Patricia Reyes, Jillian Lovas
 *
 * @see Friends, FriendsScreen, Account
 */
public class FriendProfileScreen extends ActionBarActivity {

    private Activity profileActivity = this;
    private Button unFriendButton;
    private Button createTradeButton;
    public Button getUnFriendButton(){return unFriendButton;}

    TextView name;
    TextView email;
    TextView city;
    Account account;
    //Account myFriend;
    String myFriend;
    AccountManager accountmanager;
    Account result;

    SaveLoad saveLoad;
    int position;
    private ArrayAdapter<Book> adapter;
    ListView friendInventoryList;


    //UI test stuff ---------------------------------------------------------------
    public SaveLoad getSaveLoad(){return saveLoad;}
    public String getMyFriend(){return myFriend;}
    public TextView getFriendName(){return name;}
    public TextView getFriendEmail(){return email;}
    public TextView getFriendCity(){return city;}
    public ListView getFriendInventoryListView(){return friendInventoryList;}
    //-----------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile_screen);

        position = getIntent().getIntExtra("listPosition", 0);


        saveLoad = new SaveLoad();
        account = saveLoad.loadFromFile(FriendProfileScreen.this);



        Log.e("Position", "Position is: "+ position);

        try {
            myFriend = account.getFriends().getFriendByIndex(position);

            Thread viewprofilethread = new SearchThread(account.getUsername(), myFriend);
            viewprofilethread.start();
        }catch(NegativeNumberException e){
            Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
        }catch(TooLongException e) {
            Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
        }catch(AlreadyAddedException e){

        }

        name =(TextView) findViewById(R.id.fname);
        email =(TextView) findViewById(R.id.femail);
        city = (TextView) findViewById(R.id.fcity);
        friendInventoryList = (ListView)findViewById(R.id.friendInventoryList);
        unFriendButton = (Button)findViewById(R.id.unFriendButton);
        createTradeButton = (Button)findViewById(R.id.createtradebutton);

     /*   name.setText(result.getUsername());
        email.setText(myFriend.getEmail());
        city.setText(myFriend.getCity());

        //friendInventory = myFriend.getInventory().getInventory();
        //friendInventory = myFriend.getInventory().getPublic(myFriend.getInventory());

        //populate the friend's inventory list with their stuff
        adapter = new BookListAdapter(getApplicationContext(),R.layout.friend_inventory_list, myFriend.getInventory().getPublic(myFriend.getInventory()));
        friendInventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

/*
        friendInventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {

                try {
                    Book book = myFriend.getInventory().getBookByIndex(position2);
                    //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                } catch (NegativeNumberException e) {
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
                } catch (TooLongException e) {
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(FriendProfileScreen.this, ViewBookActivity.class);
                intent.putExtra("listPosition", position2);
                intent.putExtra("position2", position);
                intent.putExtra("flag", "friendItem");
                startActivity(intent);
            }
        });*/

        unFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clicking this button will unfriend the person

                if(!(result==null)) {
                    account.getFriends().unFriend(myFriend);
                    Toast.makeText(getApplicationContext(), "Unfriended " + result.getUsername(), Toast.LENGTH_SHORT).show();
                    saveLoad.saveInFile(getApplicationContext(), account); //saves this locally

                    result.getFriends().unFriend(account.getUsername()); //unfriends you on their side
                    //now update this on the server on both ends:

                    Thread yourthread = new UpdateAThread(account);
                    yourthread.start();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Thread friendthread = new UpdateAThread(result);
                    friendthread.start();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    finish();
                }
            }
        });

        createTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnTrade = new Intent(FriendProfileScreen.this, CreateTradeScreen.class);
                turnTrade.putExtra("flag", "friend");
                startActivity(turnTrade);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_profile_screen, menu);
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

    class SearchThread extends Thread { //look to see if this account is a real account before sending a friend request
        private String sender;
        private String receiver;
        //private int flag;


        public SearchThread(String sender, String receiver) throws AlreadyAddedException {
            if(sender.equals(receiver)){ //dont allow users to friend themselves!!
                throw new AlreadyAddedException();
            }

            this.sender= sender;
            this.receiver=receiver;
            //this.flag=flag;

        }
        @Override
        public void run() {
            //FriendRequest result;
            accountmanager=new AccountManager();
            // Log.e("made it thread","made i 2 thread");
            //result=(frmanager.getFriendRequest(search.getUsername()));
            //Accounts result;

            result = accountmanager.getAccount(receiver);
            Log.e("result", result.toString());


            try {
                if(!(result==null)) {
                    //an account exists
                    FriendProfileScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            saveLoad.saveFriendInFile(getApplicationContext(),result); //caches this friend (just one friend...for now?)


                            name.setText(result.getUsername());
                            email.setText(result.getEmail());
                            city.setText(result.getCity());

                            //friendInventory = myFriend.getInventory().getInventory();
                            //friendInventory = myFriend.getInventory().getPublic(myFriend.getInventory());

                            //populate the friend's inventory list with their stuff
                            adapter = new BookListAdapter(getApplicationContext(),R.layout.friend_inventory_list, result.getInventory().getPublic(result.getInventory()));
                            friendInventoryList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


                            friendInventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
                                public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {

                                    try {
                                        Book book = result.getInventory().getBookByIndex(position2);
                                        //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                                    } catch (NegativeNumberException e) {
                                        //these will only trip if its a bug on our end, not user's fault
                                        Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
                                    } catch (TooLongException e) {
                                        //these will only trip if its a bug on our end, not user's fault
                                        Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
                                    }

                                    Intent intent = new Intent(FriendProfileScreen.this, ViewBookActivity.class);
                                    //intent.putExtra("listPosition", position2);
                                    intent.putExtra("uninum",result.getInventory().getPublic(result.getInventory()).get(position2).getUniquenum().getNumber()); //pass the unique number to identify the book
                                    //Toast.makeText(getApplicationContext(), ""+result.getInventory().getPublic(result.getInventory()).get(position2).getUniquenum().getNumber(), Toast.LENGTH_SHORT).show();
                                    //intent.putExtra("position2", position);
                                    intent.putExtra("flag", "friendItem");
                                    startActivity(intent);
                                }
                            });


                        }
                    });

                }


                else {
                    //account searched for does not exist
                   // runOnUiThread(DoesntExistMsg);

                }

            }catch(RuntimeException e) {e.printStackTrace();}
        }

    }


    class UpdateAThread extends Thread { //for updating each account on the server
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
