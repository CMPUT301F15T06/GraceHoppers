package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends ActionBarActivity {
    Button addBookButton;

    private ListView inventoryList;   //HC modified
    private ArrayAdapter<Book> adapter; //HC
    private ArrayList<Book> inventory = new ArrayList<Book>();    //HC

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        addBookButton= (Button)findViewById(R.id.addBookButton);

        inventoryList = (ListView)findViewById(R.id.inventory);         //HC

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addBIntent = new Intent(HomeScreen.this, AddBookScreen.class);
                startActivity(addBIntent);
            }
        });

    }

    @Override
    protected void onStart(){                           // HC
        super.onStart();
        adapter = new ArrayAdapter<Book>(this,R.layout.book_inventory_list, inventory);
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
