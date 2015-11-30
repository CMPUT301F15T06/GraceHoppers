package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * The HistoryOfTrades Screen displays the current trades the user has with other users,
 * as well as the past trades and their results. Currently this page is not displaying the information
 * because the trade functionality is not fully implemented, but will be in time.
 *
 * @author Hong Chen, Hong Wang, ljuarezr
 *
 * @see Trade, TradeHistory
 *
 */

public class HistoryOfTradesScreen extends ActionBarActivity {

    //No buttons on this screen; only the ListView
    //Not sure if there needs to be a click on item here to view more of a trade
    private ArrayList<Trade> history;
    private SaveLoad saveLoad;
    private Account account;
    ListView historyView;
    private ArrayAdapter<Trade> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_trades_screen);


        saveLoad = new SaveLoad();

        account = saveLoad.loadFromFile(getApplicationContext());


        historyView = (ListView)findViewById(R.id.HistoryView);

        adapter = new TradeHistoryListAdapter(getApplicationContext(), R.layout.trade_history_list, account.getTradeHistory().tradeHistory);
        historyView.setAdapter(adapter);

/*
        //For testing purposes. DELETE AFTER
        //Create a test trade and add it to the list
        Account B = new Account();
        try{
            B.setUsername("B");
            B.setEmail("xyz@gmail.com");
            B.setCity("YEG");
        } catch (NoSpacesException e){

        } catch (TooLongException e){

        } catch (IllegalEmailException e ) {

        }


        //Create two books to make the trade with
        Book bookA = new Book();
        bookA.setTitle("BookA");
        bookA.setAuthor("AuthorA");

        Book bookB = new Book();
        bookB.setTitle("BookB");
        bookB.setAuthor("AuthorB");
        ArrayList<Book> borrowerBookList = new ArrayList<>();
        borrowerBookList.add(bookB);

        //Set up the trade
        Trade trade = new Trade();
        trade.setOwner(account);
        trade.setBorrower(B);
        trade.setBorrowerBook(borrowerBookList);
        trade.setOwnerBook(bookA);
        trade.setOwnerComment("Test Trade");

        //Reset the application to a known state
        account.getTradeHistory().clear();
        account.getTradeHistory().addTrade(trade);

        //confirm that book was added to the TradeHistory
        assertTrue(account.getTradeHistory().getSize() == 1);

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/
        //Need to have the clickOnItem to set a trade as complete.
        historyView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryOfTradesScreen.this, ViewTradeActivity.class);
                //Intent intent = new Intent(HistoryOfTradesScreen.this, CounterTradeScreen.class);
                intent.putExtra("listPosition", position);
                intent.putExtra("flag", "HistoryOfTradesScreen");
                //Toast.makeText(getApplicationContext(), "CLicked on item " + position, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter = new TradeHistoryListAdapter(getApplicationContext(), R.layout.trade_history_list, account.getTradeHistory().tradeHistory);
        historyView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_of_trades_screen, menu);
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
