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
    private SaveLoad saveload; //for saving your account


    public Account(String Username, String Email, String City) {
        //checks to see if the account already exists, cant do this without the database

        //If(searchDatabase(Username)==true){throw new AlreadyExistsException;}



        //all fields must be filled in:

        if(Username==""||Email==""||City==""){
            throw new IllegalArgumentException();
        } else

        //must have a valid email
        if(!Email.contains("@")){
            throw new IllegalArgumentException();
        } else
        if(Username.contains(" ")||Email.contains(" ")||City.contains(" ")){
            throw new IllegalArgumentException();
        }else
        {


            this.username = Username;
            this.email = Email;
            this.city = City;
        }
    }

   // public void SaveAccount(final Context context){

//this doesnt need to be done within the class(?)


   // }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
