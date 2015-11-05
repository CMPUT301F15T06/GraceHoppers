package com.gracehoppers.jlovas.bookwrm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProcessTradeScreen extends ActionBarActivity {

    private Account owner;
    private Account borrower;
    private Book ownerBook;  //can be 1
    private ArrayList<Book> borrowerBook; //can be 0 or more
    public Trade trade;
    public TradeHistory tradeHistory;

    Button accept;
    Button decline;
    public AlertDialog.Builder dialog;
    public AlertDialog.Builder dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_trade_screen);

        TextView bName = (TextView)findViewById(R.id.bName);
        TextView bBook = (TextView)findViewById(R.id.bBook);
        TextView oBook = (TextView)findViewById(R.id.oBook);
        accept =(Button)findViewById(R.id.accept);
        decline = (Button)findViewById(R.id.decline);

        //if owner chooses to accept email
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set status of trade to accepted
                trade.setAccepted(Boolean.TRUE);

                //pop a dialog to promote owner to continue trade by sending email

                dialog = new AlertDialog.Builder(ProcessTradeScreen.this);

                dialog.setMessage("Continue the trade by sending email to borrower?");

                //continue to send email
                dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        send_email();
                    }
                });

                dialog.setPositiveButton("No", null);

                dialog.create();
                dialog.show();
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade.setDeclined(Boolean.TRUE);
                //pop a dialog to promote owner to continue trade by sending email
                dialog1 = new AlertDialog.Builder(ProcessTradeScreen.this);
                dialog1.setMessage("Create a counter trade?");

                //continue to send email
                dialog1.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent turnCounter = new Intent(ProcessTradeScreen.this, CounterTradeScreen.class);
                        startActivity(turnCounter);
                    }
                });

                dialog1.setPositiveButton("No", null);

                dialog1.create();
                dialog1.show();
            }
        });

    }

    public void send_email(){
        String email = trade.getBorrower().getEmail();
        String subject = "Trade accepted by owner!";
        String content = "Trade information: \n Books provided by borrower :";
        for (Book b: borrowerBook){
            content= content+ b.getTitle()+"\n";
        }
        content= content+"Books requested \n"+ ownerBook.getTitle();

        Intent sendEmail= new Intent(Intent.ACTION_SEND, Uri.parse("mailTo"));
        sendEmail.setType("text/plain");
        //send recipent,content to email app
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        sendEmail.putExtra(Intent.EXTRA_SUBJECT,subject);
        sendEmail.putExtra(Intent.EXTRA_TEXT,content);

        try{
            startActivity(sendEmail);

        }catch (android.content.ActivityNotFoundException ex){
            //if no supported app
            Toast.makeText(ProcessTradeScreen.this, "No email client found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_process_trade_screen, menu);
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
