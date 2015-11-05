package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CounterTradeScreen extends ActionBarActivity {

    private Trade oldTrade = new Trade();
    private Trade counterTrade = new Trade();
    private ArrayAdapter<Book> adapter;
    private ListView bInventory;
    private Account account1=new Account(); //borrower
    private Account account2=new Account(); //owner

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_trade_screen);

        Button add = (Button) findViewById(R.id.bAdd);
        Button submit = (Button) findViewById(R.id.submitCounter);
        Button cancel = (Button) findViewById(R.id.cancelCounter);
        bInventory = (ListView)findViewById(R.id.bInventory);

        setUp();
        oldTrade =account1.getTradeHistory().getTradeByIndex(0);

        //initialize counterTrade with items in former declined trade
        Book oBook = oldTrade.getOwnerBook();
        counterTrade.setOwnerBook(oBook);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addBook = new Intent(CounterTradeScreen.this, SelectCounterBooksActivity.class);
                startActivity(addBook);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldTrade.getOwner().getTradeHistory().addTrade(counterTrade);
                oldTrade.getBorrower().getTradeHistory().addTrade(counterTrade);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelAll = new Intent(CounterTradeScreen.this,HomeScreen.class);
                startActivity(cancelAll);
            }
        });

    }

    public void setUp(){
        try{
            account1.setCity("Lulala");
            account1.setUsername("hahaha");
            account1.setEmail("wow@cool.ca");

            account2.setCity("Lulala");
            account2.setUsername("gegege");
            account2.setEmail("wow@ha.ca");

        }catch(IllegalEmailException e){
        }catch(TooLongException te){
        }catch (NoSpacesException ne){
        }

        oldTrade.setBorrower(account1);
        oldTrade.setOwner(account2);
        Book ownerBook = new Book();
        oldTrade.setOwnerBook(ownerBook);

        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.addTrade(oldTrade);
        account1.setTradeHistory(tradeHistory);
    }

    /*
    protected void onStart(){
        super.onStart();

        //set view adapter for selected borrower book list with stored data
        ArrayList<Book> selectedBooks = counterTrade.getBorrowerBook();

        if (! selectedBooks.isEmpty()){
            adapter = new BookListAdapter(this,R.layout.book_inventory_list,selectedBooks);
            bInventory.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_counter_trade_screen, menu);
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
