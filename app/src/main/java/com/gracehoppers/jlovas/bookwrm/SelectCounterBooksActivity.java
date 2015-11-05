package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectCounterBooksActivity extends ActionBarActivity {

    private ListView counterList;
    private Trade counterTrade = new Trade();
    private ArrayAdapter<Book> adapter;
    private ArrayList<Book> bookList=new ArrayList<Book>();

    private Account account1 = new Account();
    private Account account2 = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_counter_books);

        setUp();

        counterList = (ListView) findViewById(R.id.selectCounterList);

        //set listView to borrower's inventory
        bookList= counterTrade.getBorrower().getInventory().getInventory();
        adapter = new BookListAdapter(this, R.layout.book_inventory_list,bookList);
        counterList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //set onClicklistener to book
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Book aBook = bookList.get(pos);
                counterTrade.getBorrowerBook().add(aBook);

                Intent intent = new Intent(SelectCounterBooksActivity.this, CounterTradeScreen.class);
                startActivity(intent);
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

        counterTrade.setBorrower(account1);
        counterTrade.setOwner(account2);
        Book ownerBook = new Book();
        counterTrade.setOwnerBook(ownerBook);

        Inventory inventory = new Inventory();
        Book borrowerBook = new Book();
        borrowerBook.setAuthor("aa");
        borrowerBook.setTitle("workwork");
        inventory.addBook(borrowerBook);
        account1.setInventory(inventory);

        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.addTrade(counterTrade);
        account1.setTradeHistory(tradeHistory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_counter_books, menu);
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

