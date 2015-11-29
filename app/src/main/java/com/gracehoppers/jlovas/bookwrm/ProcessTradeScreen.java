package com.gracehoppers.jlovas.bookwrm;

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
public class ProcessTradeScreen extends ActionBarActivity {

    public Trade trade; //= new Trade();
    public TradeHistory tradeHistory = new TradeHistory();

    public Trade trade1 = new Trade();
    private Account account1 = new Account();
    private Account account2 = new Account();
    Button accept;
    Button decline;
    public AlertDialog.Builder dialog;
    public AlertDialog.Builder dialog1;
    TextView bName;
    TextView bBook;
    TextView oBook;

    private Account account;
    SaveLoad saveLoad;
    TradeRequests traderequests;// = new TradeRequests();
    TradeRequestManager trmanager;// = new TradeRequestManager();
    TradeRequest tradeRequest;//= new TradeRequest();
    int position;
    AccountManager accountManager = new AccountManager();


    //public static String tradeId = "Trade ID";
    //private ProcessTradeManager processTradeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_trade_screen);

        //SET UP THE FALGS && GET THE TRADE FROM TRADEREQUESTS

        bName = (TextView)findViewById(R.id.bName);
        bBook = (TextView)findViewById(R.id.bBook);
        oBook = (TextView)findViewById(R.id.oBook);
        accept =(Button)findViewById(R.id.accept);
        decline = (Button)findViewById(R.id.decline);

        position = getIntent().getIntExtra("position", 0);

        saveLoad = new SaveLoad();
        account = saveLoad.loadFromFile(getApplicationContext());

        //Toast.makeText(getApplicationContext(), Toast.LENGTH_SHORT).show();

        Thread thread = new FindTRThread(account.getUsername());
        thread.start();

        /*
        setUp();
        tradeHistory= owner.getTradeHistory();
        try {
            trade= tradeHistory.getTradeByIndex(0);
        } catch (NegativeNumberException e) {
            Toast.makeText(getApplicationContext(), "Negative index number", Toast.LENGTH_SHORT).show();
        } catch (TooLongException e) {
            Toast.makeText(getApplicationContext(), "Index is longer than inventory size", Toast.LENGTH_SHORT).show();
        }
        borrower =trade.getBorrower();

        bName.setText("Borrower Name:\n" + borrower.getUsername());
        oBook.setText("Owner Book:\n"+trade.getOwnerBook().getTitle());

        String bookTitles ="";

        for(Book b: trade.getBorrowerBook()){
            bookTitles= bookTitles + b.getTitle() +"\n";
        }
        bBook.setText("Borrower Books:\n" + bookTitles );
*/
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set status of trade to accepted
                //trade.setAccepted(Boolean.TRUE);

                dialog = new AlertDialog.Builder(ProcessTradeScreen.this);

                dialog.setMessage("Continue the trade by sending email to borrower?");

                //continue to send email
                dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //send_email();
                        //email();
                        Toast toast = Toast.makeText(ProcessTradeScreen.this, "successfully send email", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

                dialog.setPositiveButton("No", null);

                dialog.create();
                dialog.show();


                Thread thread = new AcceptThread(account.getUsername());
                thread.start();

                //pop a dialog to promote owner to continue trade by sending email
/*

*/
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trade.setDeclined(Boolean.TRUE);



                //pop a dialog to promote owner to continue trade by sending email
                dialog1 = new AlertDialog.Builder(ProcessTradeScreen.this);
                dialog1.setMessage("Create a counter trade?");

                //continue to counterTrade
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

                Intent turnCounter = new Intent(ProcessTradeScreen.this, CounterTradeScreen.class);
                //Toast toast = Toast.makeText(ProcessTradeScreen.this, trade.getOwnerBook().getTitle(), Toast.LENGTH_LONG);
                //toast.show();
                //turnCounter.putExtra("declineOwnerBook",trade.getOwnerBook().getTitle());
                //turnCounter.putExtra("declineBorrower",trade.getOwnerBook().getTitle());
                startActivity(turnCounter);

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
            tradeRequest= new TradeRequest();
            trade = new Trade();
            try {
                traderequests = trmanager.findTradeRequests(user1);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "caught an exception :C", Toast.LENGTH_SHORT).show();
            }
            tradeRequest = traderequests.get(position);

            trade = tradeRequest.getTrade();
            //trade.setAccepted(Boolean.FALSE);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(),"trade"+tradeRequest.getTrade().getBorrower().getUsername(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"trade"+trade.getOwner().getUsername(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"trade"+trade.getBorrower().getUsername(), Toast.LENGTH_SHORT).show();

                    bName.setText("Borrower Name:\n" + trade.getBorrower().getUsername());

                    oBook.setText("Owner Book:\n"+trade.getOwnerBook().getTitle());

                    String bookTitles ="";

                    for(Book b: trade.getBorrowerBook()){
                        bookTitles= bookTitles + b.getTitle() +"\n";
                    }
                    bBook.setText("Borrower Books:\n" + bookTitles);
                }
            });
        }
    }
/*
    class AcceptThread extends Thread { //look for friend requests between x and y
        private String user1;

        public AcceptThread(String u1) {
            this.user1 = u1;
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
            trade.setAccepted(Boolean.TRUE);

            tradeRequest.acceptTradeRequest(trade.getBorrower(), trade.getOwner(), trade);

        }
    }
*/

    class AcceptThread extends Thread { //look for friend requests between x and y
        private String user1;

        public AcceptThread(String u1) {
            this.user1 = u1;
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
            trade.setAccepted(Boolean.TRUE);

            //
            account1 = accountManager.getAccount(trade.getOwner().getUsername());
            account2 = accountManager.getAccount(trade.getBorrower().getUsername());

            tradeRequest.acceptTradeRequest(trade.getBorrower(), trade.getOwner(), trade);
            /*
            tradeHistory = account1.getTradeHistory();
            tradeHistory.addTrade(trade);
            account1.setTradeHistory(tradeHistory);

            tradeHistory = account2.getTradeHistory();
            tradeHistory.addTrade(trade);
            account2.setTradeHistory(tradeHistory);
            */
            accountManager.updateAccount(account1);
            accountManager.updateAccount(account2);

            try{
                trade1 = account1.getTradeHistory().getTradeByIndex(5);
            }catch(NegativeNumberException e){

            }catch(TooLongException te){

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ProcessTradeScreen.this, "borrower: " + trade.getBorrower().getUsername(), Toast.LENGTH_SHORT).show();
                }
            });

            //trmanager.deleteTR(tradeRequest);
            //saveLoad.saveInFile(getApplicationContext(), account);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void email(){
        try {

            GMailSender sender = new GMailSender("naichaer05@gmail.com", "wxwh0505");

            sender.sendMail("This is Subject",
                    "This is Body",
                    "naichaer05@gmail.com",
                    "hong8@ualberta.ca");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }
/*
    public void setUp() {
        owner = new Account();
        borrower = new Account();
        try{
            owner.setUsername("Why");
            borrower.setUsername("GOGO");
        }catch(TooLongException te){
        }catch (NoSpacesException ne){
        }

        Book aBook = new Book();
        aBook.setTitle("goodBook");

        Book book1 = new Book();
        book1.setTitle("book1");
        Book book2 = new Book();
        book2.setTitle("book2");
        ArrayList<Book> bBooks = new ArrayList<Book>();
        bBooks.add(book1);
        bBooks.add(book2);


        Trade newTrade = new Trade();
        newTrade.setBorrower(borrower);
        newTrade.setOwner(owner);
        newTrade.setOwnerBook(aBook);
        newTrade.setBorrowerBook(bBooks);

        tradeHistory.addTrade(newTrade);
        owner.setTradeHistory(tradeHistory);


    }

    /*
    public void send_email(){
        String email = trade.getBorrower().getEmail();
        String subject = "Trade accepted by owner!";
        String content = "Trade information: \nBorrower Books :\n";
        for (Book b: trade.getBorrowerBook()){
            content= content+ b.getTitle()+"\n";
        }
        content= content+"Owner Book\n"+ trade.getOwnerBook().getTitle();

        Intent sendEmail= new Intent(Intent.ACTION_SEND, Uri.parse("mailTo"));
        sendEmail.setType("text/plain");
        //send recipent,content to email app
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendEmail.putExtra(Intent.EXTRA_TEXT, content);

        try{
            startActivity(sendEmail);

        }catch (android.content.ActivityNotFoundException ex){
            //if no supported app
            Toast.makeText(ProcessTradeScreen.this, "No email client found", Toast.LENGTH_SHORT).show();
        }
    }
    */

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
