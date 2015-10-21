package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;

import java.io.IOException;

/**
 * Created by nlovas on 10/15/15.
 */

public class Account {
    /*
    The Account of the user, which includes their username, city, email, friendlist, and booklist,
     */

    private String username;
    private String city;
    private String email;
    //private BookList booklist = new Booklist(); //uncomment this once it has been written
    //private Friendlist friendlist = new Friendlist(); //uncomment this once it has been written
    private Inventory inventory;
    private SaveLoad saveload; //for saving your account


    public Account() {
        //checks to see if the account already exists, cant do this without the database

        //If(searchDatabase(Username)==true){throw new AlreadyExistsException;}

    }

   // public void SaveAccount(final Context context){

//this doesnt need to be done within the class(?)


   // }


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
                if(email.length()>20){
                    throw new TooLongException();
                } else
                {
                    this.email = email;
                   }

    }
}
