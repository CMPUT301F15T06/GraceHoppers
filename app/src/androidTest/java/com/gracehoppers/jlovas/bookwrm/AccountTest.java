package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by nlovas on 10/15/15.
 */
public class AccountTest extends ActivityInstrumentationTestCase2{

    /*
    Tests to see if the account was properly created
     */

        //@Override
        public AccountTest(){
            super(Account.class);
        }

public void testAccountcreation() throws IllegalEmailException, NoSpacesException, TooLongException { //tests to ensure each part is correct inside the account, also that it will throw its exceptions
    Account account = new Account();

    account.setCity("Grande Prairie");
    account.setUsername("123cool");
    account.setEmail("wow@cool.ca");

//test the setters/getters
    assertTrue(account.getCity() == "Grande Prairie");
    assertTrue(account.getUsername()=="123cool");
    assertTrue(account.getEmail()=="wow@cool.ca");

    //test the empty field exceptions
    try {
        account.setUsername("");
    }catch(IllegalArgumentException x){
        assertTrue(account.getUsername() == "123cool");
    }

    try {
        account.setEmail("");
    }catch(IllegalArgumentException x){
        assertTrue(account.getEmail() == "wow@cool.ca");
    }

    try {
        account.setCity("");
    }catch(IllegalArgumentException x){
        assertTrue(account.getCity() == "Grande Prairie");
    }

    //test the email exception and the no spaces in username exception (cities can have spaces, Eg: Grande Prairie)
    try {
        account.setEmail("yahoo.ca");
    }catch(IllegalEmailException e){
        assertTrue(account.getEmail() == "wow@cool.ca");
    }
    try {
        account.setUsername("hello goodbye");
    }catch(NoSpacesException n){
        assertTrue(account.getUsername()=="123cool");
    }
    try {
        account.setEmail("s p a c e s @ u . ca");
    }catch(NoSpacesException n){
        assertTrue(account.getEmail()=="wow@cool.ca");
    }

    //test the length constraints
    try {
        account.setUsername("iWannaBeTheVeryBestLikeNoOneEverWasToCatchThemIsMyRealTestToTrainThemIsMyCausePokemonIWillTravelAcrossTheLandSearching");
    }catch(TooLongException n){
        assertTrue(account.getUsername()=="123cool");
    }
    try {
        account.setEmail("FarAndWideEachPokemonToUnderstandThePowerThatsInsidePokemonOhItsYouAndMeIKnowItsMyDestiny@ualberta.ca");
    }catch(TooLongException n){
        assertTrue(account.getEmail()=="wow@cool.ca");
    }
    try {
        account.setCity("ohYoureMyBestFriendInAWorldWeMustDefendPokemonAHeartSoTrueOurCourageWillPullUsThroughYouTeachMeAndIllTeachYouPokemon");
    }catch(TooLongException n){
        assertTrue(account.getCity()=="Grande Prairie");
    }

    //test to see that the account creates an empty inventory:

    Inventory inventory = new Inventory();
    assertTrue(account.getInventory().getSize()==inventory.getSize());
    assertTrue(account.getInventory().getSize()==0);

    //test to see that the account creates an empty friendlist:
    Friends friends = new Friends();
    assertTrue(account.getFriends().getSize()==friends.getSize());
    assertTrue(account.getFriends().getSize()==0);
}

    }


