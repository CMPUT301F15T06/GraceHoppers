package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileScreen extends ActionBarActivity {

    EditText editemail;
    EditText editcity;
    TextView name;
    TextView email;
    TextView city;
    Button edit;
    Button confirm;
    String newemail;
    String newcity;
    Account account;
    SaveLoad saveload;
    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        editemail = (EditText) findViewById(R.id.editemail);
        editcity = (EditText) findViewById(R.id.editcity);
        confirm = (Button) findViewById(R.id.confirm_edit);
        final ArrayList<View> editList = new ArrayList<View>((Arrays.asList(editemail,editcity,confirm)));
        set_invisible(editList);

        name =(TextView) findViewById(R.id.originalname);
        email =(TextView) findViewById(R.id.originalemail);
        city = (TextView) findViewById(R.id.originalcity);
        edit = (Button) findViewById(R.id.editprofile);
        final ArrayList<View> originalList = new ArrayList<View>((Arrays.asList(name,city,email,edit)));


        name.setText(account.getUsername());
        email.setText(account.getEmail());
        city.setText(account.getCity());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_visible(editList);
                set_invisible(originalList);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {

                try {
                    newemail = editemail.getText().toString();
                    newcity = editcity.getText().toString();

                    account.setEmail(newemail);
                    account.setCity(newcity);
                    saveload.saveInFile(ProfileScreen.this, account);


                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    Intent sIntent = new Intent(ProfileScreen.this, ProfileScreen.class); //sends user to profile
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

    public void set_invisible(ArrayList<View> views){
        for(View v:views){
            v.setVisibility(View.INVISIBLE);
        }
    }

    public void set_visible(ArrayList<View> views){
        for(View v: views){
            v.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_screen, menu);
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

    class getThread extends Thread {
        private String username;
        public getThread(String username){this.username=username;}


    }
}
