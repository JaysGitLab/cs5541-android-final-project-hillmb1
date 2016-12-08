package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Gallery {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mFaved;
    private String mCreator;

    public Exibit[] getItems() {
        return items;
    }

    public void setItems(Exibit[] items) {
        this.items = items;
    }

    private Exibit[] items;

    public Gallery() {
        this(UUID.randomUUID());
    }

    public Gallery(UUID id) {
        mId = id;
    }
    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isFaved() {
        return mFaved;
    }

    public void setFaved(boolean faved) {
        mFaved = faved;
    }

    public String getCreator() {
        return mCreator;
    }

    public void setCreator(String creator) {
        mCreator = creator;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
