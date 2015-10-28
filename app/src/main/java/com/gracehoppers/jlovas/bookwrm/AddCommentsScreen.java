package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCommentsScreen extends ActionBarActivity {
    EditText comments;
    Button okCommentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comments_screen);

        comments = (EditText)findViewById(R.id.commentsEditText);
        okCommentsButton = (Button)findViewById(R.id.okCommentsButton);

        okCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send text back to previous activity

                //credit to Reto Meler for the result method of doing this:
                //Reto Meler, http://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android, Oct 28, 2015
                //http://stackoverflow.com/users/822/reto-meier
                Intent result = new Intent();
                result.putExtra("COMMENTS", comments.getText().toString());
                setResult(AddCommentsScreen.RESULT_OK, result);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_comments_screen, menu);
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
