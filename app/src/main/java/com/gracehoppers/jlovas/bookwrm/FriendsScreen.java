package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendsScreen extends ActionBarActivity {

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
        oldFriendsList = (ListView) findViewById(R.id.friendListView);

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFriend = friendUsername.getText().toString();
                //int returnValue = friendList.add(newFriend);
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
