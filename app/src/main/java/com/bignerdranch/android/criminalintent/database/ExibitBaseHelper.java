package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.criminalintent.database.ExibitDbSchema.ArtTable;

public class ExibitBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "ExibitBaseHelper";
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "crimeBase.db";

    public ExibitBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + ArtTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ArtTable.Cols.UUID + ", " +
                ArtTable.Cols.TITLE + ", " +
                ArtTable.Cols.DATE + ", " +
                ArtTable.Cols.SOLVED + ", " +
                ArtTable.Cols.SUSPECT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
