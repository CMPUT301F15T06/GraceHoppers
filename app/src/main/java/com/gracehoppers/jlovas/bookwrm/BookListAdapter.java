package com.gracehoppers.jlovas.bookwrm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The Booklist Adapter is used to display the list of books in a listview.
 *
 * @see HomeScreen, FriendProfileScreen
 *
 * @author Hong Chen
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
            textview.setText("Book Name: " + bookArray.get(position).getTitle() + "\nAuthor: " + bookArray.get(position).getAuthor());

        } else {

            TextView textview = (TextView) convertView.findViewById(R.id.inventory_id);
            textview.setText("Book Name: " + bookArray.get(position).getTitle() + "\nAuthor: " + bookArray.get(position).getAuthor());
        }

        return convertView;
    }
}