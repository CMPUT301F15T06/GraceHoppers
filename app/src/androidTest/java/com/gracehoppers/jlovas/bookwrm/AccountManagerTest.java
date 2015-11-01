package com.gracehoppers.jlovas.bookwrm;

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
        String username="a";
        String city="Arizna";
        String email="a@gmail.com";
        Account account=new Account();
        account.setUsername(username);
        account.setCity(city);
        account.setEmail(email);
        System.out.println(account.getUsername());
        accountManager.addAccount(account);

        //Account result=accountManager.getAccount(account.getUsername());
        Account result=accountManager.getAccount("asf");

        accountManager.addAccount(result);

        assertEquals(account.getUsername(),result.getUsername());
        assertEquals(account.getCity(), result.getCity());
        assertEquals(account.getEmail(),result.getEmail());

        accountManager.deleteAccount(account.getUsername());
    }

   /* public void testgetAccount() throws NoSpacesException, TooLongException, IllegalEmailException{
        String username="asdf";
        String city="Arizna";
        String email="a@gmail.com";
        Account account=new Account();
        account.setUsername(username);
        account.setCity(city);
        account.setEmail(email);

        AccountManager accountManager=new AccountManager();
        accountManager.addAccount(account);

        Account result=accountManager.getAccount(account.getUsername());
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
        assertTrue(accountManager.getAccount(account.getUsername())==null);

        accountManager.deleteAccount(account.getUsername());
    }*/
}