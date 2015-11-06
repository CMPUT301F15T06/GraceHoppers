package com.gracehoppers.jlovas.bookwrm;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by chen1 on 10/19/15.
 */
public class Inventory {
    public Inventory() {
    }

    ArrayList<Book> inventory = new ArrayList<Book>();

    public void addBook(Book book) {
        inventory.add(0, book);
    }

    public void deleteBook(Book book) {
        inventory.remove(book);
    }

       // IMPORTANT: to edit an item book, delete the book from the inventory first and then add the modified one after editing

    public boolean hasBook(Book book) {
        return inventory.contains(book);
    }

    public int getSize(){
        return inventory.size();
    }

    public ArrayList<Book> getBookByTitle(String name) throws ItemNotFoundException{
        ArrayList<Book> booklist = new ArrayList<Book>();

        for (int i = 0; i < inventory.size(); i++) {
            Book book = inventory.get(i);
            if (name.equals( book.getTitle())) {
                booklist.add(book);

            }
        }
        if(booklist.size()>0) return booklist;
        else
        //ItemNotFound will only be thrown if no books in the booklist
        throw new ItemNotFoundException();

    }

    public ArrayList<Book> getInventory(){
        return inventory;
    }

    public Book getBookByIndex(int i) throws NegativeNumberException, TooLongException{
        if(i <0){
            throw new NegativeNumberException();
        }else if(i>=inventory.size()){ //if the requested position exceeds inventory size, throw exception
            throw new TooLongException();
        }else

        return inventory.get(i);
    }
}
