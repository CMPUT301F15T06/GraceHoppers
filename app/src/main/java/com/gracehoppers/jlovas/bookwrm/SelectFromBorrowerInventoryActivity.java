package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Activity where the user (as an owner) selects the Book to trade upon
 * creating a trade (from the borrower's inventory)
 * Contains a ListView of the borrower's inventory where the user chooses a
 * book by clicking on the item.
 * @see Inventory,  Account, BookListAdapter
 * @author Linda Zhang
 */
public class SelectFromBorrowerInventoryActivity extends ActionBarActivity {


    //Need to get the Friend's inventory from the server!!!


private Account friend;
private ListView inventoryList;
private SaveLoad saveLoad;



private ArrayList<Book> friendInventory;
private ArrayAdapter<Book> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_borrower_inventory);
        inventoryList = (ListView)findViewById(R.id.borrower_Inventory_id);

        saveLoad = new SaveLoad();
        friend = saveLoad.loadFriendFromFile(getApplicationContext());
        friendInventory = friend.getInventory().getInventory();
        adapter = new BookListAdapter(this,R.layout.friend_inventory_list, friendInventory);
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        inventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Book book = friend.getInventory().getBookByIndex(position);
                    Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                } catch (NegativeNumberException e) {
                    e.printStackTrace();
                } catch (TooLongException e) {
                    e.printStackTrace();
                }


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
