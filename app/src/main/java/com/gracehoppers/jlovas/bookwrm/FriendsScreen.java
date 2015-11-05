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

import java.util.ArrayList;

public class FriendsScreen extends ActionBarActivity {
    private Activity activity = this;
    SaveLoad saveLoad;
    Account account;
    private ArrayAdapter<Account> adapter;
    private ArrayList<Account> friends;

    //------------------------------------------------------------
    //For UI testing
    public ListView getOldFriendsList() {return oldFriendsList;}
    public ArrayList<Account> getFriendList() {return friendList;}
    public EditText getFriendUsername() {return friendUsername;}
    public Button getAddFriendButton() {return  addFriendButton;}

    private Button addFriendButton;
    private EditText friendUsername;
    private ArrayList<Account> friendList = new ArrayList<Account>();
    private ListView oldFriendsList;
    private ArrayAdapter<Account> friendsAdapter;
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
        friends = account.getFriends().getFriends();

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFriend = friendUsername.getText().toString();
                //****demoAccount stuff here for finding DemoAccount in the list of Accounts *******
                //add a better requirement for this if statement later
                if(account.isInAccounts(newFriend)) {

                    try {
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

                //test code
                Toast.makeText(getApplicationContext(),"Friends has "+ account.getFriends().getSize()+ " people in it!", Toast.LENGTH_SHORT).show();



                //**********************************************************************************


                //***put this stuff back once server is ready to go
                /*
                //How to call the user's friends list.
                //How to pass the context of the account?
                //Can NOT implement until server search/get is ready
                int  addFriendResult = 2; //For testing
                //String newFriend = "usernameX"; //For testing //commented out for now for demoAccount (put back after and also for tests) -JL
                //int addFriendResult = friendList.add(newFriend);
                if  (addFriendResult == 1) {
                    Toast.makeText(activity, newFriend + "is your friend already!", Toast.LENGTH_SHORT).show();
                }
                if (addFriendResult == 2) {
                    Toast.makeText(activity, newFriend + "added as a friend!", Toast.LENGTH_SHORT).show();
                    //FriendsList actually changes here.
                    //friendsAdapter.notifyDataSetChanged();
                    //saveInFile();
                }
                if (addFriendResult == 3) {
                    Toast.makeText(activity, newFriend + "is not a Bookwrm user yet!", Toast.LENGTH_SHORT).show();
                }
*/
            }

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
                saveLoad.saveInFile(getApplicationContext(), account);
                Intent intent = new Intent(FriendsScreen.this, FriendProfileScreen.class);
                intent.putExtra("listPosition", position);
                startActivity(intent);

            }
        });


    }

    protected void onStart(){
        super.onStart();
        adapter = new FriendsAdapter(getApplicationContext(),R.layout.friend_list, friends);
        oldFriendsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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


}
