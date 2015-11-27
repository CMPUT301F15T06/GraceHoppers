package com.gracehoppers.jlovas.bookwrm;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * holds a number on the server and updates it when called
 * @author nlovas
 */
public class NumberManager {
    private static final String URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t06/number/2"; //theres only one URL for one number
    private static final String SEARCHURL = "http://cmput301.softwareprocess.es:8080/cmput301f15t06/number/2/_search";
    private static final String TAG = "UniqueNumber";
    Gson gson;

    public NumberManager() {
        gson = new Gson();
    }


    public UniqueNumber getNumber() {
        SearchHit<UniqueNumber> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        System.out.print(URL);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<UniqueNumber>>() {
        }.getType();

        try {
            sr = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchHitType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sr.getSource();
    }


    public void updateNumber(UniqueNumber uninum) {
        HttpClient httpClient = new DefaultHttpClient();

        try {

            HttpPut updateRequest = new HttpPut(URL);

            //Value<Integer> tmp = new Value<Integer>(number);
            String acc = gson.toJson(uninum);
            StringEntity stringEntity = new StringEntity(acc);
            updateRequest.setEntity(stringEntity);
            updateRequest.setHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(updateRequest);
            String status = response.getStatusLine().toString();

            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * called onl once to set up the server. Only one number is ever stored
     * @param uninum
     */
    public void addNumber(UniqueNumber uninum) {

        HttpClient httpClient = new DefaultHttpClient();
        try {

            HttpPost addRequest = new HttpPost(URL);
            StringEntity stringEntity = new StringEntity(gson.toJson(uninum));


            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

            Log.i(TAG, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
