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

import java.util.ArrayList;

public class SelectFromBorrowerInventoryActivity extends ActionBarActivity {

    private Account me;
    private ListView inventoryList;
    private SaveLoad mySaveLoad;



    private ArrayList<Book> myInventory;
    private ArrayAdapter<Book> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_borrower_inventory);
        inventoryList = (ListView)findViewById(R.id.borrower_Inventory_id);

        mySaveLoad = new SaveLoad();
        me = mySaveLoad.loadFromFile(getApplicationContext());
        myInventory = me.getInventory().getInventory();
        adapter = new BookListAdapter(this,R.layout.book_inventory_list, myInventory);
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        inventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Book book = me.getInventory().getBookByIndex(position);
                } catch (NegativeNumberException e) {
                    e.printStackTrace();
                } catch (TooLongException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SelectFromBorrowerInventoryActivity.this, CreateTradeScreen.class);
                intent.putExtra("aPosition", position);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_from_borrower_inventory, menu);
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(SelectFromBorrowerInventoryActivity.this, CreateTradeScreen.class);
        startActivity(intent);
        finish();
    }

    public ListView getMyInventory() {
        return inventoryList;
    }
}
