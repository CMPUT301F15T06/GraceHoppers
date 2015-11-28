package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Allows the user to search the inventories of their friends by textual query OR category
 * @author nlovas
 *
 */
public class SearchFriendInventories extends ActionBarActivity {

    ListView searchedBooksList;
    EditText userQuery;
    Button searchButton;
    Spinner categorySelect;
    Button switchButton;
    Accounts friendlist;
    Account account;
    SaveLoad saveload;
    Inventory pubinventory;
    Inventory searchresult;
    private ArrayAdapter<Book> adapter;
    String search;
    int spinValue;
    Account owner;
    int bookposition;
    Book selectedBook;

    //1: get all friend accounts on create (using nested queries has been disabled so the inefficient way is the only way)
    //2: get all of their PUBLIC books into one list on create
    //3: query the books and put them into the adapter!!!
    //4: when the user clicks on a book, the get to view the details (retrieve the account)
    //5: if the user wants to trade, click the trade button
    //6: load createtrade screen with this item already in it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend_inventories);

        searchedBooksList = (ListView)findViewById(R.id.listView);
        userQuery = (EditText)findViewById(R.id.queryText);
        searchButton = (Button)findViewById(R.id.searchbutton);
        categorySelect = (Spinner)findViewById(R.id.searchspinner);
        switchButton = (Button)findViewById(R.id.switchbutton);



        //searching by textual query will be the default, hide the spinner of categories
        categorySelect.setVisibility(categorySelect.INVISIBLE);

        saveload = new SaveLoad();
        account = saveload.loadFromFile(getApplicationContext());
        friendlist = new Accounts();


        if(account.getFriends().getSize()==0){ //dont allow people to search if they dont have anyone to search
            Toast.makeText(getApplicationContext(), "You don't have any friends! Go make some!", Toast.LENGTH_LONG).show();
        } else {

            try {
                 for (int i = 0; i < account.getFriends().getSize(); i++) { //have to run through a list because nested queries are disabled. Obviously going to lag the more friends you have.
                Thread thread = new GetAccountThread(account.getFriends().getFriendByIndex(i));
                thread.start();
                     if(i==account.getFriends().getSize()-1) {
                         thread.join(); //wait for the thread to end before proceeding
                     }
                 }
                //now we have all of the accounts that the user is friends with. Now let's get all of their public books
                getAllBooks();





            } catch (NegativeNumberException e) {

            } catch (TooLongException e) {

            }catch(InterruptedException e){
                Toast.makeText(getApplicationContext(), "interrupted", Toast.LENGTH_SHORT).show();
            }


            switchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //toggle between making the spiner or edittext visible
                    if (categorySelect.getVisibility() == view.INVISIBLE) {
                        userQuery.setVisibility(view.INVISIBLE);
                        categorySelect.setVisibility(view.VISIBLE);
                        switchButton.setText("Search by Title");
                    } else {
                        userQuery.setVisibility(view.VISIBLE);
                        categorySelect.setVisibility(view.INVISIBLE);
                        switchButton.setText("Search by Category");
                    }

                }
            });

            //spinner/dropdown clicklistener -automatically set to 0/none on creation
            //credit to Mike James for this code/note to use this way of doing this:
            //Mike James, http://www.i-programmer.info/programming/android/6388-android-adventures-spinners-and-pickers.html?start=1, Oct 28, 2015
            AdapterView.OnItemSelectedListener onSpinner = new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(
                        AdapterView<?> parent,
                        View view,
                        int position,
                        long id) {
                    //assign to 'global' for sending to Book on ok button press
                    spinValue = position;
                }

                @Override
                public void onNothingSelected(
                        AdapterView<?>  parent) {
                }
            };

            categorySelect.setOnItemSelectedListener(onSpinner);


            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (categorySelect.getVisibility() == view.VISIBLE) {
                        //do a search by category
                        doCategorySearch();
                        showBooks();
                    }

                    if (categorySelect.getVisibility() == view.INVISIBLE) {
                        //do a search title by textual query
                        search = userQuery.getText().toString();
                        doTextualQuery(search);
                        showBooks();

                    }


                }
            });

            searchedBooksList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    try {
                        Intent intent = new Intent(SearchFriendInventories.this, ViewBookActivity.class);
                        owner = getOwner(position); //get the owner account to pass to the next intent
                        selectedBook = searchresult.getBookByIndex(position); //get the book
                        bookposition = getActualIndex(selectedBook, owner); //get the actual position in the account to pass to the next intent
                        Toast.makeText(getApplicationContext(), "" + owner.getUsername(), Toast.LENGTH_SHORT).show();
                        saveload.saveFriendInFile(getApplicationContext(),owner);
                        intent.putExtra("listPosition", bookposition);
                         intent.putExtra("flag", "Search");
                         startActivity(intent);
                    }catch(NegativeNumberException e){

                    }catch(TooLongException e){

                    }




                }
            });

        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_friend_inventories, menu);
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


    class GetAccountThread extends Thread { //for updating each account on the server
        private String username;


        public GetAccountThread(String username) {
            this.username = username;

        }

        @Override
        public void run() {

            AccountManager accountManager = new AccountManager();
            Account result;
            result = accountManager.getAccount(username);

            if(!(result==null)){

                friendlist.add(result);



            }


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void getAllBooks() {
        //get all of the public books of all of your friends
        Book tmpB;
        Account tmpA;
        pubinventory = new Inventory();
        try {
            for (int i = 0; i < friendlist.size(); i++) { //cycles through each friend
                tmpA = friendlist.get(i);
                for (int j = 0; j < tmpA.getInventory().getSize(); j++) { //cycles through each inventory
                       tmpB = tmpA.getInventory().getBookByIndex(j);
                        if (!tmpB.isPrivate()) {
                            pubinventory.addBook(tmpB);
                        }

                }

            }



        } catch (TooLongException e) {

        } catch (NegativeNumberException e) {

        }
    }

    public void showBooks(){
        //update the adapter with the relevant books
        adapter = new BookListAdapter(this,R.layout.book_inventory_list, searchresult.getInventory()); //change it to the other list!!!!!!!!!
        searchedBooksList.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    public void doTextualQuery(String src){
        //find all books with a match to the search term
        Book tmpB;
        searchresult = new Inventory();
        try {
            for (int i = 0; i < pubinventory.getSize(); i++) {
                tmpB = pubinventory.getBookByIndex(i);
                if (tmpB.getTitle().toLowerCase().contains(src.toLowerCase())){ //case insensitive
                    searchresult.addBook(tmpB);
                }
            }
        }catch(NegativeNumberException e){

        }catch(TooLongException e){

        }

    }


public void doCategorySearch(){
    //searches all public books by given category
    Book tmpB;
    searchresult = new Inventory();
    try {
        for (int i = 0; i < pubinventory.getSize(); i++) {
            tmpB = pubinventory.getBookByIndex(i);
            if (tmpB.getCategoryNumber()==spinValue){
                searchresult.addBook(tmpB);
            }
        }
    }catch(NegativeNumberException e){

    }catch(TooLongException e){

    }

}

   public Account getOwner(int position){
        //get the account that owns this book
       Book tmpB;
       int tmpnum;
       Account tmpA;
       Book tempBook;
       try {
          tmpB=searchresult.getBookByIndex(position);
           tmpnum=tmpB.getUniquenum().getNumber();


           for(int i=0;i<friendlist.size();i++){ //cycle through each friend
               tmpA = friendlist.get(i);
               for(int j=0;j<tmpA.getInventory().getSize();j++){ //cycle through each book, looking for a match
                   tempBook = tmpA.getInventory().getBookByIndex(j);
                   if(tmpnum==tempBook.getUniquenum().getNumber()){
                       return friendlist.get(i); //return the owner of the book
                   }

               }

           }


       }catch(NegativeNumberException e){

       }catch(TooLongException e){

       }

       return null;

    }

    public int getActualIndex(Book b, Account a){
//find the position of this book in the account's inventory
        int uninum = b.getUniquenum().getNumber();
        try {
            for (int i = 0; i < a.getInventory().getSize(); i++) {

                if (a.getInventory().getBookByIndex(i).getUniquenum().getNumber() == uninum) {
                    return i;
                }

            }
        }catch(NegativeNumberException e){

        }catch(TooLongException e){

        }
        return 0; //wouldnt be called
    }


}
