package com.gracehoppers.jlovas.bookwrm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Will be restricted to having 5 photos - no ading your entire life story in pictures! >:0
 *
 * Created by jlovas on 11/13/15.
 */
public class Photos{
    private ArrayList<byte[]> photos;
    private boolean hasImages;

    public Photos(){
        photos = new ArrayList<>();

        //how this works:
        //if no images, this remains false and the app will not try to load the new images
        //if images, this is set to true, and will try to load the new images
        hasImages=false;
    }

    /**
     * This method will return the book image at the specified index location
     *
     * @param index index location of the book
     * @return Bitmap book image at index location
     * @throws NegativeNumberException
     * @throws TooLongException
     */
    public byte[] getPhotoAtIndex(int index) throws NegativeNumberException, TooLongException {
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

    public void addPhoto(byte[] b) throws TooLongException {
        //protection
        if(photos.size()==5){
            throw new TooLongException();
        }else {
            photos.add(0, b);
            setHasImages(true);
            }
    }



    public void swapPhotoAtIndex(int index, byte[] b) throws TooLongException, NegativeNumberException{
        if(index < 0){
            throw new NegativeNumberException();
        }else if(index >photos.size()){
            throw new TooLongException();
        }else{
            photos.set(index, b);
        }
    }

    public boolean getHasImages(){
        return hasImages;
    }

    public ArrayList<byte[]> getPhotos(){
        return photos;
    }

    public void setHasImages(boolean b){
        hasImages=b;
    }

    //takes a photo object and sets it all here
    public void setPhotoset(Photos myPhotos){
        photos.clear();
        //neds some protection if user didn't tak any photos, yes?
        for(int i=0; i < myPhotos.getPhotos().size(); i++)
        photos.add(i, myPhotos.getPhotos().get(i));

        if(myPhotos.getPhotos().size() >0){
            hasImages = true;
        }

    }
}
