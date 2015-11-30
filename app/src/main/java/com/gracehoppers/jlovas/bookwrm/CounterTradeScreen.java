package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
 * This activity creates the countertrade screen where the user can create a counter trade against
 * the friend. At this moment, trades are not fully implemented so this activity is not fully ready
 * to function but all UI functionality is possible.
 *
 * @author Hong Wang
 *
 * @see Trade, TradeHistory
 */
public class CounterTradeScreen extends Activity{

    Trade oldTrade = new Trade();
    public static Trade counterTrade = new Trade();
    private ArrayAdapter<Book> adapter;
    private ArrayAdapter<Book> adapter1;
    //private ListView bInventory;
    private Account account1=new Account(); //borrower
    Account account2=new Account(); //owner
    private ListView text;
    TextView ownerText;
    private String bookTitle="";
    Button add;
    Button submit;
    Button cancel;
    int pos;
    int pos1;
    private static ArrayList<Book> borrowerBook = new ArrayList<Book>();

    SaveLoad saveLoad;
    Account account = new Account();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_trade_screen);

        add = (Button) findViewById(R.id.bAdd);
        submit = (Button) findViewById(R.id.submitCounter);
        cancel = (Button) findViewById(R.id.cancelCounter);
        //bInventory = (ListView)findViewById(R.id.bInventory);
        text = (ListView) findViewById(R.id.textView);
        ownerText =(TextView) findViewById(R.id.ownerBook);

        saveLoad = new SaveLoad();
        account = saveLoad.loadFromFile(getApplicationContext());

        pos = getIntent().getIntExtra("listPosition", 0);
        pos1 = getIntent().getIntExtra("BookPosition", (int) Double.POSITIVE_INFINITY);


        //pos1 = getIntent().getIntExtra("BookPosition", (int) Double.POSITIVE_INFINITY);
        //pos1 = getIntent().getIntExtra("BookPosition",0);
        //Toast.makeText(getApplicationContext(), "pos Oncreate "+pos1, Toast.LENGTH_SHORT).show();

        try{
            oldTrade = account.getTradeHistory().getTradeByIndex(pos);
            counterTrade = oldTrade;

            if(pos1<=oldTrade.getBorrower().getInventory().getSize()){
                int i = 0;
                boolean exist = false;
                Book thisBook;
                while (i<borrowerBook.size()) {
                    thisBook = borrowerBook.get(i);
                    if (thisBook.getTitle().equals(oldTrade.getBorrower().getInventory().getBookByIndex(pos1).getTitle())) {
                        exist = true;
                    }
                    i++;
                }

                if (!exist) {
                    borrowerBook.add(oldTrade.getBorrower().getInventory().getBookByIndex(pos1));
                }else{
                    Toast.makeText(CounterTradeScreen.this,"You already chose this book",Toast.LENGTH_SHORT).show();
                }
            }

            counterTrade.setBorrowerBook(borrowerBook);
            //counterTrade.setBorrowerBook(borrowerBook);
        }catch(NegativeNumberException e){

        }catch(TooLongException te){
            Toast.makeText(getApplicationContext(), "Too long", Toast.LENGTH_SHORT).show();
        }

        adapter = new BookListAdapter(this,R.layout.book_inventory_list,borrowerBook );
        text.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        text.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Book book = borrowerBook.get(position);

                //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(CounterTradeScreen.this);
                builder.setTitle("");
                builder.setItems(new CharSequence[]
                                {"Delete", "Cancel"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        borrowerBook.remove(position);
                                        text.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    case 1:
                                        dialog.cancel();

                                }
                            }
                        });
                builder.create().show();

            }
        });


        //Toast.makeText(getApplicationContext(), counterTrade.getOwner().getUsername(), Toast.LENGTH_SHORT).show();

        ownerText.setText(oldTrade.getOwnerBook().getTitle());

        //initialize counterTrade with items in former declined trade
        counterTrade.setOwnerBook(oldTrade.getOwnerBook());

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

                TradeRequest request = new TradeRequest();
                request.makeTradeRequest(oldTrade.getBorrower(), oldTrade.getOwner().getUsername(), counterTrade);
                //check if you have any tR

                Thread thread = new AddTRThread(request);
                thread.start();

                borrowerBook=new ArrayList<Book>();
                counterTrade = new Trade();
                saveLoad.saveInFile(getApplicationContext(), account);
                //Toast.makeText(getApplicationContext(), "Breakpoint, newTrade added to History", Toast.LENGTH_SHORT).show();

                //Toast to show that the trade has been created
                //Toast.makeText(getApplicationContext(), "Trade submitted!", Toast.LENGTH_SHORT).show();
                //finish();
                Toast toast = Toast.makeText(CounterTradeScreen.this, "Counter Trade successfully created", Toast.LENGTH_SHORT);
                toast.show();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent cancelAll = new Intent(CounterTradeScreen.this, ProcessTradeScreen.class);
                //startActivity(cancelAll);
                /*
                if(text.getText().toString()==""){
                    //Toast toast = Toast.makeText(CounterTradeScreen.this, "Borrower BookList already empty", Toast.LENGTH_SHORT);
                    //toast.show();
                }
                text.setText("");*/
                borrowerBook = new ArrayList<Book>();
                counterTrade = new Trade();
                finish();

            }
        });

        //Intent intent = getIntent();
        //bookTitle = intent.getStringExtra("BookName");

        //text.setText(bookTitle);

    }
    //temporary function until fully functioning
  /*  public void setUp(){
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
    } */

    public void onBackPressed(){
        super.onBackPressed();
        borrowerBook = new ArrayList<Book>();
        counterTrade = new Trade();
        finish();
    }

    class AddTRThread extends Thread { //look for friend requests between x and y
        private TradeRequest request;
        private TradeRequestManager manager;

        public AddTRThread(TradeRequest request) {
            this.request = request;
        }

        @Override
        public void run() {
            //FriendRequest result;
            manager = new TradeRequestManager();

            try {
                manager.addTradeRequest(request);
            } catch (Exception e){
                Log.e("Exception", "Caught exception adding");
            }

        }

    }
    protected void onStart(){
        super.onStart();
        //pos1 = getIntent().getIntExtra("BookPosition", (int) Double.POSITIVE_INFINITY);
        //pos1 = getIntent().getIntExtra("BookPosition",0);
        //Toast.makeText(getApplicationContext(), "pos"+pos1, Toast.LENGTH_SHORT).show();

        pos = getIntent().getIntExtra("listPosition", 0);
        pos1 = getIntent().getIntExtra("BookPosition", (int) Double.POSITIVE_INFINITY);

    }


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
