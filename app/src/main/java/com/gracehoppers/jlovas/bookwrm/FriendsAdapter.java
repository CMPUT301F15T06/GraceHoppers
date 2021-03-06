package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * The Friendlist Adapter is used to display the list of your friends in a listview.
 *
 * @see Friends
 *
 * @author Patricia Reyes, Jillian Lovas
 */
public class FriendsAdapter extends ArrayAdapter<String>{
    private Context context;
    private ArrayList<String> friendArray;

    public FriendsAdapter(Context context, int resource,
                           ArrayList<String> strings) {
        super(context, resource, strings);
        this.context = context;
        this.friendArray = strings;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friend_list, null, false);
            TextView textview = (TextView) convertView.findViewById(R.id.friend_id);
            textview.setText("\nUsername: " + friendArray.get(position) );

        } else {

            TextView textview = (TextView) convertView.findViewById(R.id.friend_id);
            textview.setText("\nUsername: " + friendArray.get(position));
        }

        return convertView;
    }
}
