package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by chen1 on 10/19/15.
 */
public class Inventory {
    public Inventory() {
    }

    ArrayList inventory = new ArrayList<Book>();

    public void addBook(Book book) {
        inventory.add(book);
    }

    public void deleteBook(Book book) {
        inventory.remove(book);
    }

    // IMPORTANT: to edit an item book, delete the book from the inventory first and then add the modified one after editing

    public boolean hasBook(Book book) {
        return inventory.contains(book);
    }

    public Book getBookByTitle(String name) {
        Book returnedBook = new Book();
        for (int i = 0; i <= inventory.size(); i++) {
            Book book = (Book) inventory.get(i);
            if (name == book.getTitle()) {
                returnedBook = book;
            }
        }
        return returnedBook;
    }
}