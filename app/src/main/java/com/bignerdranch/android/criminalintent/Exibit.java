package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Exibit {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mFaved;
    private String mCreator;

    public Exibit() {
        this(UUID.randomUUID());
    }

    public Exibit(UUID id) {
        mId = id;
        mDate = new Date();
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
