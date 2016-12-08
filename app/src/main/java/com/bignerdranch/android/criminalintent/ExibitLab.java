package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.criminalintent.database.ExibitBaseHelper;
import com.bignerdranch.android.criminalintent.database.ExibitCursorWrapper;

import com.bignerdranch.android.criminalintent.database.ExibitDbSchema.ArtTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExibitLab {
    private static ExibitLab sExibitLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ExibitLab get(Context context) {
        if (sExibitLab == null) {
            sExibitLab = new ExibitLab(context);
        }
        return sExibitLab;
    }

    private ExibitLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ExibitBaseHelper(mContext)
                .getWritableDatabase();
    }


    public void addCrime(Exibit c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(ArtTable.NAME, null, values);
    }

    public List<Exibit> getExibits() {
        List<Exibit> exibits = new ArrayList<>();

        ExibitCursorWrapper cursor = queryCrimes(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            exibits.add(cursor.getCrime());
            cursor.moveToNext();
        }
        cursor.close();

        return exibits;
    }

    public Exibit getCrime(UUID id) {
        ExibitCursorWrapper cursor = queryCrimes(
                ArtTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Exibit exibit) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, exibit.getPhotoFilename());
    }

    public void updateCrime(Exibit exibit) {
        String uuidString = exibit.getId().toString();
        ContentValues values = getContentValues(exibit);

        mDatabase.update(ArtTable.NAME, values,
                ArtTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Exibit exibit) {
        ContentValues values = new ContentValues();
        values.put(ArtTable.Cols.UUID, exibit.getId().toString());
        values.put(ArtTable.Cols.TITLE, exibit.getTitle());
        values.put(ArtTable.Cols.DATE, exibit.getDate().getTime());
        values.put(ArtTable.Cols.SOLVED, exibit.isFaved() ? 1 : 0);
        values.put(ArtTable.Cols.SUSPECT, exibit.getCreator());

        return values;
    }

    private ExibitCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ArtTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new ExibitCursorWrapper(cursor);
    }
}
