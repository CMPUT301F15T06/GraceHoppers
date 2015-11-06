package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chen1 on 11/3/15.
 */
public class TradeHistoryListAdapter extends ArrayAdapter<Trade> {

    /**
     * An Array Adapter used to populate the TradeHistory ListView.
     * @see TradeHistory
     *
     */

    private Context context;
    private ArrayList<Trade> tradeArray;

    public TradeHistoryListAdapter(Context context, int resource,
                           ArrayList<Trade> objects) {
        super(context, resource, objects);
        this.context = context;
        this.tradeArray = objects;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.trade_history_list, null, false);
            TextView textview = (TextView) convertView.findViewById(R.id.trade_inventory_id);
            textview.setText("Borrower: " + tradeArray.get(position).getBorrower().getUsername().toString() + "\nOwner: " + tradeArray.get(position).getOwner().getUsername().toString() + "\nBook" + tradeArray.get(position).getOwnerBook().getTitle().toString());

        } else {

            TextView textview = (TextView) convertView.findViewById(R.id.trade_inventory_id);
            textview.setText("Borrower: " + tradeArray.get(position).getBorrower().getUsername().toString() + "\nOwner: " + tradeArray.get(position).getOwner().getUsername().toString() + "\nBook"+ tradeArray.get(position).getOwnerBook().getTitle().toString());

        }

        return convertView;
    }
}