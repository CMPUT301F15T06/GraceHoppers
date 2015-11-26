package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Allows the user to search the inventories of their friends by textual query OR category
 * @author nlovas
 *
 */
public class SearchFriendInventories extends ActionBarActivity {

    ListView searchedBooksList;
    EditText userQuery;
    Button searchButton;
    Spinner categorySelect;
    Button switchButton;
    Accounts Friends;

    UniqueNumberGenerator ung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend_inventories);

        searchedBooksList = (ListView)findViewById(R.id.listView);
        userQuery = (EditText)findViewById(R.id.queryText);
        searchButton = (Button)findViewById(R.id.searchbutton);
        categorySelect = (Spinner)findViewById(R.id.searchspinner);
        switchButton = (Button)findViewById(R.id.switchbutton);

        //Thread thread = new SearchThread(account.getUsername(), newFriend);
        //thread.start();

        //searching by textual query will be the default, hide the spinner of categories
        categorySelect.setVisibility(categorySelect.INVISIBLE);

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toggle between making the spiner or edittext visible
                if (categorySelect.getVisibility() == view.INVISIBLE) {
                    userQuery.setVisibility(view.INVISIBLE);
                    categorySelect.setVisibility(view.VISIBLE);
                    switchButton.setText("Search by Title");
                } else {
                    userQuery.setVisibility(view.VISIBLE);
                    categorySelect.setVisibility(view.INVISIBLE);
                    switchButton.setText("Search by Category");
                }

            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              // ung = new UniqueNumberGenerator();
              //  UniqueNumber uninum = new UniqueNumber();

               // Thread thread = new AddFirstNumberToServer(uninum); //dont use this again, number has been set
                //thread.start();

              //  uninum = ung.getUniqueNumber();
               // Toast.makeText(getApplicationContext(),"number: "+ uninum.getNumber(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_friend_inventories, menu);
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
