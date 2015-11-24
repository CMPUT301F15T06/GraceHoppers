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

import java.util.ArrayList;

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

        //Need to have the clickOnItem to set a trade as complete.
        historyView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryOfTradesScreen.this, ViewBookActivity.class);
                intent.putExtra("listPosition", position);
                intent.putExtra("flag", "HistoryOfTradesScreen");

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
