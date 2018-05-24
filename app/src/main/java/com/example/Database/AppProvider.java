package com.example.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


public class AppProvider extends ContentProvider {
    private static final String TAG = "AppProvider";

    private AppDatabase appDatabase;

    public static final UriMatcher uriMatcher = buildUriMatcher();

    static final String CONTENT_AUTHORITY = "com.example.Database.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int SETS = 200;
    private static final int SETS_ID = 201;

    private static final int FLASHCARDS = 250;
    private static final int FLASHCARDS_ID = 251;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CONTENT_AUTHORITY, SetsContract.TABLE_NAME, SETS);
        matcher.addURI(CONTENT_AUTHORITY, SetsContract.TABLE_NAME + "/#", SETS_ID);

        matcher.addURI(CONTENT_AUTHORITY, FlashcardsContract.TABLE_NAME, FLASHCARDS);
        matcher.addURI(CONTENT_AUTHORITY, FlashcardsContract.TABLE_NAME + "/#", FLASHCARDS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        appDatabase = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: called with URI " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case SETS:
                queryBuilder.setTables(SetsContract.TABLE_NAME);
                break;

            case SETS_ID:
                queryBuilder.setTables(SetsContract.TABLE_NAME);
                long setId = SetsContract.getSetId(uri);
                queryBuilder.appendWhere(SetsContract.Columns._ID + " = " + setId);
                break;

            case FLASHCARDS:
                queryBuilder.setTables(FlashcardsContract.TABLE_NAME);
                break;

            case FLASHCARDS_ID:
                queryBuilder.setTables(FlashcardsContract.TABLE_NAME);
                long flashcardId = FlashcardsContract.getFlashcardId(uri);
                queryBuilder.appendWhere(FlashcardsContract.Columns._ID + " = " + flashcardId);
                break;


            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = appDatabase.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case SETS:
                return SetsContract.CONTENT_TYPE;

            case SETS_ID:
                return SetsContract.CONTENT_ITEM_TYPE;

            case FLASHCARDS:
                return FlashcardsContract.CONTENT_TYPE;

            case FLASHCARDS_ID:
                return FlashcardsContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "Entering insert, called with uri:" + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        // nie wywolujemy tutaj appDatabase.getWritableDatabase(); bo jest to dosc wolna operacja
        // i jezeli match nie bedzie nigdzie pasowal to niepotrzebnie obciazymy telefon
        final SQLiteDatabase db;

        Uri returnUri;
        long recordId;

        switch (match) {

            case SETS:
                db = appDatabase.getWritableDatabase();
                recordId = db.insert(SetsContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = SetsContract.buildSetUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;

            case FLASHCARDS:
                db = appDatabase.getWritableDatabase();
                recordId = db.insert(FlashcardsContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = FlashcardsContract.buildFlashcardUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting insert, returning " + returnUri);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update called with uri " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {

            case SETS:
                db = appDatabase.getWritableDatabase();
                count = db.delete(SetsContract.TABLE_NAME, selection, selectionArgs);
                break;

            case SETS_ID:
                db = appDatabase.getWritableDatabase();
                long setId = SetsContract.getSetId(uri);
                selectionCriteria = SetsContract.Columns._ID + " = " + setId;

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(SetsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case FLASHCARDS:
                db = appDatabase.getWritableDatabase();
                count = db.delete(FlashcardsContract.TABLE_NAME, selection, selectionArgs);
                break;

            case FLASHCARDS_ID:
                db = appDatabase.getWritableDatabase();
                long flashcardId = FlashcardsContract.getFlashcardId(uri);
                selectionCriteria = FlashcardsContract.Columns._ID + " = " + flashcardId;

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(FlashcardsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update called with uri " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {

            case SETS:
                db = appDatabase.getWritableDatabase();
                count = db.update(SetsContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case SETS_ID:
                db = appDatabase.getWritableDatabase();
                long setId = SetsContract.getSetId(uri);
                selectionCriteria = SetsContract.Columns._ID + " = " + setId;

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(SetsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case FLASHCARDS:
                db = appDatabase.getWritableDatabase();
                count = db.update(FlashcardsContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case FLASHCARDS_ID:
                db = appDatabase.getWritableDatabase();
                long flashcardId = FlashcardsContract.getFlashcardId(uri);
                selectionCriteria = FlashcardsContract.Columns._ID + " = " + flashcardId;

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(FlashcardsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;
                        default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }
}