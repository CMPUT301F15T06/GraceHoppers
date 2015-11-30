package com.gracehoppers.jlovas.bookwrm;

import android.util.Log;

import com.google.gson.Gson;
import com.gracehoppers.jlovas.bookwrm.Account;
import com.gracehoppers.jlovas.bookwrm.AccountManager;
import com.gracehoppers.jlovas.bookwrm.IllegalEmailException;
import com.gracehoppers.jlovas.bookwrm.NoSpacesException;
import com.gracehoppers.jlovas.bookwrm.TooLongException;


import junit.framework.TestCase;

/**
 * Created by dzhang4 on 10/31/15.
 */
public class AccountManagerTest extends TestCase {

    public void testaddAccount() throws NoSpacesException, TooLongException, IllegalEmailException {
        AccountManager accountManager=new AccountManager();
        //accountManager.deleteAccount("asdf");
        String username="b";
        String city="Arizna";
        String email="a@gmail.com";
        Account account=new Account();
        account.setUsername(username);
        account.setCity(city);
        account.setEmail(email);

        Gson gson = new Gson();
        Log.d("sigh", gson.toJson(account));
        accountManager.addAccount(account);

        Account result=accountManager.getAccount(account.getUsername());
        accountManager.addAccount(result);

        assertEquals(account.getUsername(),result.getUsername());
        assertEquals(account.getCity(), result.getCity());
        assertEquals(account.getEmail(),result.getEmail());

        //accountManager.deleteAccount(account.getUsername());
    }

    public void testgetAccount() throws NoSpacesException, TooLongException, IllegalEmailException{
        AccountManager accountManager=new AccountManager();
        String username="asdf";
        String city="Arizna";
        String email="a@gmail.com";
        Account account=new Account();
        account.setUsername(username);
        account.setCity(city);
        account.setEmail(email);

        accountManager.addAccount(account);

        Account result=accountManager.getAccount(account.getUsername());
        accountManager.addAccount(result);
        assertEquals(account.getUsername(), result.getUsername());
        assertEquals(account.getCity(), result.getCity());
        assertEquals(account.getEmail(), result.getEmail());

        accountManager.deleteAccount(account.getUsername());
    }

    public void testdeleteAccount() throws NoSpacesException, TooLongException, IllegalEmailException{
        String username="asdf";
        String city="Arizna";
        String email="a@gmail.com";
        Account account =new Account();
        account.setUsername(username);
        account.setCity(city);
        account.setEmail(email);

        AccountManager accountManager=new AccountManager();
        accountManager.addAccount(account);
        accountManager.deleteAccount(account.getUsername());
        Account result = new Account();
        //assertEquals(account.getUsername(), result.getUsername());
        //assertEquals(account.getCity(), result.getCity());
        //assertEquals(account.getEmail(), result.getEmail());
        assertTrue(accountManager.getAccount(account.getUsername()) == null);

        //accountManager.deleteAccount(account.getUsername());
    }

    public void testupdateAccount() throws NoSpacesException,TooLongException,IllegalEmailException{
        String username="asdf";
        String city="Arizna";
        String email="a@gmail.com";
        Account account =new Account();
        account.setUsername(username);
        account.setCity(city);
        account.setEmail(email);

        AccountManager accountManager=new AccountManager();
        accountManager.addAccount(account);

        city="newCity";
        email="newEmail@n.e";
        account.setCity(city);
        account.setEmail(email);

        accountManager.updateAccount(account);
        Account result=accountManager.getAccount(account.getUsername());
        assertEquals(city,result.getCity());
        assertEquals(email,result.getEmail());

    }
}
