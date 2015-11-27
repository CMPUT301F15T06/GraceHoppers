package com.gracehoppers.jlovas.bookwrm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.DialogInterface;
import android.widget.Toast;

import java.io.IOException;

/**
 * checks the server and shows you all of your friend requests
 * @author ljuarezr (based on nlovas' ViewFRActivity)
 * @see TradeRequest
 * @see TradeRequestManager
 * @see TradeRequests
 */


public class ViewTradeRequest extends ActionBarActivity {

    private ArrayAdapter<TradeRequest> adapter;
    private ListView TRlist;
    TradeRequests traderequests;
    TradeRequestManager trmanager;
    SaveLoad saveload;
    Account account;
    AlertDialog SingleInfo;
    String tradeId;
    Account result;
    TradeRequest tradeRequest;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trade_request);

        TRlist = (ListView) findViewById(R.id.TRlistView);

        TRlist.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tradeId = (String) adapter.getItem(position).getSender();
                Intent intent = new Intent(context, ProcessTradeScreen.class);
                intent.putExtra(ProcessTradeScreen.tradeId, tradeId);

                startActivity(intent);
                //openDialog(sender);
                //Instead of opening Dialog, it should go to the proceedTrade Screen!!

                /*ProceedTrade should do
                 - Response(Accept/Decline/Counter) (just change Status)
                 - Delete Trade request from Server (use DeleteTRThread)
                 - Add Trade to Trade History in both accounts (use SearchThread, tradeHistory.addTrade )
                 - Update the account in the server (use UpdateAThread )
                */
            }
        });

        saveload = new SaveLoad();
        account = saveload.loadFromFile(getApplicationContext());



        //check if you have any TR
        Thread thread = new FindTRThread(account.getUsername());
        thread.start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_trade_request, menu);
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

    class FindTRThread extends Thread { //look for friend requests between x and y
        private String user1;


        public FindTRThread(String u1) {
            this.user1 = u1;

        }

        @Override
        public void run() {
            //TradeRequest result;
            trmanager = new TradeRequestManager();
            // Log.e("made it thread","made i 2 thread");
            //result=(trmanager.getFriendRequest(search.getUsername()));
            traderequests = new TradeRequests();
            try {
                traderequests = trmanager.findTradeRequests(user1);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "caught an exception :C", Toast.LENGTH_SHORT).show();
            }
            if (!traderequests.isEmpty()) {
                runOnUiThread(UpdateTRAdapter);
                Log.e("true!!", "you have a TR for you");
            } else {
                runOnUiThread(UpdateTRAdapter);
                Log.e("false!!", "no TR for you");
            }
/*
            try { //get rid of this part later/change it
                if(result != null) {
                    //a friendrequest exists
                    HomeScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "found a friend request", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else {
                    //add account
                    Thread thread=new AddThread(search);
                    thread.start();
                }

            }catch(RuntimeException e) {e.printStackTrace();}
        */
        }

    }

    private Runnable UpdateTRAdapter = new Runnable() {
        @Override
        public void run() {
            adapter = new TRAdapter(getApplicationContext(), R.layout.trlist, traderequests);
            TRlist.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    };


    class DeleteTRThread extends Thread {
        private String sender;
        private String receiver;
        private TradeRequest request;

        public DeleteTRThread(TradeRequest tradeRequest) {
            this.request = tradeRequest ;
        }

        @Override
        public void run() {
            trmanager = new TradeRequestManager();
            trmanager.deleteTR(tradeRequest);

            //give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(DeleteTradeSuccess);

        }
    }




    private Runnable DeleteTradeSuccess = new Runnable() {
        @Override
        public void run() {
            // Toast.makeText(getApplicationContext(), "Friend Request Declined", Toast.LENGTH_SHORT).show();
            Thread thread = new FindTRThread(account.getUsername());
            thread.start();
            runOnUiThread(UpdateTRAdapter);
        }
    };



    class SearchThread extends Thread { //for getting the account
        private String search;

        public SearchThread(String search) {
            this.search = search;
        }

        @Override
        public void run() {
            result = new Account();
            AccountManager accountManager = new AccountManager();
            result = (accountManager.getAccount(search));

            try {
                if (result != null) {
                    Log.e("found!", "found the account");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(ProceedTrade);
                } else {

                }

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

    }

    private Runnable ProceedTrade = new Runnable() {
        @Override
        public void run() {

        }
    };


    class UpdateAThread extends Thread { //for updating each account on the server
        private Account account;

        public UpdateAThread(Account account) {
            this.account = account;
        }

        @Override
        public void run() {

            AccountManager accountManager = new AccountManager();
            accountManager.updateAccount(account);
        }
    }

}

