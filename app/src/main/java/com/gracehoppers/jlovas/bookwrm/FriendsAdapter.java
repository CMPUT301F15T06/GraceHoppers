package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by ljuarezr on 10/28/15.
 */
public class FriendsAdapter extends ArrayAdapter<Account>{
    private Context context;
    private ArrayList<Account> friendArray;

    public FriendsAdapter(Context context, int resource,
                           ArrayList<Account> objects) {
        super(context, resource, objects);
        this.context = context;
        this.friendArray = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friend_list, null, false);
            TextView textview = (TextView) convertView.findViewById(R.id.friend_id);
            textview.setText("\nUsername: " + friendArray.get(position).getUsername() + "\nCity: " + friendArray.get(position).getCity());

        } else {

            TextView textview = (TextView) convertView.findViewById(R.id.friend_id);
            textview.setText("\nUsername: " + friendArray.get(position).getUsername() + "\nCity: " + friendArray.get(position).getCity());
        }

        return convertView;
    }
}
