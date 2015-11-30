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

/**
 * This activity allows for the selection of counter items against a friend.
 *
 * @see Trade
 *
 */
public class SelectCounterBooksActivity extends ActionBarActivity {

    ListView counterList;
    private Trade trade = new Trade();
    private ArrayAdapter<Book> adapter;
    private ArrayList<Book> bookList=new ArrayList<Book>();

    private Account account = new Account();

    SaveLoad saveLoad;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_counter_books);

        saveLoad = new SaveLoad();
        account = saveLoad.loadFromFile(getApplicationContext());
        pos = getIntent().getIntExtra("listPosition", 0);

        try{
            trade = account.getTradeHistory().getTradeByIndex(pos);
        }catch(NegativeNumberException e){

        }catch(TooLongException te){
            Toast.makeText(getApplicationContext(), "Too long", Toast.LENGTH_SHORT).show();
        }

        //setUp();

        counterList = (ListView) findViewById(R.id.selectCounterList);


        //set listView to borrower's inventory
        bookList= trade.getBorrower().getInventory().getInventory();
        adapter = new BookListAdapter(this, R.layout.book_inventory_list,bookList);
        counterList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //set onClicklistener to book
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                //Book aBook = bookList.get(pos);
                //.getBorrowerBook().add(aBook);

                Intent intent = new Intent(SelectCounterBooksActivity.this, CounterTradeScreen.class);

                Book aBook = bookList.get(pos);
                pos=trade.getBorrower().getInventory().getRealPosition(aBook.getUniquenum().getNumber());

                intent.putExtra("BookPosition", pos);
                startActivity(intent);
                finish();
            }
        });

    }


/*
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
        Book book1 = new Book();
        book1.setTitle("book1");
        book1.setAuthor("aa");
        Book book2 = new Book();
        book2.setTitle("book2");
        book2.setAuthor("bb");
        inventory.addBook(book1);
        inventory.addBook(book2);
        account1.setInventory(inventory);

        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.addTrade(counterTrade);
        account1.setTradeHistory(tradeHistory);
    }
*/
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

