package com.gracehoppers.jlovas.bookwrm;
/**
 * Created by chen1 on 10/19/15.
 */
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds all the trades ever because a history of trades is everything
 * Current and past trades, as specified in OS 20.
 * Contains methods addTrade and get TradebyIndex
 * @see Trade
 *
 * @author chen1, ljuarezr
 */
public class TradeHistory implements Serializable{
    ArrayList<Trade> tradeHistory = new ArrayList<Trade>();

    public TradeHistory(){
        tradeHistory = new ArrayList<Trade>();
    }

    //add the trade into the list of trades
    public void addTrade(Trade trade){
        tradeHistory.add(trade);
    }


    public ArrayList<Trade> getTradeHistory(){
        return tradeHistory;
    }


    public Trade getTradeByIndex(int i) throws NegativeNumberException, TooLongException{
        if (i<0){
            throw new NegativeNumberException();
        } else if(i>= tradeHistory.size()) { //if requested position exceeds list size
            throw new TooLongException();
        } else

            return tradeHistory.get(i);
    }

    //deleted the delete() method. Don't need one. Not in the requirements.
    //deleted the clear() method. Don't need one. Not in the requirements.
}
