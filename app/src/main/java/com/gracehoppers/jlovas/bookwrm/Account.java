package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * The account used by a user once they have logged in or signed up.
 * <p>
 * Contains Strings city, email, and a unique username.
 * also has an Inventory, Friends, and TradeHistory
 * Account has getters for all of these and setters for the Strings and Inventories
 * @author nlovas
 *@see Inventory , Friends , TradeHistory
 */

public class Account implements Serializable {


    private String username;
    private String city;
    private String email;
    private TradeHistory tradeHistory= new TradeHistory(); // create trade history
    private Inventory inventory;
    private Friends friends; //create friend list
    private boolean needUpdate;

    public boolean getNeedTRupdate() {
        return needTRupdate;
    }

    public void setNeedTRupdate(boolean needTRupdate) {
        this.needTRupdate = needTRupdate;
    }

    private boolean needTRupdate;
    private  static ArrayList<TradeRequest> queue;


    public TradeHistory getTradeHistory() {
        return tradeHistory;
    }

    public void setTradeHistory(TradeHistory tradeHistory) {
        this.tradeHistory = tradeHistory;
    }

    public ArrayList<TradeRequest> getQueue() {
        if (queue == null) {
            queue = new ArrayList<TradeRequest>();
        }
        return queue;
    }

    /**
     * Constructor for Account creates an empty Inventory and empty Friends
     */
    public Account() {
        inventory = new Inventory();
        friends = new Friends();
        tradeHistory = new TradeHistory();
        needUpdate=false;

        needTRupdate = false;
        queue = new ArrayList<TradeRequest>();


    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws NoSpacesException, TooLongException {
//length constraint: 20 characters
        if(username.isEmpty()){
            throw new IllegalArgumentException();
        } else
            //fields cant have spaces
                if(username.contains(" ")){
                    throw new NoSpacesException();
                    } else{
                    if(username.length()>20){
                        throw new TooLongException();
                    }else
                    this.username = username;
                    }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws NoSpacesException, TooLongException {
//length constraint: 20 characters
        if(city.isEmpty()){
            throw new IllegalArgumentException();
        } else
        if(city.length()>20){
            throw new TooLongException();
        } else
             //cities ARE allowed to have spaces
                {
                    this.city = city;
                }

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalEmailException, NoSpacesException, TooLongException {
//length constraint: 20 characters
        if(email.isEmpty()){
            throw new IllegalArgumentException();
        } else
            //must have a valid email
            if(!email.contains("@")){
                throw new IllegalEmailException();
            } else //fields cant have spaces
                if(email.contains(" ")){
                    throw new NoSpacesException();
                } else
                if(email.length()>30){
                    throw new TooLongException();
                } else
                {
                    this.email = email;
                   }

    }

    public Friends getFriends(){
        return friends;
    }

    //not sure how i can access the inventory otherwise - need this for tests
    //change if you have to


    public Inventory getInventory() {

        return inventory;
    }

    public void setInventory(Inventory anInventory){
        inventory = anInventory;
    }

    public void setNeedUpdate(boolean update) {needUpdate=update;}
    public boolean getNeedUpdate() {return needUpdate;}
}
