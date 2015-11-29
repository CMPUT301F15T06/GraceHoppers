package com.gracehoppers.jlovas.bookwrm;

import android.graphics.Bitmap;

/**
 * BitMapScaler helps to scale down the bitmaps.
 * Credit to: Codepath for this scaler class
 * https://guides.codepath.com/android/Working-with-the-ImageView#scaling-a-bitmap, Nov 14th, 2015
 *
 * @author jlovas on 11/14/15.
 *
 */
public class BitmapScaler {

        // Scale and maintain aspect ratio given a desired width
        // BitmapScaler.scaleToFitWidth(bitmap, 100);
        public static Bitmap scaleToFitWidth(Bitmap b, int width){
            float factor = width / (float) b.getWidth();
            return Bitmap.createScaledBitmap(b, width, (int) (b.getHeight() * factor), true);
        }

        // Scale and maintain aspect ratio given a desired height
        // BitmapScaler.scaleToFitHeight(bitmap, 100);
        public static Bitmap scaleToFitHeight(Bitmap b, int height){
            float factor = height / (float) b.getHeight();
            return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor), height, true);
        }
}
