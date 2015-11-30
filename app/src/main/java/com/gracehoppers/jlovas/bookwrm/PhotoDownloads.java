package com.gracehoppers.jlovas.bookwrm;

import android.app.Application;

/**
 * PhotoDownloads is a global boolean variable to check whether photo downloads
 * are enabled or disabled.
 *
 * @author jlovas
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
