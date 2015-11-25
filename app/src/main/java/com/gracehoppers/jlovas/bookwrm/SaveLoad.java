package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * Saves and loads the Account to the SD card using gson into a file called "file.sav"
 * <p>
 *Code from the CMPUT 301 labs. University of Alberta:
 *TA's Joshua Campbell, Shida He, Stephen Romansky, and Ian Watts, 2015-16-10
 * @author nlovas
 * @see Account
 */
public class SaveLoad {


    protected static final String FILENAME = "file.sav";
    protected static final String PHOTOFILE = "photos.sav";

    protected static final String FRIEND = "friend.sav"; //this could go into cache. using it to save one friend's account when you want to view a specific book of theirs


    public SaveLoad() {
    }

    /**
     * saves the account to the SD card using gson.
     * @param context
     * @param account
     */
        public void saveInFile(Context context,Account account) {
            try {
                FileOutputStream fos = context.openFileOutput(FILENAME, 0);
                BufferedWriter out=new BufferedWriter(new OutputStreamWriter(fos));
                Gson gson=new Gson();
                gson.toJson(account, out);
                out.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e);
            }
        }

    /**
     * loads an account from the SD card using gson.
     * @param context
     * @return Account
     */
        public Account loadFromFile(Context context) {
            Account account=null;
            try {

                FileInputStream fis = context.openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson=new Gson();
                //https://sites.google.com/site/gson/gson-user-guide 2015-16-10
                Type type=new TypeToken<Account>() {}.getType();
                account=gson.fromJson(in,type);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                //throw new RuntimeException(e);
                //key=new ArrayList<keyStats>();
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //throw new RuntimeException(e);
                e.printStackTrace();
            }
            return account;
        }

    //adding this for storing images to pass between actvities
    public void savePhotos(Context context, Photos photoList){

        try {
            FileOutputStream fos = context.openFileOutput(PHOTOFILE, 0);
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson=new Gson();
            gson.toJson(photoList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    //adding this for storing images to pass between actvities
    public Photos loadPhotos(Context context){

        Photos myPhotos=null;
        try {

            FileInputStream fis = context.openFileInput(PHOTOFILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson=new Gson();
            //https://sites.google.com/site/gson/gson-user-guide 2015-16-10
            Type type=new TypeToken<Photos>() {}.getType();
            myPhotos=gson.fromJson(in,type);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException(e);
            //key=new ArrayList<keyStats>();
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return myPhotos;
    }

    /**
     * loads a friend account from the SD card using gson.
     * @param context
     * @return Account
     */
    public Account loadFriendFromFile(Context context) {
        Account account=null;
        try {

            FileInputStream fis = context.openFileInput(FRIEND);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson=new Gson();
            //https://sites.google.com/site/gson/gson-user-guide 2015-16-10
            Type type=new TypeToken<Account>() {}.getType();
            account=gson.fromJson(in,type);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException(e);
            //key=new ArrayList<keyStats>();
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return account;
    }


    /**
     * saves a friend account to the SD card using gson.
     * @param context
     * @param account
     */
    public void saveFriendInFile(Context context,Account account) {
        try {
            FileOutputStream fos = context.openFileOutput(FRIEND, 0);
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson=new Gson();
            gson.toJson(account, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    }

