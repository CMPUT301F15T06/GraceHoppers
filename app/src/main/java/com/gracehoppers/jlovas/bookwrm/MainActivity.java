package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    Button logInButton;
    Button signUpButton;
    EditText usernameText;
    SaveLoad saveLoad;



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
        Account account = new Account();
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

        saveLoad.saveInFile(getApplicationContext(), account);
        //------------------------------------------------------------------------------------------


        logInButton = (Button)findViewById(R.id.logInButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add conditions for this to work here!!
                Toast.makeText(getApplicationContext(), "Login not set up quite yet, I go through regardless for testing book purposes! c:", Toast.LENGTH_SHORT).show();

                //search through database
                //if username exists:

                /*
                userNameText = (EditText) findViewById(R.id.usernameText);
                user = userNameText.getText().toString();
                */

                //TODO: put the new account into the Gson or whatever we store it with so we can pull it out on further screens!

                usernameText = (EditText)findViewById(R.id.usernameText);
                final String username=usernameText.getText().toString();
                Intent lIntent = new Intent(MainActivity.this, HomeScreen.class);
                lIntent.putExtra("username",username);
                startActivity(lIntent);

                //else;
                //Toast.makeText(getApplicationContext(), "username does not exist, please sign up or enter the correct username.", Toast.LENGTH_LONG).show();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sIntent = new Intent(MainActivity.this, SignUpActivity.class); //change me to where sign up should go!
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
}
