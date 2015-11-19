package com.gracehoppers.jlovas.bookwrm;

import android.app.Application;
import android.content.Context;

/**
 * Created by dzhang4 on 11/18/15.
 */
public class ContextProvider extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context get() {
        return context;
    }
}
