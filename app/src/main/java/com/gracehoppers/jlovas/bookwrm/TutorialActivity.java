package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This activity displays buttons for the tutorial to be displayed.
 *
 * @author Jillian Lovas
 */
public class TutorialActivity extends ActionBarActivity {
    Button howToBook;
    Button howToFriend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        howToBook = (Button)findViewById(R.id.howToBookButton);
        howToFriend = (Button)findViewById(R.id.howToFriendButton);


        howToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialActivity.this, TutActivity.class);
                intent.putExtra("flag", "book");
                startActivity(intent);

            }
        });

        howToFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialActivity.this, TutActivity.class);
                intent.putExtra("flag", "friend");
                startActivity(intent);

            }
        });


    }


}
