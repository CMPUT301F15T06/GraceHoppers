package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends ActionBarActivity {

    /*
    Activity where the user signs up
     */

    EditText username;
    EditText email;
    EditText city;
    Button signupButton;
    private SaveLoad saveload= new SaveLoad();

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


                Account account = new Account(username.getText().toString(),email.getText().toString(),city.getText().toString());

                saveload.saveInFile(SignUpActivity.this, account);
/*
                Account account2 = new Account("butter","123@gmail.com","olds");
                account2=saveload.loadFromFile(SignUpActivity.this,account2); //should replace account2's info with account's


                Toast.makeText(getApplicationContext(), account2.getUsername(),
                        Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(), account2.getEmail(),
                        Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(), account2.getCity(),
                        Toast.LENGTH_SHORT).show();*/


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
}
