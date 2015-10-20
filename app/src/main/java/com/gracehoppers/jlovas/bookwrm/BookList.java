package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;

/**
 * Created by jlovas on 10/17/15.
 */
public class BookList {
    private ArrayList<Book> books;

    public BookList() {
        this.books = new ArrayList<Book>();
    }

    public void addBook(Book book){

    }

    public void deleteBook(Book book){

    }

    public boolean hasBook(Book book){
        return true; //for now
    }

    public void getBook(Book book){
        //how are we going to implement this? find by name?
        //shouldnt the parameter be a string? (not the book) and
        //the return value the book object?
    }
}
