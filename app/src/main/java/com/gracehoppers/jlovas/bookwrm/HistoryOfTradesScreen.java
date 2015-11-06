package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryOfTradesScreen extends ActionBarActivity {

    //No buttons on this screen; only the ListView
    //Not sure if there needs to be a click on item here to view more of a trade
    private ArrayList<Trade> history;
    private SaveLoad saveLoad;
    private Account account;
    private ListView historyView;
    private ArrayAdapter<Trade> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_trades_screen);

        //Link the TradeHistory array with the ListView
        /* This is a static view, since we can't delete
           And can't add a trade from the same activity/screen */

        saveLoad = new SaveLoad();

        account = saveLoad.loadFromFile(getApplicationContext());
        //history = account.getTradeHistory();

        historyView = (ListView)findViewById(R.id.HistoryView);

    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter = new TradeHistoryListAdapter(getApplicationContext(), R.layout.trade_history_list, history);
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
