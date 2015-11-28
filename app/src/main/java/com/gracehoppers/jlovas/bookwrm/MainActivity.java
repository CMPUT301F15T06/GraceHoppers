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
 * @see Account
 * @author jlovas, Linda, nlovas
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


        logInButton = (Button)findViewById(R.id.logInButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        usernameText = (EditText)findViewById(R.id.usernameText);



            logInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //add conditions for this to work here!!
                    //Toast.makeText(getApplicationContext(), "Login not set up quite yet, I go through regardless for testing book purposes! c:", Toast.LENGTH_SHORT).show();
                    ConnectionCheck connection=new ConnectionCheck();

                    //TODO: put the new account into the Gson or whatever we store it with so we can pull it out on further screens!
                    if(connection.checkConnection(MainActivity.this)==true) {
                        usernameText = (EditText) findViewById(R.id.usernameText);
                        final String username = usernameText.getText().toString();


                        SearchThread thread = new SearchThread(username);
                        thread.start();
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

    class SearchThread extends Thread {
        private String search;

        public SearchThread(String search) {
            this.search = search;
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
