package com.gracehoppers.jlovas.bookwrm;

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


    public Account(String Username, String Email, String City) {
        this.username=Username;
        this.email = Email;
        this.city=City;
    }

    public void SaveAccount(){



    }


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
