package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.content.Intent;
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

public class SignUpActivity extends ActionBarActivity {

    /*
    Activity where the user signs up
     */



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
                    saveload.saveInFile(SignUpActivity.this, account);

                    accounts.add(account); //for UI testing

                    //execute thread
                    Thread thread=new AddThread(account);
                    thread.start();

                    Toast.makeText(getApplicationContext(), "Account created",
                            Toast.LENGTH_SHORT).show();

                    Intent sIntent = new Intent(SignUpActivity.this, HomeScreen.class); //sends user to profile
                    startActivity(sIntent);
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
        }
    }
}


