package com.gracehoppers.jlovas.bookwrm;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by chen1 on 11/29/15.
 */
public class TopTraderTest extends ActivityInstrumentationTestCase2 {
    public TopTraderTest(){
        super(TopTraderTrackManager.class);
    }
    public void testGetTopTrader() throws NoSpacesException, TooLongException {
        TopTraderTrackManager topTraderTrackManager = new TopTraderTrackManager();

        Trade tradeA = new Trade();
        Trade tradeB = new Trade();
        Trade tradeC = new Trade();

        tradeA.setStatus(TradeStatus.ACCEPTED);
        tradeB.setStatus(TradeStatus.ACCEPTED);
        tradeC.setStatus(TradeStatus.ACCEPTED);

        Account accountA = new Account();
        Account accountB = new Account();
        Account accountC = new Account();

        accountC.getTradeHistory().addTrade(tradeA);
        accountB.getTradeHistory().addTrade(tradeB);
        accountB.getTradeHistory().addTrade(tradeC);

        accountA.setUsername("A");
        accountB.setUsername("B");
        accountC.setUsername("C");

        Accounts accounts = new Accounts();
        accounts.add(accountA);
        accounts.add(accountB);
        accounts.add(accountC);


        ArrayList result = new ArrayList();
        result = topTraderTrackManager.calculateScores(accounts);
        assertTrue(result.get(0).equals("B"));
        assertTrue(result.get(2).equals("C"));

    }
}
