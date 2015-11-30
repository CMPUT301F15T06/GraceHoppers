package com.gracehoppers.jlovas.bookwrm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This activity displays the tutorial images, depending on where you enter from (previous
 * screen had buttons).
 *
 *
 * @author jlovas
 */
public class TutActivity extends ActionBarActivity {
    Button arrowButton;
    ImageView image;
    TextView title;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tut);

        arrowButton = (Button)findViewById(R.id.arrowButton);
        image = (ImageView)findViewById(R.id.image);
        title = (TextView)findViewById(R.id.howToFont);

        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("flag").equals("book")){
                    switch (count){
                        case(0):
                            image.setImageResource(R.drawable.add_book_2);
                            break;
                        case(1):
                            image.setImageResource(R.drawable.add_book_3);
                            break;
                        case(2):
                            image.setImageResource(R.drawable.add_book_4);
                            break;
                        case(3):
                            finish();
                    }
                    count++;
                }else if(getIntent().getStringExtra("flag").equals("friend")){
                    switch (count) {
                        case (0):
                            image.setImageResource(R.drawable.add_friend_2);
                            break;
                        case (1):
                            image.setImageResource(R.drawable.add_friend_3);
                            break;
                        case (2):
                            image.setImageResource(R.drawable.add_friend_4);
                            break;
                        case (3):
                            image.setImageResource(R.drawable.add_friend_5actually);
                            break;
                        case(4):
                            finish();
                    }
                    count++;
                }else if(getIntent().getStringExtra("flag").equals("trade")){


                    count++;
                }
            }
        });

    }


    public void onResume(){
        super.onResume();

        //the passed intent tells us which images to load
        if (getIntent().getStringExtra("flag").equals("book")) {
            //load the book tutorial images
            image.setImageResource(R.drawable.add_book_1);
            title.setText("How to Add a Book");

        }else if(getIntent().getStringExtra("flag").equals("friend")){
            //load the friend images
            image.setImageResource(R.drawable.friend_1);
            title.setText("How to Add a Friend");

        }else if(getIntent().getStringExtra("flag").equals("trade")){
            //load the trade images

        }
    }




}
