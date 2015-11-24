package com.gracehoppers.jlovas.bookwrm;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * checks the server and shows you all of your friend requests
 * @author nlovas
 * @see FriendRequest
 * @see FriendRequestManager
 * @see FriendRequests
 */
public class ViewFRActivity extends ActionBarActivity {

    private ArrayAdapter<FriendRequest> adapter;
    private ListView FRlist;
    FriendRequests friendrequests;
    FriendRequestManager frmanager;
    SaveLoad saveload;
    Account account;
    AlertDialog SingleInfo;
    String sender;
    Account result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fr);

        FRlist = (ListView) findViewById(R.id.FRlistView);

        FRlist.setOnItemClickListener(new AdapterView.OnItemClickListener() { //referenced from CMPUT 301 lab
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sender = (String) adapter.getItem(position).getSender();
                openDialog(sender);
            }
        });

        saveload = new SaveLoad();
        account = saveload.loadFromFile(getApplicationContext());



        //check if you have any FR
        Thread thread = new FindFRThread(account.getUsername());
        thread.start();


        //test code -------------------------------------------
        // try {
        // Account result1 = new Account();
        //  result1.setUsername("johnEgbert");
        //  result1.setCity("sburb");
        //  result1.setEmail("gt@homestuck.com");
        //  account.getFriends().addFriend(result1);

          /*  Account result2 = new Account();
            result2.setUsername("jadeHarley");
            result2.setCity("sbfurb");
            result2.setEmail("gt@hffomestuck.com");
            //result.getFriends().addFriend(account);

            account.getFriends().unFriend(result2.getUsername());*/


        //BytesUtil bytesutil = new BytesUtil(); //put this into updateaccount after testing
        //  byte[] bytes;
        //   bytes=bytesutil.toByteArray(account.getFriends()); //friends is a byte array


        //Account test;
        //test = (Account)bytesutil.toObject(bytes); //turns it back into an account object
        //   Friends test = (Friends)bytesutil.toObject(bytes);

        //  Toast.makeText(getApplicationContext(), "test:" + test.getFriendByIndex(0).getUsername(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(), "test:" + test.getFriendByIndex(1).getUsername(), Toast.LENGTH_SHORT).show();


        // Log.e("before threads","work plz");
        // Thread uthread = new UpdateAThread(account); //FOR UPDATING THE ACCOUNT
        //uthread.start();
        //  Log.e("first thread done", "yes");

        //testing: lets get the account back and see if the friendslist is accurate
        // Thread yourthread = new SearchThread(account.getUsername());           FOR GETTING THE ACCOUNT
        // yourthread.start();
        //grabs your updated account and stores it inside result Account

/*
        }catch(AlreadyAddedException e){
            e.printStackTrace();
        }
    catch(NoSpacesException e){
            e.printStackTrace();
        }catch(TooLongException e){
            e.printStackTrace();
        }catch(IllegalEmailException e){
            e.printStackTrace();
        }catch(IOException e){
            Toast.makeText(getApplicationContext(), "IO exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();*/
        //}catch(ClassNotFoundException e){
        // Toast.makeText(getApplicationContext(), "Class not found", Toast.LENGTH_SHORT).show();
        // e.printStackTrace();
        //}
        //Thread u2thread = new UpdateAThread(result);
        // u2thread.start();
        // Log.e("second thread done","yes");

        //-------------------------------------------------------


    }


    protected void onStart() {
        super.onStart();

/*
        adapter = new FRAdapter(this,R.layout.frlist, account.getInventory().getInventory());
        FRlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
    }

    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_fr, menu);
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

    class FindFRThread extends Thread { //look for friend requests between x and y
        private String user1;


        public FindFRThread(String u1) {
            this.user1 = u1;

        }

        @Override
        public void run() {
            //FriendRequest result;
            frmanager = new FriendRequestManager();
            // Log.e("made it thread","made i 2 thread");
            //result=(frmanager.getFriendRequest(search.getUsername()));
            friendrequests = new FriendRequests();
            try {
                friendrequests = frmanager.findFriendRequests(user1);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "caught an exception :C", Toast.LENGTH_SHORT).show();
            }
            if (!friendrequests.isEmpty()) {
                runOnUiThread(UpdateFRAdapter);
                Log.e("true!!", "you have a FR for you");
            } else {
                runOnUiThread(UpdateFRAdapter);
                Log.e("false!!", "no FR for you");
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

    private Runnable UpdateFRAdapter = new Runnable() {
        @Override
        public void run() {
            adapter = new FRAdapter(getApplicationContext(), R.layout.frlist, friendrequests);
            FRlist.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    };


    class DeleteFRThread extends Thread {
        private String sender;
        private String receiver;

        public DeleteFRThread(String sender, String receiver) {
            this.sender = sender;
            this.receiver = receiver;

        }

        @Override
        public void run() {
            frmanager = new FriendRequestManager();
            frmanager.deleteFR(sender, receiver);

            //give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(DeleteFriendSuccess);

        }
    }


    public void openDialog(final String sender) {
        //asks the user if they want to accept this user as a friend
        //http://stackoverflow.com/questions/31151306/how-to-show-a-dialog-with-android-studio heloisasim 2015-31-10
        AlertDialog.Builder singleInfo = new AlertDialog.Builder(this);
        singleInfo.setTitle("Respond To Friend Request");
        singleInfo.setMessage("Add this user as a friend?");
        singleInfo.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Thread friendthread = new SearchThread(sender);
                friendthread.start();
                dialog.dismiss();

            }
        })
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                          Thread thread = new DeleteFRThread(sender, account.getUsername());
                          thread.start();
                        Toast.makeText(getApplicationContext(), "Friend Request Declined", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                });
        //AlertDialog SingleInfo=singleInfo.create();
        SingleInfo = singleInfo.create();
        SingleInfo.show();
    }

    private Runnable DeleteFriendSuccess = new Runnable() {
        @Override
        public void run() {
           // Toast.makeText(getApplicationContext(), "Friend Request Declined", Toast.LENGTH_SHORT).show();
            Thread thread = new FindFRThread(account.getUsername());
            thread.start();
            runOnUiThread(UpdateFRAdapter);
        }
    };




    class SearchThread extends Thread { //for getting the account so you can add eachother as friends!
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
                     runOnUiThread(AddFriends);
                } else {

                }

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

    }

    private Runnable AddFriends = new Runnable() {
        @Override
        public void run() {
            //test code --------------------------------------

/*
try {
    BytesUtil bytesutil = new BytesUtil();
    Friends tempfriends = (Friends)bytesutil.toObject(result.getFriendlistBytes());
    Toast.makeText(getApplicationContext(), tempfriends.getFriendByIndex(0).getUsername(), Toast.LENGTH_SHORT).show();
    result.setFriends(tempfriends);
    Toast.makeText(getApplicationContext(), result.getUsername(), Toast.LENGTH_SHORT).show();
    Toast.makeText(getApplicationContext(), result.getFriends().getFriendByIndex(0).getUsername(), Toast.LENGTH_SHORT).show();
}catch (ClassNotFoundException e){
    e.printStackTrace();
}catch(IOException e){
    e.printStackTrace();
}*/

            //end test code ---------------------------------

            Toast.makeText(getApplicationContext(), "Friend Request Accepted", Toast.LENGTH_SHORT).show();
            try { //add to eachother's friend lists
                result.getFriends().addFriend(account);
                account.getFriends().addFriend(result);
                //put this account into your friend list
                //put your account into their friend list
                //result.getFriends().clear();
                //account.getFriends().clear();
                Thread ythread = new UpdateAThread(account);
                ythread.start();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Thread fthread = new UpdateAThread(result);
                fthread.start();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Thread thread = new DeleteFRThread(result.getUsername(), account.getUsername());
                thread.start();
            //update server for you
            //update the server for them

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }catch(AlreadyAddedException e){
                Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
            }
            saveload.saveInFile(getApplicationContext(), account);
            //Toast.makeText(getApplicationContext(),"Friends has "+ account.getFriends().getSize()+ " people in it!", Toast.LENGTH_SHORT).show();
            runOnUiThread(UpdateFRAdapter);
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
