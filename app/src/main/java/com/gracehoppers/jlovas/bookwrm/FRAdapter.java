package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * an adapter for friend requests with the sender's name shown
 * Created by nlovas on 11/19/15.
 */
public class FRAdapter extends ArrayAdapter<FriendRequest> {

    private Context context;
    private ArrayList<FriendRequest> friendArray;

    public FRAdapter(Context context, int resource,
                          ArrayList<FriendRequest> objects) {
        super(context, resource, objects);
        this.context = context;
        this.friendArray = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.frlist, null, false);
            TextView textview = (TextView) convertView.findViewById(R.id.frlist_id);
            textview.setText("\nUsername: " + friendArray.get(position).getSender());

        } else {

            TextView textview = (TextView) convertView.findViewById(R.id.frlist_id);
            textview.setText("\nUsername: " + friendArray.get(position).getSender());
        }

        return convertView;
    }

}
