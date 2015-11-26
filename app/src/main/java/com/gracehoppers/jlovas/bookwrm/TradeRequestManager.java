package com.gracehoppers.jlovas.bookwrm;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by ljuarezr on 11/25/15.
 *
 * Keeps track of all TradeRequests
 * @author ljuarezr (based on nlovas' FriendRequestManager)
 *
 */
public class TradeRequestManager {

    private static final String URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t06/traderequests/";
    private static final String SEARCHURL = "http://cmput301.softwareprocess.es:8080/cmput301f15t06/traderequests/_search";
    private static final String TAG = "TradeRequests";
    private Gson gson;

    private HttpClient httpclient = new DefaultHttpClient(); //pasted in

    public TradeRequestManager() {
        gson = new Gson();
    }

    /**
     * stores a friendrequest on the server
     * @param traderequest
     */





}
