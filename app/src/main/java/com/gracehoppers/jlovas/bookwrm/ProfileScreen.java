package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileScreen extends ActionBarActivity {

    EditText editname;
    EditText editemail;
    EditText editcity;
    TextView name;
    TextView email;
    TextView city;
    Button edit;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        editname =(EditText) findViewById(R.id.editname);
        editemail = (EditText) findViewById(R.id.editemail);
        editcity = (EditText) findViewById(R.id.editcity);
        confirm = (Button) findViewById(R.id.confirm_edit);
        final ArrayList<View> editList = new ArrayList<View>((Arrays.asList(editname,editemail,editcity,confirm)));
        set_invisble(editList);

        name =(TextView) findViewById(R.id.originalname);
        email =(TextView) findViewById(R.id.originalemail);
        city = (TextView) findViewById(R.id.originalcity);
        edit = (Button) findViewById(R.id.editprofile);
        final ArrayList<View> originalList = new ArrayList<View>((Arrays.asList(name,city,email,edit)));


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_invisble(editList);
                set_visible(originalList);
            }
        });
    }

    public void set_invisble(ArrayList<View> views){
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
}
