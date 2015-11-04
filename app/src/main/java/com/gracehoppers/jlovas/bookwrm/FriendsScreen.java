package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    //------------------------------------------------------------
    //For UI testing
    public ListView getOldFriendsList() {return oldFriendsList;}
    public ArrayList<Account> getFriendList() {return friendList;}
    public EditText getFriendUsername() {return friendUsername;}
    public Button getAddFriendButton() {return  addFriendButton;}

    private Button addFriendButton;
    private EditText friendUsername;
    private ArrayList<Account> friendList;
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


        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFriend = friendUsername.getText().toString();
                //****demoAccount stuff here for finding DemoAccount in the list of Accounts *******
                //protect against adding the same friend again and again!!
                if(account.isInAccounts(newFriend)) {

                    try {
                        account.getFriends().addFriend(account.searchAccountsByUsername(newFriend));
                        Toast.makeText(getApplicationContext(), newFriend + " successfully added as a friend", Toast.LENGTH_SHORT).show();
                    } catch (DoesNotExistException e) {
                        Toast.makeText(getApplicationContext(), newFriend + " does not exist", Toast.LENGTH_SHORT).show();
                    } catch (BlankFieldException e) {
                        Toast.makeText(FriendsScreen.this, "No username input found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), newFriend + " does not exist", Toast.LENGTH_SHORT).show(); //doesn't work so well for blank search but eh
                }

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
