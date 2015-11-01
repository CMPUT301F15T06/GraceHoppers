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
import java.lang.reflect.Type;

/**
 * Created by dzhang4 on 10/31/15.
 */
public class AccountManager {
    private static final String URL="http://cmput301.softwareprocess.es:8080/cmput301f15t06/account/";
    private static final String TAG="AccountSearch";
    Gson gson;

    public AccountManager() {
        gson=new Gson();
    }

    public void addAccount(Account account) {
        System.out.print(URL+account.getUsername());
        HttpClient httpClient=new DefaultHttpClient();
        try{
            HttpPost addRequest=new HttpPost(URL+account.getUsername());
            StringEntity stringEntity=new StringEntity(gson.toJson(account));

            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

            Log.i(TAG, status);
        }catch(Exception e) {e.printStackTrace();}
    }

    public Account getAccount(String username) {
        SearchHit<Account> sr=null;
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(URL+username);
        System.out.print(URL+username);

        HttpResponse response=null;

       /* try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<Account>>() {}.getType();

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
        }*/
        try{
            //System.out.print("1");
            response=httpClient.execute(httpGet);
            //System.err.print(response.toString());

            BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result=new StringBuffer();
            String info;
            while((info=reader.readLine())!=null) {
                result.append(info);
                //System.out.println("Info"+info);
            }
            String result2=result.toString();

            System.out.print("result2 "+result2);

            Type type=new TypeToken<SearchHit<Account>>() {}.getType();

            SearchHit<Account> accounts=gson.fromJson(result2,type);
            return accounts.getSource();


        }catch(Exception e){e.printStackTrace();}
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


}
