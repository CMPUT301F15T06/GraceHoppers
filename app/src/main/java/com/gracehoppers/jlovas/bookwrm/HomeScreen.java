package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeScreen extends Activity {
    Button addBookButton;

    private ListView inventoryList;
    private ArrayAdapter<Book> adapter;
    private Inventory userInventory = new Inventory();
    private ArrayList<Book> inventory;
    Account account; //= new Account();
    private SaveLoad saveload= new SaveLoad();
    String username;

    //For UI testing -----------------------------------------
    //private ArrayList<Book> inventory = new ArrayList<Book>();
    public ArrayList<Book> getInventory(){return inventory;}
    public ListView getInventoryList() {return inventoryList;}
    //-------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent i=getIntent();
        Bundle Username=i.getExtras();
        username=Username.getString("username");

        //-------------------------DELTE ONCE SAVING AND LOADING ACCOUNT WORKS-----------------------------------------------
/*
        try {
            account.setUsername("Jill");
            account.setEmail("jlovas@ualberta.ca");
            account.setCity("GP");
        } catch (NoSpacesException e) {
            e.printStackTrace();
        } catch (TooLongException e) {
            e.printStackTrace();
        } catch (IllegalEmailException e) {
            e.printStackTrace();
        }

        int testCategory =1;
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
        book1.setCategory(testCategory);

            book1.setDescription("None");

        book1.setQuality(4);
        book1.setIsPrivate(false);
        //one book
        account.getInventory().addBook(book1);

        Book book2 = new Book(testImage);
        book2.setTitle("Tokyo Ghoul");
        book2.setAuthor("Not sure");

        try {
            book2.setQuantity("1");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }

        book2.setCategory(testCategory);

            book2.setDescription("None");

        book2.setQuality(4);
        book2.setIsPrivate(false);

        //add second book
        account.getInventory().addBook(book2);


*/
        //------------------------------------------------------------------------

        addBookButton= (Button)findViewById(R.id.addBookButton);

        account = saveload.loadFromFile(getApplicationContext());



        inventory = account.getInventory().getInventory();
        inventoryList = (ListView)findViewById(R.id.inventory1);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addBIntent = new Intent(HomeScreen.this, AddBookScreen.class);
                startActivity(addBIntent);
            }
        });

        inventoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Book book = account.getInventory().getBookByIndex(position);
                    //Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                } catch (NegativeNumberException e) {
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
                } catch (TooLongException e) {
                    //these will only trip if its a bug on our end, not user's fault
                    Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(HomeScreen.this, ViewBookActivity.class);
                intent.putExtra("listPosition", position);
                intent.putExtra("flag", "Homescreen");
                startActivity(intent);
            }
        });

        Button profile = (Button)findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnProfile = new Intent(HomeScreen.this, ProfileScreen.class);
                //turnProfile.putExtra(ProfileScreen.Username,username);
                turnProfile.putExtra("Username",username);
                startActivity(turnProfile);
            }
        });

        Button friend = (Button) findViewById(R.id.friendsButton);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnFriend = new Intent(HomeScreen.this, FriendsScreen.class);
                startActivity(turnFriend);
            }
        });

        Button trade = (Button) findViewById(R.id.tradeButton);
        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent turnTrade = new Intent(HomeScreen.this, CreateTradeScreen.class);
                startActivity(turnTrade);
            }
        });

    }



    @Override
    protected void onStart(){
        super.onStart();

        //-------------------------DELTE ONCE SAVING AND LOADING ACCOUNT WORKS-----------------------------------------------
        /*
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
        int testCategory =1;
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
        book1.setCategory(testCategory);

            book1.setDescription("None");

        book1.setQuality(4);
        book1.setIsPrivate(false);
        //one book
        testAccount.getInventory().addBook(book1);

        Book book2 = new Book(testImage);
        book2.setTitle("Tokyo Ghoul");
        book2.setAuthor("Not sure");

        try {
            book2.setQuantity("1");
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }

        book2.setCategory(testCategory);

            book2.setDescription("None");

        book2.setQuality(4);
        book2.setIsPrivate(false);

        //add second book
        testAccount.getInventory().addBook(book2);

        saveload.saveInFile(HomeScreen.this,testAccount); //delete this once server is working

        //------------------------------------------------------------------------
*/

        //inventory = account.getInventory().getInventory();
        //adapter = new BookListAdapter(this,R.layout.book_inventory_list, inventory);
        //inventoryList.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    //onResume() knowledge of using this coems from class presentation of activity lifecycle
    //Abram Hindle, CMPUT301 Fall 2015, September, University of Alberta
    protected void onResume(){
        super.onResume();
        account = saveload.loadFromFile(getApplicationContext());
        inventory = account.getInventory().getInventory();
        adapter = new BookListAdapter(this,R.layout.book_inventory_list, inventory);
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
