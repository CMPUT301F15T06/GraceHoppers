package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dzhang4 on 11/18/15.
 */
public class ConnectionCheck {
    private boolean connectionStatus;

    public boolean checkConnection(Context context) {

        try{
            ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo network=cm.getActiveNetworkInfo();
            return network!=null && network.isConnectedOrConnecting();
        }catch(Exception e) {return false;}
    }


}