package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Created by chen1 on 11/29/15.
 */
public class UserNameHolder {

    ArrayList usernames;

    public ArrayList getUsernames() {
        return usernames;
    }


    public void addUser(String user){
        usernames.add(user);
    }
}
