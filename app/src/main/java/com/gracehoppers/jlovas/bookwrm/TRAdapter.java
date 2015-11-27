package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ljuarezr on 11/27/15.
 * @author ljuarezr (based on nlovas' FRAdapater)
 *
 *
 */
public class TRAdapter extends ArrayAdapter<TradeRequest> {
    private Context context;
    private ArrayList<TradeRequest> tradeArray;

    public TRAdapter(Context context, int resource,
                     ArrayList<TradeRequest> objects) {
        super(context, resource, objects);
        this.context = context;
        this.tradeArray = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.frlist, null, false);
            TextView textview = (TextView) convertView.findViewById(R.id.frlist_id);
            textview.setText("\nUsername: " + tradeArray.get(position).getSender() +
                            "\nBooks for trade: " + tradeArray.get(position).getOwnerBook()+
                            "; " + tradeArray.get(position).getBorrowerBook());

        } else {

            TextView textview = (TextView) convertView.findViewById(R.id.frlist_id);
            textview.setText("\nUsername: " + tradeArray.get(position).getSender()+
                    "\nBooks for trade: " + tradeArray.get(position).getOwnerBook()+
                    "; " + tradeArray.get(position).getBorrowerBook());
        }

        return convertView;
    }



}
