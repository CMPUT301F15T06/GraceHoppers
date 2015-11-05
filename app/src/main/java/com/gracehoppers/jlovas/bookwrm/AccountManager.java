package com.gracehoppers.jlovas.bookwrm;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by dzhang4 on 10/31/15.
 */
public class AccountManager {
    private static final String URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t06/account/";
    private static final String TAG = "AccountSearch";
    Gson gson;

    public AccountManager() {
        gson = new Gson();
    }

    public void addAccount(Account account) {
        //System.out.print(URL+account.getUsername());
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost addRequest = new HttpPost(URL + account.getUsername());
            StringEntity stringEntity = new StringEntity(gson.toJson(account));

            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

            Log.i(TAG, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account getAccount(String username) {
        SearchHit<Account> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL + username);
        System.out.print(URL + username);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<Account>>() {
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

    public void deleteAccount(String username) {
        HttpClient httpClient=new DefaultHttpClient();
        try{
            HttpDelete delete=new HttpDelete(URL+username);
            delete.setHeader("Accept","application/json");

            HttpResponse response=httpClient.execute(delete);
            String status=response.getStatusLine().toString();
            Log.i(TAG,status);
        }catch(Exception e) {e.printStackTrace();}
    }

    /*
    public Accounts searchAccount(String username) {
        Accounts result=new Accounts();
        HttpPost searchRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t06/account/_search");
        SimpleSearchCommand command=new SimpleSearchCommand(username);

        String query=gson.toJson(command);
        Log.i(TAG, "Json command: " + query);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringEntity);

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpClient.execute(searchRequest);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Type searchResponseType = new TypeToken<SearchResponse<Account>>() {
        }.getType();

        SearchResponse<Account> esResponse;

        try {
            esResponse = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (SearchHit<Account> hit : esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }



        //result.notifyObservers();

        return result;
        HttpClient httpClient=new DefaultHttpClient();
        try {
            HttpPost searchRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t06/account/_search");
            HttpResponse response=httpClient.execute(searchRequest);

            String status=response.getStatusLine().toString();
            Log.i(TAG, status);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));
            StringBuffer buffer=new StringBuffer();
            String line="";
            while((line=rd.readLine())!=null) {
                buffer.append(line);
            }
            String json=buffer.toString();
            Type searchResponseType=new TypeToken<SearchResponse<Account>>() {}.getType();
            SearchResponse<Account> esResponse=gson.fromJson(json,searchResponseType);
            Hits<Account> hits=esResponse.getHits();
            hits.getTotal();


        }catch(IOException e) {e.printStackTrace();}
        //return result;

    }*/

}
