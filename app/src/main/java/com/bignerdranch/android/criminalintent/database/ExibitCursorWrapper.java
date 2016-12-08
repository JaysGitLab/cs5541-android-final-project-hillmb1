package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.Exibit;

import java.util.Date;
import java.util.UUID;

import com.bignerdranch.android.criminalintent.database.ExibitDbSchema.ArtTable;

public class ExibitCursorWrapper extends CursorWrapper {
    public ExibitCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Exibit getCrime() {
        String uuidString = getString(getColumnIndex(ArtTable.Cols.UUID));
        String title = getString(getColumnIndex(ArtTable.Cols.TITLE));
        long date = getLong(getColumnIndex(ArtTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(ArtTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(ArtTable.Cols.SUSPECT));

        Exibit exibit = new Exibit(UUID.fromString(uuidString));
        exibit.setTitle(title);
        exibit.setDate(new Date(date));
        exibit.setFaved(isSolved != 0);
        exibit.setCreator(suspect);

        return exibit;
    }
}
