package com.gracehoppers.jlovas.bookwrm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Will be restricted to having 5 photos - no ading your entire life story in pictures! >:0
 *
 * Created by jlovas on 11/13/15.
 */
public class Photos {
    private ArrayList<Bitmap> photos;

    public Photos(){
        photos = new ArrayList<>();
        //insert a default image like some grey image, not sure if i need this or not anymore
        Bitmap defaultImage = BitmapFactory.decodeFile("defaultbook.png");
        photos.add(defaultImage);
    }

    /**
     * This method will return the book image at the specified index location
     *
     * @param index index location of the book
     * @return Bitmap book image at index location
     * @throws NegativeNumberException
     * @throws TooLongException
     */
    public Bitmap getPhotoAtIndex(int index) throws NegativeNumberException, TooLongException {
        if(index <0) {
            throw new NegativeNumberException();
        }
        else if(index >photos.size()) {
            throw new TooLongException();
        }else if(index >4){
            throw new TooLongException();
        }
        else
            return photos.get(index);
    }

    public void addPhoto(Bitmap photo) throws TooLongException {
        //some sort of protection?
        if(photos.size()==5){
            throw new TooLongException();
        }else
            photos.add(photo);
    }
}
