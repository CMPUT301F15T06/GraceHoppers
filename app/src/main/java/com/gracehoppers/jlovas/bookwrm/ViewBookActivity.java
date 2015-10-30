package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class ViewBookActivity extends ActionBarActivity {

    /*
    When a user clicks on a book in their inventory, they can view its details, choose to edit it, or delete it.
     */

    TextView bookTitle;
    TextView bookAuthor;
    private SaveLoad saveload = new SaveLoad();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        bookTitle= (TextView)findViewById(R.id.BookTitletextView);
        bookAuthor= (TextView)findViewById(R.id.authortextView);

        int pos = getIntent().getIntExtra("listPosition", 0); //https://youtu.be/ViwazAAR-vE, TZCoder, 2015-30-10

        Account account;

        account = saveload.loadFromFile(ViewBookActivity.this);

        Book tempBook = account.getInventory().getBookByIndex(pos);

        bookTitle.setText(tempBook.getTitle());
        bookAuthor.setText(tempBook.getAuthor());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_book, menu);
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
