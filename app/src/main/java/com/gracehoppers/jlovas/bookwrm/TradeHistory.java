package com.gracehoppers.jlovas.bookwrm;
/**
 * Created by chen1 on 10/19/15.
 */
import java.util.ArrayList;

/**
 * Holds all the trades ever because a history of trades is everything
 * Current and past trades, as specified in OS 20.
 * Contains methods addTrade and get TradebyIndex
 * @see Trade
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

    public Trade getTradeByIndex(int i){
        return tradeHistory.get(i);
    }

    /* Not sure yet until trade is written more, how will we call up a trade?
    public Trade getTradeWithUser(Account username){
        return ;
    }
    */

    //deleted the delete() method. Don't need one. Not in the requirements.
    //deleted the clear() method. Don't need one. Not in the requirements.
}
