package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Holds all the trades ever because a history of trades is everything
 * Current and past trades, as specified in OS 20.
 */
public class TradeHistory {
    ArrayList<Trade> tradeHistory;

    public TradeHistory(){
        tradeHistory = new ArrayList<Trade>();
    }

    //add the trade into the list of trades
    public void addTrade(Trade trade){
        tradeHistory.add(trade);
    }

    //delete the trade
    public void deleteTrade(Trade trade){
        tradeHistory.remove(trade);
    }

    /* Not sure yet until trade is written more, how will we call up a trade?
    public Trade getTradeWithUser(Account username){
        return ;
    }
    */

    //clear the history of trades (but keep current trades)
    public void clearHistory(){
        //check through list, put current trades into a temp list
        //copy back or assign historylist to newly made list
    }
}
