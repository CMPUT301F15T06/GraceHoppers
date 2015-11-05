package com.gracehoppers.jlovas.bookwrm;

/**
 * Created by jlovas on 11/4/15.
 */
public class OneAccount {
    private static OneAccount ourInstance = new OneAccount();

    public static OneAccount getInstance() {
        return ourInstance;
    }

    private OneAccount() {
    }
}

//call load once per application sign up/log in

//when sign up for the first time, saveLoad =null
//when logged