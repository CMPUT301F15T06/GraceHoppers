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
    Account myFriend;

    SaveLoad saveLoad;
    int position;
    private ArrayAdapter<Book> adapter;
    ListView friendInventoryList;
    //private ArrayList<Book> friendInventory;

    //UI test stuff ---------------------------------------------------------------
    public SaveLoad getSaveLoad(){return saveLoad;}
    public Account getMyFriend(){return myFriend;}
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
        }catch(NegativeNumberException e){
            Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
        }catch(TooLongException e) {
            Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
        }

        name =(TextView) findViewById(R.id.fname);
        email =(TextView) findViewById(R.id.femail);
        city = (TextView) findViewById(R.id.fcity);
        friendInventoryList = (ListView)findViewById(R.id.friendInventoryList);
        unFriendButton = (Button)findViewById(R.id.unFriendButton);
        createTradeButton = (Button)findViewById(R.id.createtradebutton);

        name.setText(myFriend.getUsername());
        email.setText(myFriend.getEmail());
        city.setText(myFriend.getCity());

        //friendInventory = myFriend.getInventory().getInventory();
        //friendInventory = myFriend.getInventory().getPublic(myFriend.getInventory());

        //populate the friend's inventory list with their stuff
        adapter = new BookListAdapter(getApplicationContext(),R.layout.friend_inventory_list, myFriend.getInventory().getPublic(myFriend.getInventory()));
        friendInventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();


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
        });

        unFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clicking this button will unfriend the person

                //**NOT ENTIRELY FUNCTIONAL YET *** return to me afterwards and update stuff so it's gone ***
                account.getFriends().unFriend(myFriend);
                Toast.makeText(getApplicationContext(), "Friend removed", Toast.LENGTH_SHORT).show();
                saveLoad.saveInFile(getApplicationContext(), account);
                finish();
            }
        });

        createTradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnTrade = new Intent(FriendProfileScreen.this, CreateTradeScreen.class);
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
}
