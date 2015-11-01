package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreateTradeScreen extends Activity {

    private ListView borrowerInventoryListView;
    private ArrayAdapter<Book> adapter;
    private ArrayList<Book> selectedBorrowerBooks;
    private Book ownerBook;
    private Trade newTrade = new Trade();
    Button borrowerAdd;
    Button ownerSelect;
    Button submitTrade;
    Button cancelTrade;


    @Override
    protected void onStart(){
        super.onStart();
        selectedBorrowerBooks = newTrade.getBorrowerBook();
        borrowerInventoryListView = (ListView)findViewById(R.id.borrowerInventory);
        adapter = new BookListAdapter(this,R.layout.book_inventory_list, selectedBorrowerBooks);
        borrowerInventoryListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ownerBook = newTrade.getOwnerBook();
        TextView ownerBookTextView = (TextView)findViewById(R.id.ownerBookText);
        ownerBookTextView.setText(ownerBook.getTitle());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trade_screen);

        borrowerAdd = (Button)findViewById(R.id.borrowerAdd);
        borrowerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent borrowerAddIntent = new Intent(CreateTradeScreen.this, SelectFromBorrowerInventoryActivity.class);
                startActivity(borrowerAddIntent);
            }
        });

        ownerSelect = (Button)findViewById(R.id.selectFromFriend);
        ownerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent borrowerAddIntent = new Intent(CreateTradeScreen.this, SelectFromOwnerInventoryActivity.class);
                startActivity(borrowerAddIntent);
            }
        });

        submitTrade =  (Button)findViewById(R.id.submitButton);
        submitTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add this Trade into Trade History

            }
        });

        cancelTrade = (Button)findViewById(R.id.cancelTradeButton);
        cancelTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTrade = null;
                Intent borrowerAddIntent = new Intent(CreateTradeScreen.this, HomeScreen.class);
                startActivity(borrowerAddIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_trade_screen, menu);
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
