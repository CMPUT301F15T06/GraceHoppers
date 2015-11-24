package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This activity allows or searching the server for other registered users which can be added
 * as friends to trade with. Currently the search for users is artificial and does not actually
 * search the server (functionality coming soon). A user can then click on the added friend
 * to explore their profile and items.
 *
 * @see Friends, FriendsListAdapter, Account, FriendProfileScreen
 *
 * @author Hong Wang, Jillian Lovas, Patricia Reyes
 */

public class FriendsScreen extends ActionBarActivity {
    private Activity activity = this;
    SaveLoad saveLoad;
    Account account;
    private ArrayAdapter<String> adapter;
   // private ArrayList<String> friends;
    private AccountManager accountmanager;
    private FriendRequest friendrequest;
    private FriendRequestManager friendrequestmanager;

    //------------------------------------------------------------
    //For UI testing
    public ListView getOldFriendsList() {return oldFriendsList;}
    public ArrayList<String> getFriendList() {return friendList;}
    public EditText getFriendUsername() {return friendUsername;}
    public Button getAddFriendButton() {return  addFriendButton;}

    private Button addFriendButton;
    private EditText friendUsername;
    private ArrayList<String> friendList = new ArrayList<String>();
    private ListView oldFriendsList;
    private ArrayAdapter<String> friendsAdapter;
    //------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_screen);

        friendUsername = (EditText) findViewById(R.id.newFriendText);
        addFriendButton = (Button) findViewById(R.id.addFriendButton);
        oldFriendsList = (ListView) findViewById(R.id.FriendListView);

        saveLoad = new SaveLoad();

        account= saveLoad.loadFromFile(getApplicationContext());
        //friends = account.getFriends().getFriendnames();

        adapter = new FriendsAdapter(getApplicationContext(),R.layout.friend_list, account.getFriends().getFriendnames());
        oldFriendsList.setAdapter(adapter);


        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFriend = friendUsername.getText().toString();

                accountmanager = new AccountManager();
                try {
                    Thread thread = new SearchThread(account.getUsername(), newFriend);
                    thread.start();
                }catch(AlreadyAddedException e){
                    Toast.makeText(getApplicationContext(), "You can't add yourself as a friend, silly!", Toast.LENGTH_SHORT).show();
                }



                //SearchThread thread=new SearchThread(newFriend);
                //thread.start();
                /*

                //****demoAccount stuff here for finding DemoAccount in the list of Accounts *******
                //add a better requirement for this if statement later
               /* if(account.isInAccounts(newFriend)) {

                    try {

                        //accountmanager.searchAccount()

                        account.getFriends().addFriend(account.searchAccountsByUsername(newFriend)); //want to return an account to add the friend BUT prevent doubles
                        Toast.makeText(getApplicationContext(), newFriend + " successfully added as a friend", Toast.LENGTH_SHORT).show();
                        adapter = new FriendsAdapter(getApplicationContext(),R.layout.friend_list, friends);
                        oldFriendsList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        saveLoad.saveInFile(getApplicationContext(), account);


                    } catch (DoesNotExistException e) {
                        Toast.makeText(getApplicationContext(), newFriend + " does not exist", Toast.LENGTH_SHORT).show();
                    } catch (BlankFieldException e) {
                        Toast.makeText(FriendsScreen.this, "No username input found", Toast.LENGTH_SHORT).show();
                    }catch(AlreadyAddedException e){
                        Toast.makeText(FriendsScreen.this, "Already added this user", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), newFriend + " does not exist", Toast.LENGTH_SHORT).show(); //doesn't work so well for blank search but eh
                }
*/




                //**********************************************************************************


                //***put this stuff back once server is ready to go

                //How to call the user's friends list. -account.getFriends()
                //How to pass the context of the account?
                //Can NOT implement until server search/get is ready
             /*   Friends friends=account.getFriends();
                AccountManager accountManager=new AccountManager();
                Account friendAccount=accountManager.getAccount(newFriend);
                int addFriendResult=friends.addFriend(friendAccount);
                //int  addFriendResult = 2; //For testing
                //String newFriend = "usernameX"; //For testing //commented out for now for demoAccount (put back after and also for tests) -JL
                //int addFriendResult = friendList.add(newFriend);
                if  (addFriendResult == 1) {
                    Toast.makeText(activity, newFriend + "is your friend already!", Toast.LENGTH_SHORT).show();
                }
                if (addFriendResult == 2) {
                    Toast.makeText(activity, newFriend + "added as a friend!", Toast.LENGTH_SHORT).show();
                    //account updates here
                    //FriendsList actually changes here.
                    //friendsAdapter.notifyDataSetChanged();
                    //saveInFile();
                }
                if (addFriendResult == 3) {
                    Toast.makeText(activity, newFriend + "is not a Bookwrm user yet!", Toast.LENGTH_SHORT).show();
                }

            */}

        });

        oldFriendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               /* try {
                    //friendAccount = account.getFriends().getFriendByIndex(position);
                    //Log.e("FriendName", "My friend's name is:" + friendAccount.getUsername());
                    //Toast.makeText(getApplicationContext(), "Position is: " + position, Toast.LENGTH_SHORT).show();

                } catch (NegativeNumberException e) {
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
                } catch (TooLongException e) {
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
                }*/

                //saveLoad.saveInFile(getApplicationContext(), account);
                Intent intent = new Intent(FriendsScreen.this, FriendProfileScreen.class);
                intent.putExtra("listPosition", position);
                startActivity(intent);

            }
        });


    }

    protected void onStart(){
        super.onStart();
        adapter = new FriendsAdapter(getApplicationContext(),R.layout.friend_list, account.getFriends().getFriendnames());
        oldFriendsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //onResume() knowledge of using this coems from class presentation of activity lifecycle
    //Abram Hindle, CMPUT301 Fall 2015, September, University of Alberta
    protected void onResume(){
        super.onResume();
        account = saveLoad.loadFromFile(getApplicationContext());
        //Toast.makeText(getApplicationContext(), "Friends has " + account.getFriends().getSize() + " people in it!", Toast.LENGTH_SHORT).show();
       // adapter = new FriendsAdapter(getApplicationContext(),R.layout.friend_list, friends);
        //oldFriendsList.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        //Toast.makeText(getApplicationContext(),"made to resume", Toast.LENGTH_SHORT).show();
        runOnUiThread(UpdateAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends_screen, menu);
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

 /*   class SearchThread extends Thread {
        private String search;

        public SearchThread(String search) {
            this.search = search;
        }

        @Override
        public void run() {
            Account result;
            AccountManager accountManager=new AccountManager();
            result=(accountManager.getAccount(search));

            try {
                if(result == null) {
                    //username does not exist
                    FriendsScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else {
                    boolean alreadyFriend=false;
                    //check if user is added as friend
                    friends=result.getFriends().getFriends();
                    for(int i=0;i<friends.size();i++) {
                        result=accountManager.getAccount(friends.get(i).getUsername());
                        if(result.getUsername()==search) {
                            //user added as friend
                            alreadyFriend=true;
                            FriendsScreen.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User already a friend", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                    if(alreadyFriend==false) {
                        //add friend

                        //update account with new friend

                    }

                }

            }catch(RuntimeException e) {e.printStackTrace();}
        }

    }*/




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
        Accounts result;

            result = accountmanager.searchAccount(receiver);
            Log.e("result", result.toString());


            try {
                if(!result.isEmpty()) {
                    //an account exists
                    FriendsScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Thread thread = new SearchFRThread(sender, receiver); //ensure that no requests already exist between x and y
                            thread.start();
                            //    Log.e("send a FR", "ye");
                        }
                    });

                }


                else {
                    //account searched for does not exist
                   runOnUiThread(DoesntExistMsg);

                }

            }catch(RuntimeException e) {e.printStackTrace();}
        }

}

    class SearchFRThread extends Thread { //look for any friend requests between x and y
        private String sender;
        private String receiver;

        public SearchFRThread(String sender, String receiver) {
            this.sender = sender;
            this.receiver = receiver;
        }

        @Override
        public void run() {
            //FriendRequest result;
            friendrequestmanager=new FriendRequestManager();
            // Log.e("made it thread","made i 2 thread");
            //result=(frmanager.getFriendRequest(search.getUsername()));
            boolean result=false;
            try {
                result = friendrequestmanager.searchFriendRequest(sender, receiver);
            }catch(IOException e){
                Toast.makeText(getApplicationContext(), "caught an exception :C", Toast.LENGTH_SHORT).show();
            }
            if(result==true) {
                //theres already a friend request between x and y
                runOnUiThread(AlreadySentMsg);
                Log.e("true!!","true");
            } else {
                //send a friend request

                try {
                    friendrequest = new FriendRequest();
                    friendrequestmanager = new FriendRequestManager();
                    friendrequest.makeRequest(account, receiver); //make a FR object
                    //friendrequestmanager.addFriendRequest(friendrequest); //store it on the server
                    //proceed to send a friend request
                    Thread thread=new AddFRThread(friendrequest);
                    thread.start();
                    runOnUiThread(SentFRMsg);
                }catch(AlreadyAddedException e){
                    runOnUiThread(AlreadyAddedMsg);
                }
                Log.e("FR already exist?","false");
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


    private Runnable AlreadyAddedMsg=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "You are already friends with this user",
                    Toast.LENGTH_SHORT).show();

            //finish();
        }
    };

    private Runnable AlreadySentMsg=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "A request between you and this user already exists",
                    Toast.LENGTH_SHORT).show();

            //finish();
        }
    };

    private Runnable DoesntExistMsg=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Username does not exist",
                    Toast.LENGTH_SHORT).show();

            //finish();
        }
    };

    private Runnable SentFRMsg=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Friend Request Sent",
                    Toast.LENGTH_SHORT).show();

            //finish();
        }
    };

    class AddFRThread extends Thread {
        private FriendRequest friendrequest;

        public AddFRThread(FriendRequest friendrequest) {
            this.friendrequest = friendrequest;

        }

        @Override
        public void run() {
            friendrequestmanager = new FriendRequestManager();
            friendrequestmanager.addFriendRequest(friendrequest);

            //give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //runOnUiThread(AddFriendSuccess);

        }
    }

    private Runnable UpdateAdapter = new Runnable() {
        @Override
        public void run() {
            adapter = new FriendsAdapter(getApplicationContext(),R.layout.friend_list, account.getFriends().getFriendnames());
            oldFriendsList.setAdapter(adapter);
           // Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();

        }
    };

}
