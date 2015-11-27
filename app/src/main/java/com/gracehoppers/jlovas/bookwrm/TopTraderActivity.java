package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class TopTraderActivity extends Activity {

    private AccountManager accountManager=new AccountManager();
    Account allAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_trader);
        SearchThread thread=new SearchThread();
        thread.start();

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
            ArrayList<Account> result = null;
            accountManager=new AccountManager();
            ArrayList<Account> all = new ArrayList<Account>();
            result = accountManager.getAccounts("_search?pretty=true&q=*:*");

            try {
                if(result != null) {
                    //username exists
                    TopTraderActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Username exists, please choose a new username", Toast.LENGTH_SHORT).show();
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
