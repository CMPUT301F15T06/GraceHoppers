package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ProcessTradeScreen extends ActionBarActivity {

    private Account owner;
    private Account borrower;
    private Book ownerBook;  //can be 1
    private ArrayList<Book> borrowerBook; //can be 0 or more

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_trade_screen);

        TextView bName = (TextView)findViewById(R.id.bName);
        TextView bBook = (TextView)findViewById(R.id.bBook);
        TextView oBook = (TextView)findViewById(R.id.oBook);
        Button accept =(Button)findViewById(R.id.accept);
        Button decline = (Button)findViewById(R.id.decline);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_process_trade_screen, menu);
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
