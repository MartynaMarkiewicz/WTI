package com.example.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";

    public static final String DATABASE_NAME = "WTI.db";
    public static final int DATABASE_VERSION = 1;

    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSetsTable());
        db.execSQL(createFlashCardsTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                // upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion: " + newVersion);
        }
    }

    private String createSetsTable() {
        return  "CREATE TABLE " + SetsContract.TABLE_NAME + " ("
                + SetsContract.Columns._ID          + " INTEGER PRIMARY KEY NOT NULL, "
                + SetsContract.Columns.SETS_USER_ID + " INTEGER(10) NOT NULL, "
                + SetsContract.Columns.SETS_NAME    + " VARCHAR(40) NOT NULL)";
    }

    private String createFlashCardsTable() {
        return  "CREATE TABLE " + FlashcardsContract.TABLE_NAME + " ("
                + FlashcardsContract.Columns._ID                + " INTEGER PRIMARY KEY NOT NULL, "
                + FlashcardsContract.Columns.FLASHCARDS_SET_ID  + " INTEGER(10) NOT NULL, "
                + FlashcardsContract.Columns.FLASHCARDS_WORD_PL + " VARCHAR(50) NOT NULL, "
                + FlashcardsContract.Columns.FLASHCARDS_WORD_EN + " VARCHAR(50) NOT NULL)";
    }
}
