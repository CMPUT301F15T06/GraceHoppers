package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chen1 on 10/26/15.
 */
public class BookListAdapter extends ArrayAdapter<Book> {
    private Context context;
    private ArrayList<Book> bookArray;

    public BookListAdapter(Context context, int resource,
                     ArrayList<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.bookArray = objects;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_inventory_list, null, false);
            TextView textview = (TextView) convertView.findViewById(R.id.inventory_id);
            textview.setText(bookArray.get(position).getTitle());

        } else {

            TextView textview = (TextView) convertView.findViewById(R.id.inventory_id);
            textview.setText(bookArray.get(position).getTitle());
        }

        return convertView;
    }
}