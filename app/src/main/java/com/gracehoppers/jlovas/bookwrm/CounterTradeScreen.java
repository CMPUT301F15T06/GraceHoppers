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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This activity creates the countertrade screen where the user can create a counter trade against
 * the friend. At this moment, trades are not fully implemented so this activity is not fully ready
 * to function but all UI functionality is possible.
 *
 * @author Hong Wang
 *
 * @see Trade, TradeHistory
 */
public class CounterTradeScreen extends ActionBarActivity {

    Trade oldTrade = new Trade();
    private Trade counterTrade = new Trade();
    private ArrayAdapter<Book> adapter;
    //private ListView bInventory;
    private Account account1=new Account(); //borrower
    Account account2=new Account(); //owner
    private TextView text;
    TextView ownerText;
    private String bookTitle="";
    Button add;
    Button submit;
    Button cancel;
    int pos;
    int pos1;
    private ArrayList<Book> borrowerBook = new ArrayList<Book>();

    SaveLoad saveLoad;
    Account account = new Account();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_trade_screen);

        saveLoad = new SaveLoad();
        account = saveLoad.loadFromFile(getApplicationContext());
        pos = getIntent().getIntExtra("listPosition", 0);
        pos1 = getIntent().getIntExtra("BookPosition", (int) Double.POSITIVE_INFINITY);


        try{
            oldTrade = account.getTradeHistory().getTradeByIndex(pos);
            borrowerBook.add(oldTrade.getBorrower().getInventory().getBookByIndex(pos));
            counterTrade = oldTrade;
            counterTrade.setBorrowerBook(borrowerBook);
        }catch(NegativeNumberException e){

        }catch(TooLongException te){
            Toast.makeText(getApplicationContext(), "Too long", Toast.LENGTH_SHORT).show();
        }


        Toast.makeText(getApplicationContext(), counterTrade.getOwner().getUsername(), Toast.LENGTH_SHORT).show();

        add = (Button) findViewById(R.id.bAdd);
        submit = (Button) findViewById(R.id.submitCounter);
        cancel = (Button) findViewById(R.id.cancelCounter);
        //bInventory = (ListView)findViewById(R.id.bInventory);
        text = (TextView) findViewById(R.id.textView);
        ownerText =(TextView) findViewById(R.id.ownerBook);

        //setUp();
        /*
        try {
            oldTrade = account1.getTradeHistory().getTradeByIndex(0);
        } catch (NegativeNumberException e) {
            Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
        } catch (TooLongException e) {
            Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
        }
        */
        ownerText.setText(oldTrade.getOwnerBook().getTitle());

        //initialize counterTrade with items in former declined trade
        counterTrade.setOwnerBook(oldTrade.getOwnerBook());


        String bookTitles ="";

        for(Book b: counterTrade.getBorrowerBook()){
            bookTitles= bookTitles + b.getTitle() +"\n";
        }
        text.setText(bookTitles);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addBook = new Intent(CounterTradeScreen.this, SelectCounterBooksActivity.class);
                addBook.putExtra("listPosition", pos);
                startActivity(addBook);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //oldTrade.getOwner().getTradeHistory().addTrade(counterTrade);
                //oldTrade.getBorrower().getTradeHistory().addTrade(counterTrade);

                //tradeHistory.addTrade(counterTrade);

                //Intent submitAll = new Intent(CounterTradeScreen.this,HomeScreen.class);
                //startActivity(submitAll);

                Toast toast = Toast.makeText(CounterTradeScreen.this, "Haven't implement, will do saving trade into account's history", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent cancelAll = new Intent(CounterTradeScreen.this, ProcessTradeScreen.class);
                //startActivity(cancelAll);
                if(text.getText().toString()==""){
                    //Toast toast = Toast.makeText(CounterTradeScreen.this, "Borrower BookList already empty", Toast.LENGTH_SHORT);
                    //toast.show();
                }
                text.setText("");

            }
        });

        //Intent intent = getIntent();
        //bookTitle = intent.getStringExtra("BookName");

        //text.setText(bookTitle);

    }
    //temporary function until fully functioning
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
        ownerBook.setTitle("goodBook");
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

        <ListView
        android:layout_marginTop="20dp"
        android:layout_width="wrap_content"
        android:layout_height="300dp"
        android:id="@+id/bInventory"
        android:layout_below="@+id/textView"
        android:layout_alignParentStart="true"
        android:background="#fffbf4" />

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
