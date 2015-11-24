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
 * keeps track of all friendrequests
 * @author nlovas
 */
public class FriendRequestManager {

    private static final String URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t06/friendrequests/";
    private static final String SEARCHURL = "http://cmput301.softwareprocess.es:8080/cmput301f15t06/friendrequests/_search";
    private static final String TAG = "FriendRequests";
    private Gson gson;


    private HttpClient httpclient = new DefaultHttpClient(); //pasted in

    public FriendRequestManager() {
        gson = new Gson();
    }

    /**
     * stores a friendrequest on the server
     * @param friendrequest
     */
    public void addFriendRequest(FriendRequest friendrequest) {

        HttpClient httpClient = new DefaultHttpClient();
        try {
            Log.e("friend request sender: ", friendrequest.getSender());
            HttpPost addRequest = new HttpPost(URL + friendrequest.getSender() + "-"+friendrequest.getReceiver());
            StringEntity stringEntity = new StringEntity(gson.toJson(friendrequest));


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
    public FriendRequests findFriendRequests(String username) throws IOException{
        //find any unanswered FR's for this user
        FriendRequests result = new FriendRequests();
    HttpPost searchRequest = new HttpPost(SEARCHURL);
    //SimpleSearchCommand command = new SimpleSearchCommand(senderusername);
//--------------------

    String query = "{\"query\": {\"bool\": {\"must\": [ { \"match\": { \"receiver\": \"" + username+ "\"   }}, { \"match\": { \"isAnswered\": \"false\"   }}] } } }";
    //find a FR where you are receiver, and the request hasnt been answered

    StringEntity stringentity = new StringEntity(query);

    searchRequest.setHeader("Accept","application/json");
    searchRequest.setEntity(stringentity);

    HttpResponse response = httpclient.execute(searchRequest);
    String status = response.getStatusLine().toString();
    System.out.println(status);

    String json = getEntityContent(response);

    //Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<FriendRequest>>(){}.getType();
    //ElasticSearchSearchResponse<FriendRequest> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);

    Type searchResponseType = new TypeToken<SearchResponse<FriendRequest>>() {
    }.getType();

    SearchResponse<FriendRequest> esResponse;

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
        /*    for (SearchResponse<FriendRequest> r : esResponse.getHits()) {
                FriendRequest friendrequest = r.getSource();
                System.err.println(friendrequest);
            }*/



    for (SearchHit<FriendRequest> hit : esResponse.getHits().getHits()) {
        result.add(hit.getSource());
    }

    //searchRequest.releaseConnection();

       // if(!esResponse.getHits().getHits().isEmpty()){ //if there are results
            return result;
      //  } else return null;

    }

    /**
     * returns true if there are any unanswered friend requests between user 1 and user 2
     * @param senderusername
     * @param receiverusername
     * @return boolean
     * @throws IOException
     */
    public boolean searchFriendRequest(String senderusername, String receiverusername) throws IOException {
        //want: to search both sender and receiver and the status being false
        int found =0;
        //Accounts result = new Accounts();
        //Log.e("made it", "made it!");
        HttpPost searchRequest = new HttpPost(SEARCHURL);
        //SimpleSearchCommand command = new SimpleSearchCommand(senderusername);
//--------------------

        String query = "{\"query\": {\"bool\": {\"must\": [ { \"match\": { \"sender\":  \"" +senderusername + "\" }}, { \"match\": { \"receiver\": \"" + receiverusername+ "\"   }}, { \"match\": { \"isAnswered\": \"false\"   }}] } } }";
        //find a FR where you are the sender, friend is receiver, and the request hasnt been answered

            StringEntity stringentity = new StringEntity(query);

            searchRequest.setHeader("Accept","application/json");
            searchRequest.setEntity(stringentity);

            HttpResponse response = httpclient.execute(searchRequest);
            String status = response.getStatusLine().toString();
            System.out.println(status);

            String json = getEntityContent(response);

            //Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<FriendRequest>>(){}.getType();
            //ElasticSearchSearchResponse<FriendRequest> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);

        Type searchResponseType = new TypeToken<SearchResponse<FriendRequest>>() {
        }.getType();

        SearchResponse<FriendRequest> esResponse;

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
        /*    for (SearchResponse<FriendRequest> r : esResponse.getHits()) {
                FriendRequest friendrequest = r.getSource();
                System.err.println(friendrequest);
            }*/

        ArrayList<FriendRequest> result = new ArrayList<FriendRequest>();
        for (SearchHit<FriendRequest> hit : esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }

            //searchRequest.releaseConnection();

        if(esResponse.getHits().getHits().isEmpty()){ //if there are no results at all
            //return false;
          //  found =0;
        } else found++;

        //next, lets see if theres a FR where friend is the sender and you are the receiver

        String query2 = "{\"query\": {\"bool\": {\"must\": [ { \"match\": { \"sender\":  \"" +receiverusername + "\" }}, { \"match\": { \"receiver\": \"" + senderusername+ "\"   }}, { \"match\": { \"isAnswered\": \"false\"   }}] } } }";

        StringEntity stringentity2 = new StringEntity(query2);

        searchRequest.setHeader("Accept","application/json");
        searchRequest.setEntity(stringentity2);

        HttpResponse response2 = httpclient.execute(searchRequest);
        String status2 = response2.getStatusLine().toString();
        System.out.println(status2);

        String json2 = getEntityContent(response2);

        //Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<FriendRequest>>(){}.getType();
        //ElasticSearchSearchResponse<FriendRequest> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);

        Type searchResponseType2 = new TypeToken<SearchResponse<FriendRequest>>() {
        }.getType();

        SearchResponse<FriendRequest> esResponse2;

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
        Log.e("es Response","esresponse: ");
        System.err.println(esResponse2);
        /*    for (SearchResponse<FriendRequest> r : esResponse.getHits()) {
                FriendRequest friendrequest = r.getSource();
                System.err.println(friendrequest);
            }*/

        ArrayList<FriendRequest> result2 = new ArrayList<FriendRequest>();
        for (SearchHit<FriendRequest> hit2 : esResponse2.getHits().getHits()) {
            result2.add(hit2.getSource());
        }

        //searchRequest.releaseConnection();

        if(esResponse2.getHits().getHits().isEmpty()){ //if there are no results at all
            //return false;
          //  found =0;
        } else found++;
Log.e("found value","" +found);

        if(found>0){ //either you have already sent this person a FR, or your friend has sent you a FR
        return true;
        } else return false;


    }




        //------------------ pasted in

/*
        //String query = gson.toJson(command);
        String query2 = gson.toJson(command);
        Log.i(TAG, "Json command: " + query2);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query2);
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

        Type searchResponseType = new TypeToken<SearchResponse<FriendRequest>>() {
        }.getType();

        SearchResponse<FriendRequest> esResponse;

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
*/

/*
        for (SearchHit<FriendRequest> hit : esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }
nicole
*/
        //result.notifyObservers();

        //return result;
        //Log.e("hits",hit.getSource());


/*
    public FriendRequest getFriendRequest(String senderusername) {
        SearchHit<FriendRequest> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL + senderusername + "/"); //change this !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        System.out.print(URL + senderusername);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<FriendRequest>>() {
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
*/

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
     * deletes a FR from the server
     * @param senderusername
     * @param receiverusername
     */
    public void deleteFR(String senderusername, String receiverusername) {
        HttpClient httpClient=new DefaultHttpClient();
        try{
            HttpDelete delete=new HttpDelete(URL+ senderusername + "-" + receiverusername);
            delete.setHeader("Accept","application/json");

            HttpResponse response=httpClient.execute(delete);
            String status=response.getStatusLine().toString();
            Log.i(TAG,status);
            Log.e("deleted","FR deleted");
        }catch(Exception e) {e.printStackTrace();}
    }

}
