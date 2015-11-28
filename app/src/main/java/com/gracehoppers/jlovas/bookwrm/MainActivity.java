package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 * The first screen the user sees when running the app.
 * Gives the user the option to either sign up or sign in with a username.
 *@see Account
 * @author jlovas
 */
public class MainActivity extends ActionBarActivity {
    Button logInButton;
    Button signUpButton;
    EditText usernameText;
    SaveLoad saveLoad;
    Account result;
    //ConnectionCheck connection;



    //for UI testing:-------------------------------------------
    public Button getSignUpButton(){
        return signUpButton;
    }
    public Button getLogInButton(){
        return logInButton;
    }
    public EditText getEditText(){
        return usernameText;
    }
//--------------------------------------------------------------

    /*
    String user;
    private EditText userNameText;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveLoad = new SaveLoad();

        //----------------Delete me when login server stuff is working -----------------------------

        //please be aware that because this makes a new account every time you log in (tempoarily),
        //the data from the previous log in will not be there - this is not an error!
        //if you don't want that happening just delete/comment out this stuff and sign up once, then
        //log in
   /*     final Account account = new Account();
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
        }*/

        //****Demo Account part, delete afterwards as well!*************************************
/*
        Account demoAccount = new Account();
        try {
            demoAccount.setUsername("DemoAccount");
            demoAccount.setEmail("demo@hotmail.com");
            demoAccount.setCity("Demoville");
        }catch(NoSpacesException e){

        }catch(TooLongException e){

        }catch(IllegalEmailException e){

        }

        Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

        Book book1 = new Book();
        book1.setTitle("A Cool Guy Book");
        book1.setAuthor("Joseph Campbell");
        book1.setCategory(3);
        book1.setQuality(4);
        book1.setIsPrivate(true);
        book1.setDescription("This book is pretty cool. Maybe too cool.");
        //let these set to quantity default of 1 so i don't have to add extra exception catches for temporary code

        Book book2 = new Book();
        book2.setTitle("Undertale");
        book2.setDescription("I'm watching Markiplier play this so I don't need to read it anymore");
        book2.setAuthor("Not sure");
        book2.setCategory(0);
        book2.setQuality(5);
        //let these set to quantity default of 1 so i don't have to add extra exception catches for temporary code

        demoAccount.getInventory().addBook(book1);
        demoAccount.getInventory().addBook(book2);

        account.getAccounts().add(demoAccount);
        //******************************************************************************





        saveLoad.saveInFile(getApplicationContext(), account);*/
        //------------------------------------------------------------------------------------------



        logInButton = (Button)findViewById(R.id.logInButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        usernameText = (EditText)findViewById(R.id.usernameText);



            logInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConnectionCheck connection=new ConnectionCheck();

                    //TODO: put the new account into the Gson or whatever we store it with so we can pull it out on further screens!
                    if(connection.checkConnection(MainActivity.this)==true) {
                        usernameText = (EditText) findViewById(R.id.usernameText);
                        final String username = usernameText.getText().toString();

                            try {
                                SearchThread thread = new SearchThread(username);
                                thread.start();
                            }catch(BlankFieldException e){
                                Toast.makeText(getApplicationContext(),"Please enter a valid username", Toast.LENGTH_SHORT).show();
                            }
                        }


                }
            });

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sIntent = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(sIntent);
                }
            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        private String search;

        public SearchThread(String search) throws BlankFieldException {
            if(search.equals("")) throw new BlankFieldException();
            else this.search = search;
        }

        @Override
        public void run() {
            result=new Account();
            AccountManager accountManager=new AccountManager();
            result=(accountManager.getAccount(search));

            try {
                if(result != null) {
                    saveLoad = new SaveLoad();
                    saveLoad.saveInFile(getApplicationContext(),result);

                    Intent lIntent = new Intent(MainActivity.this, HomeScreen.class);
                    lIntent.putExtra("username", search);
                    startActivity(lIntent);
                }


                else {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Username does not exist, please sign up or enter the correct username", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }catch(RuntimeException e) {e.printStackTrace();}
        }

            //notifyUpdated();
        //}

    }
}
