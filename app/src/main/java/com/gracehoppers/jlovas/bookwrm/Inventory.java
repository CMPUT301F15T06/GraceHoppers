package com.gracehoppers.jlovas.bookwrm;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * This class handles the user's inventory. It fills with instances of books for the user to trade with.
 *
 * @see Book, HomeScreen
 *
 * @author Hong Chen
 */
public class Inventory implements Serializable {
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

    /**
     * This method detects if the book is in the user's inventory.
     * @param book book to check if already in the inventory
     * @return true or false based on if the book is in the inventory or not
     */
    public boolean hasBook(Book book) {
        return inventory.contains(book);
    }

    public int getSize(){
        return inventory.size();
    }

    /**
     * getBookByTitle will find a book in the inventory by its name
     * @param name the name of the book to find in your inventory
     * @return the books with the same name (a list for duplicate books)
     * @throws ItemNotFoundException
     */
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

    /**
     * getBookByIndex will return a book based on the index it has been stored
     * @param i index location
     * @return the book at the index location
     * @throws NegativeNumberException
     * @throws TooLongException
     */
    public Book getBookByIndex(int i) throws NegativeNumberException, TooLongException{
        if(i <0){
            throw new NegativeNumberException();
        }else if(i>=inventory.size()){ //if the requested position exceeds inventory size, throw exception
            throw new TooLongException();
        }else

        return inventory.get(i);
    }

    /**
     * return all public items in the inventory in the form of another inventory
     * @param inventory
     * @return
     */
    public ArrayList<Book> getPublic(Inventory inventory){
        ArrayList<Book> publicInventory = new ArrayList<Book>();
        for(int i=0;i<inventory.getSize();i++){
            try {
                if (inventory.getBookByIndex(i).isPrivate() == false) { //if the book is public
                    publicInventory.add(inventory.getBookByIndex(i));
                }
            }catch (NegativeNumberException e){
                e.getStackTrace();
            }catch(TooLongException e){
                e.getStackTrace();
            }
        }
        return publicInventory;
    }

    /**
     * gets a book that matches a given unique number
     * @param uninum
     * @return Book
     */
    public Book getBookByUniqueNumber(int uninum){
Log.e("uninum:",uninum+"");
        try {
            for (int i = 0; i < this.getSize(); i++) {
                if (this.getBookByIndex(i).getUniquenum().getNumber() == uninum) {
                    return this.getBookByIndex(i);
                }
            }
        }catch(NegativeNumberException e){

        }catch(TooLongException e){

        }
        return null; //wouldnt happen
    }

    /**
     * gets the position of a book from its uniquenumber
     * @param uninum
     * @return int
     */
    public int getRealPosition(int uninum){
        //get the position from the total list so that createtrade activity doesnt need to be refactored
        try {
            for (int i = 0; i < this.getSize(); i++) {
                if (this.getBookByIndex(i).getUniquenum().getNumber() == uninum) {
                    return i;
                }
            }
        }catch(NegativeNumberException e){

        }catch(TooLongException e){

        }
        return 0; //wouldnt happen
    }

}
