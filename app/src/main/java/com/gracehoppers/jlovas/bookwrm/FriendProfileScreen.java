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

public class FriendProfileScreen extends ActionBarActivity {

    private Activity profileActivity = this;
    private Button unFriendButton;
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
    private ArrayList<Book> friendInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile_screen);

        position = getIntent().getIntExtra("listPosition", 0);
        Log.e("Position", "Position is: " + position);

        saveLoad = new SaveLoad();
        account = saveLoad.loadFromFile(FriendProfileScreen.this);
        Log.e("Me", "My username is: " + account.getUsername());
        try {
            Log.e("Friend", "My Friend is: " + account.getFriends().getFriendByIndex(0).getUsername());
        }catch(NegativeNumberException e){}
        catch(TooLongException e){}

        try {
            myFriend = account.getFriends().getFriendByIndex(position);
            Log.e("Me", "My username is: " + account.getUsername());
            Log.e("Friend", "My Friend is: " + myFriend.getUsername());
        }catch(NegativeNumberException e){
            Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
        }catch(TooLongException e) {
            Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
        }

        name =(TextView) findViewById(R.id.fname);
        email =(TextView) findViewById(R.id.femail);
        city = (TextView) findViewById(R.id.fcity);
        friendInventoryList = (ListView)findViewById(R.id.friendInventoryList);

        name.setText(myFriend.getUsername());
        email.setText(myFriend.getEmail());
        city.setText(myFriend.getCity());

        friendInventory = myFriend.getInventory().getInventory();

        //populate the friend's inventory list with their stuff
        adapter = new BookListAdapter(getApplicationContext(),R.layout.friend_inventory_list, friendInventory);
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
                intent.putExtra("flag","friendItem");
                startActivity(intent);
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
