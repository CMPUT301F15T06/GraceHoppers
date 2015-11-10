package com.gracehoppers.jlovas.bookwrm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
/**Activity representing the view of a user account details.
 * Allows for editing funtionality.
 * Contains a TextView of the name, email, city of a user; as well as
 * edit/confirm buttons and the corresponding EditTexts for name, email and
 * user if editing. Getters and Setters for these.
 * @see Account, AccountManager, SaveLoad.
 * @author Linda Zhang
 */

public class ProfileScreen extends ActionBarActivity {



    //public static String Username="Username";
    EditText editemail;
    EditText editcity;
    TextView name;
    TextView email;
    TextView city;
    Button edit;
    Button confirm;
    String newemail;
    String newcity;
    Account account;
    SaveLoad saveload;
    AccountManager accountManager;
<<<<<<< HEAD
    ArrayList<View> editList;
    ArrayList<View> originalList;


    private Runnable doUpdateGUIDetails2=new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
            Intent sIntent = new Intent(ProfileScreen.this, ProfileScreen.class); //sends user to profile
            sIntent.putExtra("Username",account.getUsername());
            startActivity(sIntent);
        }
    };

    private Runnable doUpdateGUIDetails=new Runnable() {
        public void run() {

            /*name =(TextView) findViewById(R.id.originalname);
=======
    public ArrayList<View> editList;
    public ArrayList<View> originalList;

    /**
     * This is the activity for the mean functionality of answering a question. This
     * activity will be acted when the "Add Answer" button in the
     * QuestionDetailActivity.java is clicked.
     *
     * @author Hong Wang
     * @author Di Zhang
     *
     */

    private Runnable doUpdateGUIDetails=new Runnable() {
        public void run() {
            
            /*
            name =(TextView) findViewById(R.id.originalname);
>>>>>>> 2961a6e90e7b32fc2245f922dbacc30f99bc20c9
            editemail = (EditText) findViewById(R.id.editemail);
            editcity = (EditText) findViewById(R.id.editcity);
            confirm = (Button) findViewById(R.id.confirm_edit);
            editList = new ArrayList<View>((Arrays.asList(editemail, editcity, confirm)));
            set_invisible(editList);

            //name =(TextView) findViewById(R.id.originalname);
            email =(TextView) findViewById(R.id.originalemail);
            city = (TextView) findViewById(R.id.originalcity);
            edit = (Button) findViewById(R.id.editprofile);
            originalList = new ArrayList<View>((Arrays.asList(city,email,edit)));
            set_visible(originalList);
<<<<<<< HEAD
*/
=======

            */
            saveload = new SaveLoad();
            account = saveload.loadFromFile(getApplicationContext());
>>>>>>> 2961a6e90e7b32fc2245f922dbacc30f99bc20c9

            name.setText(account.getUsername());
            email.setText(account.getEmail());
            city.setText(account.getCity());

            edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_visible(editList);
                set_invisible(originalList);
            }
            });

            confirm.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View view) {
                    try {
<<<<<<< HEAD
=======
                        //Toast.makeText(getApplicationContext(), "Profile test 1", Toast.LENGTH_SHORT).show();
>>>>>>> 2961a6e90e7b32fc2245f922dbacc30f99bc20c9
                        editemail = (EditText) findViewById(R.id.editemail);
                        editcity = (EditText) findViewById(R.id.editcity);

                        newemail = editemail.getText().toString();
                        newcity = editcity.getText().toString();

                        account.setEmail(newemail);
                        account.setCity(newcity);
                        //saveload.saveInFile(ProfileScreen.this, account);

<<<<<<< HEAD
                        //accountManager.updateAccount(account);
                        Thread thread=new updateThread(account);
                        thread.start();


                        /*Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        Intent sIntent = new Intent(ProfileScreen.this, ProfileScreen.class); //sends user to profile
                        sIntent.putExtra("Username",account.getUsername());
                        startActivity(sIntent);*/
=======
                        //accountManager.addAccount(account); //commented this out for my tests; having issues with it for some reason


                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                       // Intent sIntent = new Intent(ProfileScreen.this, ProfileScreen.class); //sends user to profile
                       // sIntent.putExtra("Username",account.getUsername());
                       // startActivity(sIntent);

                        set_invisible(editList);
                        set_visible(originalList);
                        email.setText(newemail);
                        city.setText(newcity);


>>>>>>> 2961a6e90e7b32fc2245f922dbacc30f99bc20c9
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getApplicationContext(), "All Fields must be filled",
                                Toast.LENGTH_SHORT).show();
                    } catch (IllegalEmailException f) {
                        Toast.makeText(getApplicationContext(), "Must have valid email",
                                Toast.LENGTH_SHORT).show();
                    } catch (NoSpacesException s) {
                        Toast.makeText(getApplicationContext(), "Fields cannot contain spaces",
                                Toast.LENGTH_SHORT).show();
                    } catch (TooLongException w) {
                        Toast.makeText(getApplicationContext(), "A field is too long",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        name =(TextView) findViewById(R.id.originalname);
        editemail = (EditText) findViewById(R.id.editemail);
        editcity = (EditText) findViewById(R.id.editcity);
        confirm = (Button) findViewById(R.id.confirm_edit);
        editList = new ArrayList<View>((Arrays.asList(editemail, editcity, confirm)));
        set_invisible(editList);

<<<<<<< HEAD
        editemail = (EditText) findViewById(R.id.editemail);
        editcity = (EditText) findViewById(R.id.editcity);
        confirm = (Button) findViewById(R.id.confirm_edit);
=======
        //name =(TextView) findViewById(R.id.originalname);
        email =(TextView) findViewById(R.id.originalemail);
        city = (TextView) findViewById(R.id.originalcity);
        edit = (Button) findViewById(R.id.editprofile);
        originalList = new ArrayList<View>((Arrays.asList(city,email,edit)));
        set_visible(originalList);


        /*try{
            account.setCity("Lulala");
            account.setUsername("hahaha");
            account.setEmail("wow@cool.ca");
        }catch(IllegalEmailException e){
        }catch(TooLongException te){
        }catch (NoSpacesException ne){
        }*/

        //editemail = (EditText) findViewById(R.id.editemail);
        //editcity = (EditText) findViewById(R.id.editcity);
        //confirm = (Button) findViewById(R.id.confirm_edit);
>>>>>>> 2961a6e90e7b32fc2245f922dbacc30f99bc20c9
        //final ArrayList<View> editList = new ArrayList<View>((Arrays.asList(editemail,editcity,confirm)));
        editList = new ArrayList<View>((Arrays.asList(editemail,editcity,confirm)));
        set_invisible(editList);

        name =(TextView) findViewById(R.id.originalname);
        email =(TextView) findViewById(R.id.originalemail);
        city = (TextView) findViewById(R.id.originalcity);
        edit = (Button) findViewById(R.id.editprofile);
        //final ArrayList<View> originalList = new ArrayList<View>((Arrays.asList(name,city,email,edit)));
        originalList = new ArrayList<View>((Arrays.asList(name,city,email,edit)));


        /*name.setText(account.getUsername());
        email.setText(account.getEmail());
        city.setText(account.getCity());*/

        /*edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_visible(editList);
                set_invisible(originalList);
            }
        });*/

        /*confirm.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {

                try {
                    newemail = editemail.getText().toString();
                    newcity = editcity.getText().toString();

                    account.setEmail(newemail);
                    account.setCity(newcity);
                    saveload.saveInFile(ProfileScreen.this, account);


                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    Intent sIntent = new Intent(ProfileScreen.this, ProfileScreen.class); //sends user to profile
                    startActivity(sIntent);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), "All Fields must be filled",
                            Toast.LENGTH_SHORT).show();
                } catch (IllegalEmailException f) {
                    Toast.makeText(getApplicationContext(), "Must have valid email",
                            Toast.LENGTH_SHORT).show();
                } catch (NoSpacesException s) {
                    Toast.makeText(getApplicationContext(), "Fields cannot contain spaces",
                            Toast.LENGTH_SHORT).show();
                } catch (TooLongException w) {
                    Toast.makeText(getApplicationContext(), "A field is too long",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        accountManager=new AccountManager();
        Intent intent=getIntent();
        if(intent !=null) {
            Bundle extras=intent.getExtras();
            if(extras!=null) {
                String username=extras.getString("Username");
                Thread thread=new getThread(username);
                thread.start();
            }
        }

    }

    public void set_invisible(ArrayList<View> views){
        for(View v:views){
            v.setVisibility(View.INVISIBLE);
        }
    }

    public void set_visible(ArrayList<View> views){
        for(View v: views){
            v.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_screen, menu);
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

    class getThread extends Thread {
        private String username;
        public getThread(String username){this.username=username;}

        @Override
        public void run() {
//            account=accountManager.getAccount(username);
            runOnUiThread(doUpdateGUIDetails);

        }

    }

    class updateThread extends Thread {
        private Account account;
        public updateThread(Account account) {this.account=account;}

        @Override
        public void run() {
            accountManager.updateAccount(account);
            runOnUiThread(doUpdateGUIDetails2);
        }
    }
}
