package com.example.codetribe.miwok;

/**
 * Created by Codetribe on 11/3/2016.
 */
public class Word {

    private String DefaultTranslation;
    private String MiwokTranslation;
    private int mImageRource = NO_IMAGE;
    private static final int NO_IMAGE = -1;

    public Word(String defaultTranslation, String miwokTranslation, int imageRource) {
        DefaultTranslation = defaultTranslation;
        mImageRource = imageRource;
        MiwokTranslation = miwokTranslation;
    }

    public Word(String defaultTranslation, String miwokTranslation) {
        DefaultTranslation = defaultTranslation;
        MiwokTranslation = miwokTranslation;

    }

    public int getImageRource() {
        return mImageRource;
    }

    public String getDefaultTranslation() {

        return DefaultTranslation;
    }

    public String getMiwokTranslation() {

        return MiwokTranslation;
    }

    public boolean hasImage(){

        boolean img;
        if (mImageRource != NO_IMAGE) {
            img = true;
        }
        else {
            img = false;
        }
        return img;
    }
}
