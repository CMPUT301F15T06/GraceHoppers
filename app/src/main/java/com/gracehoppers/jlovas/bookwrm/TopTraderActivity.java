package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An activity for displaying the top traders using the app.
 *
 *
 * @author Hong Chen
 */
public class TopTraderActivity extends Activity {

    private AccountManager accountManager=new AccountManager();
    private Accounts all = new Accounts();
    private TopTraderTrackManager topTraderTrackManager = new TopTraderTrackManager();
    private ArrayList allTraders = new ArrayList();
    private TextView topTraders;
    private String result = "Top Traders:\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_trader);
        SearchThread thread=new SearchThread();
        thread.start();
        topTraders = (TextView) findViewById(R.id.topTraders);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_trader, menu);
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

    class SearchThread extends Thread {

        public SearchThread() {
        }

        @Override
        public void run() {
            accountManager=new AccountManager();
            all = accountManager.allAcounts();
            allTraders = topTraderTrackManager.calculateScores(all);

            int i = 0;
            while (i < allTraders.size()){
                result = result + allTraders.get(i) + "\n";
                i = i + 1 ;
            }

            try {
                if(!allTraders.isEmpty()) {
                    //username exists
                    TopTraderActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            topTraders.setText(result);

                        }
                    });
                }

                else {
                    //add account
                }

            }catch(RuntimeException e) {e.printStackTrace();}
        }

    }

}
