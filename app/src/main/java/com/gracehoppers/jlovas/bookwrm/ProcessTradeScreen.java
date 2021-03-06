package com.gracehoppers.jlovas.bookwrm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ProcessTradeScreen displays the information about a trade the user is having with a friend.
 * Accept and decline are offered to a user on a current trade, while the history displays
 * as a reciept of the past trade.
 *
 * @author Hong Wang
 */
public class ProcessTradeScreen extends Activity {

    public Trade trade; //= new Trade();
    public TradeHistory tradeHistory = new TradeHistory();

    public Trade trade1 = new Trade();
    private Account account1 = new Account();
    private Account account2 = new Account();
    Button accept;
    Button decline;
    //Button back;
    public AlertDialog.Builder dialog;
    public AlertDialog.Builder dialog1;
    TextView bName;
    TextView bBook;
    TextView oBook;

    public Account account;
    SaveLoad saveLoad;
    TradeRequests traderequests;// = new TradeRequests();
    TradeRequestManager trmanager;// = new TradeRequestManager();
    TradeRequest tradeRequest;//= new TradeRequest();
    int position;
    AccountManager accountManager = new AccountManager();
    String comments = "";
    Boolean sent = false;

    //public static String tradeId = "Trade ID";
    //private ProcessTradeManager processTradeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_trade_screen);

        //SET UP THE FALGS && GET THE TRADE FROM TRADEREQUESTS

        bName = (TextView) findViewById(R.id.bName);
        bBook = (TextView) findViewById(R.id.bBook);
        oBook = (TextView) findViewById(R.id.oBook);
        accept = (Button) findViewById(R.id.accept);
        decline = (Button) findViewById(R.id.decline);
        // back = (Button)findViewById(R.id.processBack);

        position = getIntent().getIntExtra("position", 0);

        saveLoad = new SaveLoad();
        account = saveLoad.loadFromFile(getApplicationContext());


        //Toast.makeText(getApplicationContext(), Toast.LENGTH_SHORT).show();

        Thread thread = new FindTRThread(account.getUsername());
        thread.start();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set status of trade to accepted
                trade.setAccepted(Boolean.TRUE);

                dialog = new AlertDialog.Builder(ProcessTradeScreen.this);
                final EditText input = new EditText(ProcessTradeScreen.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                dialog.setView(input);

                dialog.setMessage("Add comments for trade");

                //dialog,setContentView();

                //continue to send email
                dialog.setNegativeButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        comments = input.getText().toString();

                        Thread thread = new AcceptThread(account.getUsername(), comments, true);
                        thread.start();
                        send_email();

                    }
                });

                dialog.create();
                dialog.show();

                sent = true;


            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade.setDeclined(Boolean.TRUE);
                comments = "";
                Thread thread = new AcceptThread(account.getUsername(), comments, false);
                thread.start();


            }
        });
    }


    class FindTRThread extends Thread { //look for friend requests between x and y
        private String user1;

        public FindTRThread(String u1) {
            this.user1 = u1;

        }

        @Override
        public void run() {
            traderequests = new TradeRequests();
            trmanager = new TradeRequestManager();
            tradeRequest = new TradeRequest();
            trade = new Trade();
            try {
                traderequests = trmanager.findTradeRequests(user1);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "caught an exception :C", Toast.LENGTH_SHORT).show();
            }
            tradeRequest = traderequests.get(position);

            trade = tradeRequest.getTrade();
            //trade.setAccepted(Boolean.TRUE);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bName.setText("Borrower Name:\n" + trade.getBorrower().getUsername());

                    oBook.setText("Owner Book:\n" + trade.getOwnerBook().getTitle());

                    String bookTitles = "";

                    for (Book b : trade.getBorrowerBook()) {
                        bookTitles = bookTitles + b.getTitle() + "\n";
                    }
                    bBook.setText("Borrower Books:\n" + bookTitles);
                }
            });
        }
    }

    class AcceptThread extends Thread { //look for friend requests between x and y
        private String user1;
        private String coms;
        private Boolean bool;

        public AcceptThread(String u1, String com, Boolean b) {
            this.user1 = u1;
            this.coms = com;
            this.bool = b;
        }

        @Override
        public void run() {

            try {
                traderequests = trmanager.findTradeRequests(user1);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "caught an exception :C", Toast.LENGTH_SHORT).show();
            }

            tradeRequest = traderequests.get(position);
            trade = tradeRequest.getTrade();

            if (bool) {
                trade.setAccepted(Boolean.TRUE);
                trade.setOwnerComment(coms);
            } else {
                trade.setDeclined(Boolean.TRUE);
            }
            /*
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),coms, Toast.LENGTH_SHORT).show();
                }
            });*/

            account1 = accountManager.getAccount(trade.getOwner().getUsername());
            account2 = accountManager.getAccount(trade.getBorrower().getUsername());
            //tradeRequest.acceptTradeRequest(trade.getBorrower(), trade.getOwner(), trade);

            tradeHistory = account1.getTradeHistory();
            tradeHistory.addTrade(trade);

            account1.setTradeHistory(tradeHistory);
            accountManager.updateAccount(account1);

            tradeHistory = account2.getTradeHistory();
            tradeHistory.addTrade(trade);

            account2.setTradeHistory(tradeHistory);
            accountManager.updateAccount(account2);
            //

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ProcessTradeScreen.this, "trade has been processed", Toast.LENGTH_SHORT).show();
                }
            });

            trmanager.deleteTR(tradeRequest);
            saveLoad.saveInFile(getApplicationContext(), account);

        }
    }

    public void send_email() {

        String email = trade.getBorrower().getEmail();
        String email1 = trade.getOwner().getEmail();

        String subject = "Trade accepted by owner!";
        String content = "Trade information: \nBorrower Books :\n";
        for (Book b : trade.getBorrowerBook()) {
            content = content + b.getTitle() + "\n";
        }
        content = content + "Owner Book:\n" + trade.getOwnerBook().getTitle();

        content = content + "\nComments:\n" + comments;

        Intent sendEmail = new Intent(Intent.ACTION_SEND, Uri.parse("mailTo"));
        sendEmail.setType("text/plain");
        //send recipent,content to email app
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{email, email1});
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendEmail.putExtra(Intent.EXTRA_TEXT, content);

        try {
            startActivity(sendEmail);

        } catch (android.content.ActivityNotFoundException ex) {
            //if no supported app
            Toast.makeText(ProcessTradeScreen.this, "No email client found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

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

