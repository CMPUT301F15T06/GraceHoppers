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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
/**
 * Activity where the user creates an account.
 * Contains editTexts username, email and city; and a signUp button.
 * Setters and Getters for these fields.
 * @see Account, AccountManager
 *
 * @Author Linda Zhang
 */
public class SignUpActivity extends ActionBarActivity {






    //---------------------------------------------------------------
    //For UI testing
    public Activity getActivity(){return this;}

    private ArrayList<Account> accounts = new ArrayList<Account>();
    public ArrayList<Account> getAccounts() {return accounts;}

    public EditText getCityText() {return city;}

    public EditText getUsernameText(){return username;}

    public EditText getEmailText(){return email;}

    public Button getSignButton(){return signupButton;}
    //----------------------------------------------------------------

    EditText username;
    EditText email;
    EditText city;
    Button signupButton;
    private SaveLoad saveload= new SaveLoad();
    private AccountManager accountManager=new AccountManager();
    private Runnable doFinishAdd=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Account created",
                    Toast.LENGTH_SHORT).show();

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText)findViewById(R.id.usernameeditText);
        email = (EditText)findViewById(R.id.emaileditText);
        city = (EditText)findViewById(R.id.CityeditText);
        signupButton = (Button)findViewById(R.id.SignUpbutton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upon button press, try to create a new account based on whats in the textviews



                try {
                    Account account = new Account();
                    account.setUsername(username.getText().toString());
                    account.setEmail(email.getText().toString());
                    account.setCity(city.getText().toString());
                    //saveload.saveInFile(SignUpActivity.this, account); //moving this line down for demoAccount for now


                    accounts.add(account); //for UI testing

                    SearchThread thread=new SearchThread(account);
                    thread.start();





                    //****Demo Account part, delete afterwards *************************************

                    Account demoAccount = new Account();
                    demoAccount.setUsername("DemoAccount");
                    demoAccount.setEmail("demo@hotmail.com");
                    demoAccount.setCity("Demoville");

                    Bitmap testImage = BitmapFactory.decodeFile("defaultbook.png");

                    Book book1 = new Book(testImage);
                    book1.setTitle("A Cool Guy Book");
                    book1.setAuthor("Joseph Campbell");
                    book1.setCategory(3);
                    book1.setQuality(4);
                    book1.setIsPrivate(false);
                    book1.setDescription("This book is pretty cool. Maybe too cool.");
                    //let these set to quantity default of 1 so i don't have to add extra exception catches for temporary code

                    Book book2 = new Book(testImage);
                    book2.setTitle("Undertale");
                    book2.setDescription("I'm watching Markiplier play this so I don't need to read it anymore");
                    book2.setAuthor("Not sure");
                    book2.setCategory(0);
                    book2.setQuality(5);
                    //let these set to quantity default of 1 so i don't have to add extra exception catches for temporary code

                    demoAccount.getInventory().addBook(book1);
                    demoAccount.getInventory().addBook(book2);

                    account.getAccounts().add(demoAccount);
                    saveload.saveInFile(SignUpActivity.this, account);
                    //******************************************************************************

                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), "All Fields must be filled",
                            Toast.LENGTH_SHORT).show();
                } catch (IllegalEmailException f) {
                    Toast.makeText(getApplicationContext(), "Must have valid email",
                            Toast.LENGTH_SHORT).show();
                } catch (NoSpacesException s) {
                    Toast.makeText(getApplicationContext(), "Fields cannot contain spaces",
                            Toast.LENGTH_SHORT).show();
                } catch (TooLongException w) {
                    Toast.makeText(getApplicationContext(), "A field is too long",
                            Toast.LENGTH_SHORT).show();
                }

                }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    class AddThread extends Thread {
        private Account account;
        public AddThread(Account account) {this.account=account;}

        @Override
        public void run(){
            accountManager.addAccount(account);

            //give some time to get updated info
            try{
                Thread.sleep(500);
            } catch(InterruptedException e){e.printStackTrace();}

            runOnUiThread(doFinishAdd);
            Intent sIntent = new Intent(SignUpActivity.this, HomeScreen.class); //sends user to profile
            sIntent.putExtra("username", account.getUsername());
            startActivity(sIntent);
        }
    }

    class SearchThread extends Thread {
        private Account search;

        public SearchThread(Account search) {
            this.search = search;
        }

        @Override
        public void run() {
            Account result;
            accountManager=new AccountManager();
            result=(accountManager.getAccount(search.getUsername()));

            try {
                if(result != null) {
                    //username exists
                    SignUpActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Username exists, please choose a new username", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else {
                    //add account
                    Thread thread=new AddThread(search);
                    thread.start();
                }

            }catch(RuntimeException e) {e.printStackTrace();}
        }

    }
}


