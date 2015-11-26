package com.gracehoppers.jlovas.bookwrm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * The Book class creates instances of a book which contain the various information
 * about the book the user would like to trade.
 * <p>
 * The user sets the title, author, quantity, quality, category, privacy, and description.
 * </p>
 *
 * @author Jillian Lovas
 *
 * @see AddBookScreen, ViewBookActivity.
 */
public class Book {

    //***please check your book declarations; if there's a problem, it's probably because this was removed!
    //public Book(){

    //}

    private String title;
    private String author;
    private int quantity;
    private Category category;
    private boolean isPrivate;
    private String description;
    private double quality;
    private Photos photoList;
    private UniqueNumber uniquenum;




    /**
     * Constructor for the book creates a book with default values (to be overwritten when user provides information.
     * ***No longer takes an image as a parameter! Book will no longer hold the default image.
     *    The xml provides the default image until photos are detected and loaded.
     */
    public Book() {
        //no args? just creates a book
        //default values here - may remove these and put into setters
        this.title = "Untitled";
        this.author= "No author specified";
        this.quantity=1;
        this.category=category.NONE; //will see if this works the way I want it to
        this.isPrivate=false; //false= public, true=private
        this.description = "None";
        this.quality = 0; //default 0 for now?
        //one default photo
        photoList=new Photos();


    }

    public UniqueNumber getUniquenum() {
        return uniquenum;
    }

    /**
     * will be given from the server a unique number to identify the book. No two books have the same number
     *
     */
    public void setUniquenum(UniqueNumber uniquenum) {
       this.uniquenum=uniquenum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.isEmpty()){
            //throw an exception
            throw new IllegalArgumentException();
        }
        else {
            this.title = title;
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if(author.isEmpty()){
            //throw exception
            throw new IllegalArgumentException();
        }else {
            this.author = author;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * This method returns a string because it is easier to work with a String to display than
     * a number.
     *
     * @return a string of the category type.
     */
    public String getCategory() {

        switch (this.category) {
            case NONE:
                return "None";
            case HARDBACK:
                return "Hardback";
            case PAPERBACK:
                return "Paperback";
            case AUDIOBOOK:
                return "Audiobook";
            case COMIC:
                return "Comic";
            case TEXTBOOK:
                return "Textbook";
            case PICTURE:
                return "Picture";
            case BRAILLE:
                return "Braille";
            case REFERENCE:
                return "Reference";
            case RECIPE:
                return "Recipe";
            case DIY:
                return "DIY";
            default:
                return "None";
        }
    }

    public int getCategoryNumber() {

        switch (this.category) {
            case NONE:
                return 0;
            case HARDBACK:
                return 1;
            case PAPERBACK:
                return 2;
            case AUDIOBOOK:
                return 3;
            case COMIC:
                return 4;
            case TEXTBOOK:
                return 5;
            case PICTURE:
                return 6;
            case BRAILLE:
                return 7;
            case REFERENCE:
                return 8;
            case RECIPE:
                return 9;
            case DIY:
                return 10;
            default:
                return 0;
        }
    }

    public void setCategory(int category) {

        switch (category) {
            case 0:
                this.category = Category.NONE;
                break;
            case 1:
                this.category = Category.HARDBACK;
                break;
            case 2:
                this.category = Category.PAPERBACK;
                break;
            case 3:
                this.category = Category.AUDIOBOOK;
                break;
            case 4:
                this.category = Category.COMIC;
                break;
            case 5:
                this.category = Category.TEXTBOOK;
                break;
            case 6:
                this.category = Category.PICTURE;
                break;
            case 7:
                this.category = Category.BRAILLE;
                break;
            case 8:
                this.category = Category.REFERENCE;
                break;
            case 9:
                this.category = Category.RECIPE;
                break;
            case 10:
                this.category = Category.DIY;
                break;
            default:
                this.category = Category.NONE;
                break;
        }

    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.isEmpty()){
            this.description = "None";
        }else

            this.description = description;

    }

    public double getQuality(){
        return quality;
    }

    public void setQuality(double quality){
        this.quality= quality;
    }

    public void setQuantity(String quantity) throws NegativeNumberException {
        int converted =Integer.parseInt(quantity);
        if(converted <1){
            throw new NegativeNumberException();
        }
        else {
            this.quantity = converted;
        }
    }

    public Photos getPhotos(){
        return photoList;
    }

}