package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Created by chen1 on 11/27/15.
 */
public class TopTraderTrackManager {

    private ArrayList allTraders = new ArrayList();

    public void setTrader(String username, int score){
        allTraders.add(username);
        allTraders.add(score);
    }

    public ArrayList calculateScores(Accounts allAccounts){
        Account account;
        int score = 0;
        for (int i = 0; i < allAccounts.size(); i++){
            score = 0;
            account = allAccounts.get(i);

            int i2 = 0;
            while (i2 < account.getTradeHistory().getSize()){
                if (account.getTradeHistory() != null){
                    try {
                        if (!(account.getTradeHistory().getTradeHistory().get(i2).getDeclined())) {
                            score++;
                        }
                    }catch (RuntimeException e){}
                }
            i2 ++;
            }

            setTrader(account.getUsername(),score);
        }

        ArrayList resultList = new ArrayList();

        while (allTraders.size()>0) {
            int i2 = 1;
            int max = 0;
            int position = 1;
            while (i2 < allTraders.size()) {
                if ((int)allTraders.get(i2) > max){
                    max = (int) allTraders.get(i2);
                    position = i2;
                }
                i2 = i2 + 2;
            }
            resultList.add(allTraders.get(position-1));
            resultList.add(allTraders.get(position));
            allTraders.remove(position-1);
            allTraders.remove(position-1);
        }


        return resultList;
    }


}
