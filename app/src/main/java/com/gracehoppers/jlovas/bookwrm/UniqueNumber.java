package com.gracehoppers.jlovas.bookwrm;

/**
 * Holds the unique number for each book (each book has a unique number).
 *
 * @author Nicole Lovas
 */
public class UniqueNumber {

    private int number=-1;

    public void inc(){
        this.number++;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
