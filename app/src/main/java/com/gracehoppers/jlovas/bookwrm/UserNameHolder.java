package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Created by chen1 on 11/29/15.
 */
public class UserNameHolder {
    public ArrayList getUsernames() {
        return usernames;
    }

    ArrayList usernames = new ArrayList();

    public void addUser(String user){
        usernames.add(user);
    }
}
