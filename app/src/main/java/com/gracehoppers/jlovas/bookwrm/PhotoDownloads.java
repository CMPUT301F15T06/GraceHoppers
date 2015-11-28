package com.gracehoppers.jlovas.bookwrm;

import android.app.Application;

/**
 * Created by jlovas on 11/27/15.
 */
public class PhotoDownloads extends Application {



    private boolean enabled=true;

    public void setEnabled(boolean b){
        enabled = b;
    }

    public boolean getEnabled(){
        return enabled;
    }

}
