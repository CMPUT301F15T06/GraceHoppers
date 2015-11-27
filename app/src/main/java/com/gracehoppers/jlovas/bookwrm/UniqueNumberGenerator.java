package com.gracehoppers.jlovas.bookwrm;

import android.util.Log;

/**
 * Creates a unique number for each book
 * @author nlovas
 */
public class UniqueNumberGenerator extends Thread {

    //private int number;
    UniqueNumber uniquenum;

    public UniqueNumberGenerator() {

    }

    public UniqueNumber getUniqueNumber() {//check the server and update the number
        //1: get the number on the server (if theres no number, add one)

        //2: ++

        //3: update the server

        //4: return this number
            start();

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Log.e("inside method:", ""+uniquenum.getNumber());
        return uniquenum;
    }

    @Override
    public void run() {

        NumberManager numbermanager = new NumberManager();
        uniquenum=numbermanager.getNumber();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        uniquenum.inc();
        //Log.e("number: ", "" + uniquenum.getNumber());
        numbermanager.updateNumber(uniquenum);

    }

}

