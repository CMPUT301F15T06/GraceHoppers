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
/**
 * Activity where the user (as an owner) selects the Book to trade upon
 * creating a trade.
 * Contains a ListView of the user's inventory where the user chooses a
 * book by clicking on the item.
 * @see Inventory,  Account, BookListAdapter
 * @author Linda Zhang
 */

public class SelectFromOwnerInventoryActivity extends ActionBarActivity {

    private Account me;

    public ListView getMyInventory() {
        return inventoryList;
    }

    private ListView inventoryList;
    private SaveLoad mySaveLoad;
    private ArrayList<Book> myInventory; //need public books only.
    private ArrayAdapter<Book> adapter;
    int pos;
    Book selectedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_owner_inventory);
        inventoryList = (ListView)findViewById(R.id.owner_inventory_id);


        mySaveLoad = new SaveLoad();
        me = mySaveLoad.loadFromFile(getApplicationContext());
        myInventory = me.getInventory().getPublic(me.getInventory());
        adapter = new BookListAdapter(this,R.layout.book_inventory_list, myInventory);
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        inventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
                try {
                    Book book = me.getInventory().getBookByIndex(position);
                } catch (NegativeNumberException e) {
                    e.printStackTrace();
                } catch (TooLongException e) {
                    e.printStackTrace();
                }*/
                //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SelectFromOwnerInventoryActivity.this, CreateTradeScreen.class);
             //   if(getIntent().getStringExtra("flag").equals("search")){ //prevents the app from crashing from no flag extra
             //       intent.putExtra("flag","search");
             //   } else intent.putExtra("flag","friend");


                selectedBook=myInventory.get(position);
                pos=me.getInventory().getRealPosition(selectedBook.getUniquenum().getNumber());


                intent.putExtra("bPosition", pos);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_from_owner_inventory, menu);
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
        Intent intent = new Intent(SelectFromOwnerInventoryActivity.this, CreateTradeScreen.class);
        startActivity(intent);
        finish();
    }

}
