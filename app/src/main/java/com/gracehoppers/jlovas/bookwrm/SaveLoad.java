package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * Created by nlovas on 10/16/15.
 */
public class SaveLoad {
    /*
    Saves and loads to the SD card using gson
    Code from the CMPUT 301 labs. University of Alberta:
     TA's Joshua Campbell, Shida He, Stephen Romansky, and Ian Watts, 2015-16-10
     */


        protected static final String FILENAME = "file.sav";

    //saves your account locally
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

        //load your account
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
    }

