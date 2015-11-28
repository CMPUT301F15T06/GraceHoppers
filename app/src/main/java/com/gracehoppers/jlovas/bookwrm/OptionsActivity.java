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
 * OptionsActivity is for setting the photo downloads as enabled/disabled.
 * The help guide for the app can also be found here.
 *
 * @author jlovas
 */
public class OptionsActivity extends ActionBarActivity {
    CheckBox checkBox;
    Button tutorialButton;
    PhotoDownloads pD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        checkBox = (CheckBox)findViewById(R.id.pDcheckbox);
        tutorialButton = (Button)findViewById(R.id.tutButton);

        pD = (PhotoDownloads)getApplicationContext();

        //tutorial button to launch tutorialActivity
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this, TutorialActivity.class);
                startActivity(intent);
                }
        });

        if(pD.getEnabled()) {
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when checkbox is unchecked, we are disabling photo downloads
                if(!checkBox.isChecked()){
                    pD.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Photo downloads are now disabled", Toast.LENGTH_SHORT).show();
                }

                else if(checkBox.isChecked()){
                    pD.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Photo downloads are now enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
