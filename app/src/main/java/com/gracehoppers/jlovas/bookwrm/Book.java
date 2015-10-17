package com.gracehoppers.jlovas.bookwrm;

import android.graphics.Bitmap;

/**
 * Created by jlovas on 10/17/15.
 * Hello I am a book!
 * I contain:
 *  -the name of the book
 *  -author
 *  -quantity
 *  -category (easier as an enum I would think)
 *  -private/public view ability
 *  -comments/description
 *  -photo (will figure this out in time)
 *
 *  -setters and getters for each
 */
public class Book {

    private enum Category{
            //Enum will give each a number by default. NONE=0, HARDBACK=1, etc
        NONE, HARDBACK, PAPERBACK, AUDIOBOOK, COMIC, TEXTBOOK, PICTURE, BRAILLE, REFERENCE, RECIPE, DIY;

    }

    private String title;
    private String author;
    private int quantity;
    private Category category;
    private boolean isPrivate;
    private String description;
    //private Bitmap photo; //will figure this one out later


    public Book() {
        //no args? just creates a book
        //default values here
        this.title = "Untitled";
        this.author= "No author specified";
        this.quantity=1;
        this.category=category.NONE;
        this.isPrivate=false; //false= public, true=private
        this.description = "None";
        //this.photo=0; //insert a default image like some grey image
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        this.description = description;
    }
/*
will return to this when i understand it better
    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
    */
}