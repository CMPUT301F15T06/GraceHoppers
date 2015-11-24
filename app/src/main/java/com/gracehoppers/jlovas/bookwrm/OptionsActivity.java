package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * @author jlovas
 */
public class OptionsActivity extends ActionBarActivity {
    CheckBox checkBox;
    Button tutorialButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        checkBox = (CheckBox)findViewById(R.id.pDcheckbox);
        tutorialButton = (Button)findViewById(R.id.tutButton);

        //tutorial button to launch tutorialActivity
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, TutorialActivity.class);
                startActivity(intent);
                }
        });


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when checkbox is unchecked, we are disabling photo downloads
                if(!checkBox.isChecked()){
                    Toast.makeText(getApplicationContext(), "Photo downloads are now disabled", Toast.LENGTH_SHORT).show();
                }

                else if(checkBox.isChecked()){
                    Toast.makeText(getApplicationContext(), "Photo downloads are now enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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
