package com.gracehoppers.jlovas.bookwrm;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
     * stores a traderequest on the server
     * @param traderequest
     */

    public void addTradeRequest(TradeRequest traderequest) {

        HttpClient httpClient = new DefaultHttpClient();
        try {
            Log.e("TR sender: ", traderequest.getSender());
            Log.e("TR receiver: ", traderequest.getReceiver());
            Log.e("TR owner: ", traderequest.getTrade().getOwner().getUsername());
            Log.e("TR borrower: ", traderequest.getTrade().getBorrower().getUsername());
            Log.e("TR ownerBook: ", traderequest.getOwnerBook());
            Log.e("TR borrowerBook: ", traderequest.getBorrowerBook());


            HttpPost addRequest = new HttpPost(URL + traderequest.getSender() + "-"+traderequest.getReceiver()
                                                + "-"+traderequest.getOwnerBook() + "-"+traderequest.getBorrowerBook());
            StringEntity stringEntity = new StringEntity(gson.toJson(traderequest));


            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

            Log.i(TAG, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *gives all unanswered friend requests where the receiver=you
     * @param username
     * @return ArrayList<FriendRequest>
     * @throws IOException
     */
    public TradeRequests findTradeRequests(String username) throws IOException{
        //find any unanswered TR's for this user
        TradeRequests result = new TradeRequests();
        HttpPost searchRequest = new HttpPost(SEARCHURL);
        //SimpleSearchCommand command = new SimpleSearchCommand(senderusername);
//--------------------

        String query = "{\"query\": {\"bool\": {\"must\": [ { \"match\": { \"receiver\": \"" + username+ "\"   }}, { \"match\": { \"isAnswered\": \"false\"   }}] } } }";
        //find a TR where you are receiver, and the request hasnt been answered

        StringEntity stringentity = new StringEntity(query);

        searchRequest.setHeader("Accept","application/json");
        searchRequest.setEntity(stringentity);

        HttpResponse response = httpclient.execute(searchRequest);
        String status = response.getStatusLine().toString();
        System.out.println(status);

        String json = getEntityContent(response);

        //Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<FriendRequest>>(){}.getType();
        //ElasticSearchSearchResponse<FriendRequest> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);

        Type searchResponseType = new TypeToken<SearchResponse<TradeRequest>>() {
        }.getType();

        SearchResponse<TradeRequest> esResponse;

        try {
            esResponse = gson.fromJson(json, searchResponseType);
//                    new InputStreamReader(response.getEntity().getContent()),
            //                  searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
        Log.e("es Response","esresponse: ");
        System.err.println(esResponse);

        for (SearchHit<TradeRequest> hit : esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }

        //searchRequest.releaseConnection();

        // if(!esResponse.getHits().getHits().isEmpty()){ //if there are results
        return result;
        //  } else return null;

    }

    /**
     * get the http response and return json string
     * pasted in
     */
    String getEntityContent(HttpResponse response) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));
        String output;
        System.err.println("Output from Server -> ");
        String json = "";
        while ((output = br.readLine()) != null) {
            System.err.println(output);
            json += output;
        }
        System.err.println("JSON:"+json);
        return json;
    }

    /**
     * returns true if there are any unanswered friend requests between user 1 and user 2
     * @param senderusername
     * @param receiverusername
     * @return boolean
     * @throws IOException
     */
    public boolean searchTradeRequest(String senderusername, String receiverusername) throws IOException {
        //want: to search both sender and receiver and the status being false
        int found = 0;
        //Accounts result = new Accounts();
        //Log.e("made it", "made it!");
        HttpPost searchRequest = new HttpPost(SEARCHURL);
        //SimpleSearchCommand command = new SimpleSearchCommand(senderusername);
//--------------------

        String query = "{\"query\": {\"bool\": {\"must\": [ { \"match\": { \"sender\":  \"" + senderusername + "\" }}, { \"match\": { \"receiver\": \"" + receiverusername + "\"   }}, { \"match\": { \"isAnswered\": \"false\"   }}] } } }";
        //find a TR where you are the sender, friend is receiver, and the request hasnt been answered

        StringEntity stringentity = new StringEntity(query);

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringentity);

        HttpResponse response = httpclient.execute(searchRequest);
        String status = response.getStatusLine().toString();
        System.out.println(status);

        String json = getEntityContent(response);

        //Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<FriendRequest>>(){}.getType();
        //ElasticSearchSearchResponse<FriendRequest> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);

        Type searchResponseType = new TypeToken<SearchResponse<TradeRequest>>() {
        }.getType();

        SearchResponse<TradeRequest> esResponse;

        try {
            esResponse = gson.fromJson(json, searchResponseType);
//                    new InputStreamReader(response.getEntity().getContent()),
            //                  searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
        Log.e("es Response", "esresponse: ");
        System.err.println(esResponse);
        /*    for (SearchResponse<FriendRequest> r : esResponse.getHits()) {
                FriendRequest friendrequest = r.getSource();
                System.err.println(friendrequest);
            }*/

        ArrayList<TradeRequest> result = new ArrayList<TradeRequest>();
        for (SearchHit<TradeRequest> hit : esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }

        //searchRequest.releaseConnection();

        if (esResponse.getHits().getHits().isEmpty()) { //if there are no results at all
            //return false;
            //  found =0;
        } else found++;

        //next, lets see if theres a TR where friend is the sender and you are the receiver

        String query2 = "{\"query\": {\"bool\": {\"must\": [ { \"match\": { \"sender\":  \"" + receiverusername + "\" }}, { \"match\": { \"receiver\": \"" + senderusername + "\"   }}, { \"match\": { \"isAnswered\": \"false\"   }}] } } }";

        StringEntity stringentity2 = new StringEntity(query2);

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringentity2);

        HttpResponse response2 = httpclient.execute(searchRequest);
        String status2 = response2.getStatusLine().toString();
        System.out.println(status2);

        String json2 = getEntityContent(response2);

        Type searchResponseType2 = new TypeToken<SearchResponse<TradeRequest>>() {
        }.getType();

        SearchResponse<TradeRequest> esResponse2;

        try {
            esResponse2 = gson.fromJson(json2, searchResponseType2);
//                    new InputStreamReader(response.getEntity().getContent()),
            //                  searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
        Log.e("es Response", "esresponse: ");
        System.err.println(esResponse2);


        ArrayList<TradeRequest> result2 = new ArrayList<TradeRequest>();
        for (SearchHit<TradeRequest> hit2 : esResponse2.getHits().getHits()) {
            result2.add(hit2.getSource());
        }

        //searchRequest.releaseConnection();

        if (esResponse2.getHits().getHits().isEmpty()) { //if there are no results at all
            //return false;
            //  found =0;
        } else found++;
        Log.e("found value", "" + found);

        if (found > 0) { //either you have already sent this person a TR, or your friend has sent you a TR
            return true;
        } else return false;
    }


    public void deleteTR(TradeRequest traderequest) {
        HttpClient httpClient=new DefaultHttpClient();
        try{
            HttpDelete delete=new HttpDelete(URL + traderequest.getSender() + "-"+traderequest.getReceiver()
                    + "-"+traderequest.getOwnerBook() + "-"+traderequest.getBorrowerBook());
            delete.setHeader("Accept","application/json");

            HttpResponse response=httpClient.execute(delete);
            String status=response.getStatusLine().toString();
            Log.i(TAG,status);
            Log.e("deleted","TR deleted");
        }catch(Exception e) {e.printStackTrace();}
    }
}
