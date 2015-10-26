package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends ActionBarActivity {
    Button addBookButton;

    private ListView inventoryList;
    private ArrayAdapter<Book> adapter;
    private Inventory userInventory = new Inventory();
    private ArrayList<Book> inventory;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //------------------------------------------------------------------------
        Account testAccount = new Account();
        try {
            testAccount.setUsername("Jill");
            testAccount.setEmail("jlovas@ualberta.ca");
            testAccount.setCity("GP");
        } catch (NoSpacesException e) {
            e.printStackTrace();
        } catch (TooLongException e) {
            e.printStackTrace();
        } catch (IllegalEmailException e) {
            e.printStackTrace();
        }
        Category testCategory = null;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");
        //create book 1
        Book book1 = new Book(testImage);
        book1.setTitle("Eragon");
        book1.setAuthor("Christopher Paolini");
        try {
            book1.setQuantity("1");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        book1.setCategory(testCategory.HARDBACK);
        try {
            book1.setDescription("None");
        } catch (BlankFieldException e) {
            e.printStackTrace();
        }
        book1.setQuality(4);
        book1.setIsPrivate(false);
        //one book
        testAccount.getInventory().addBook(book1);

        //------------------------------------------------------------------------

        addBookButton= (Button)findViewById(R.id.addBookButton);

        inventory = testAccount.getInventory().getInventory();
        inventoryList = (ListView)findViewById(R.id.inventory);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addBIntent = new Intent(HomeScreen.this, AddBookScreen.class);
                startActivity(addBIntent);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        //------------------------------------------------------------------------
        Account testAccount = new Account();
        try {
            testAccount.setUsername("Jill");
            testAccount.setEmail("jlovas@ualberta.ca");
            testAccount.setCity("GP");
        } catch (NoSpacesException e) {
            e.printStackTrace();
        } catch (TooLongException e) {
            e.printStackTrace();
        } catch (IllegalEmailException e) {
            e.printStackTrace();
        }
        Category testCategory = null;
        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");
        //create book 1
        Book book1 = new Book(testImage);
        book1.setTitle("Eragon");
        book1.setAuthor("Christopher Paolini");
        try {
            book1.setQuantity("1");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        book1.setCategory(testCategory.HARDBACK);
        try {
            book1.setDescription("None");
        } catch (BlankFieldException e) {
            e.printStackTrace();
        }
        book1.setQuality(4);
        book1.setIsPrivate(false);
        //one book
        testAccount.getInventory().addBook(book1);

        //------------------------------------------------------------------------


        inventory = testAccount.getInventory().getInventory();
        adapter = new ArrayAdapter<Book>(this,R.layout.book_inventory_list, inventory);
        inventoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
