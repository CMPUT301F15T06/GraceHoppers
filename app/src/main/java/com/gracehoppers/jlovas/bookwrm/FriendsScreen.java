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

public class FriendsScreen extends ActionBarActivity {
    private Activity activity = this;

    private Button addFriendButton;
    private EditText friendUsername;
    private Friends friendList;
    private ListView oldFriendsList;
    private ArrayAdapter<Account> friendsAdapter;

    public ListView getOldFriendsList() {return oldFriendsList;}
    public Friends getFriendList() {return friendList;}
    public EditText getFriendUsername() {return friendUsername;}
    public Button getAddFriendButton() {return  addFriendButton;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_screen);

        friendUsername = (EditText) findViewById(R.id.newFriendText);
        addFriendButton = (Button) findViewById(R.id.addFriendButton);
        oldFriendsList = (ListView) findViewById(R.id.FriendListView);

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String newFriend = friendUsername.getText().toString();
                //How to call the user's friends list.
                //How to pass the context of the account?
                int  addFriendResult = 2; //For testing
                String newFriend = "usernameX"; //For testing
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
