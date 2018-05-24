package com.example.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.UserDictionary;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;

import com.example.aplikacja_screen.MainActivity;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class Database {

    private static final String TAG = "Database";

    private ContentResolver contentResolver;

    public Database(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // Wszystkie nowe funkcje dajemy pod tym komentarzem

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //funkcja dla wyświetlenia zestawów
    public Cursor getSets() {
        String[] projection = {
                SetsContract.Columns._ID,
                SetsContract.Columns.SETS_USER_ID,
                SetsContract.Columns.SETS_NAME};

        Cursor cursor = contentResolver.query(SetsContract.CONTENT_URI,
                projection,
                null,
                null,
                SetsContract.Columns._ID);
        return cursor;
    }

    //usuwanie zestawu o podanym ID
    public void deleteFromSets(int setId) {
        String selection = SetsContract.Columns._ID + " = ?";
        String[] args = {String.valueOf(setId)};
        int count = contentResolver.delete(SetsContract.CONTENT_URI, selection, args);
    }

    //funkcja dla wyświetlania fiszek
    public Cursor getFlashcards() {
        String[] projection = {
                FlashcardsContract.Columns._ID,
                FlashcardsContract.Columns.FLASHCARDS_SET_ID,
                FlashcardsContract.Columns.FLASHCARDS_WORD_PL,
                FlashcardsContract.Columns.FLASHCARDS_WORD_EN};

        Cursor cursor = contentResolver.query(FlashcardsContract.CONTENT_URI,
                projection,
                null,
                null,
                FlashcardsContract.Columns._ID);
        return cursor;
    }

    //usuwanie fiszki o podanym ID
    public void deleteFromFlashcards(int flashcardID) {
        String selection = FlashcardsContract.Columns._ID + " =?";
        String[] args = {String.valueOf(flashcardID)};
        int count = contentResolver.delete(FlashcardsContract.CONTENT_URI, selection, args);
    }

    //usuwanie wszystkich fiszek z zestawu o podanym ID
    public void deleteFlashcards(int setID){
        String selection = FlashcardsContract.Columns.FLASHCARDS_SET_ID + " =?";
        String[] args = {String.valueOf((setID))};
        int count = contentResolver.delete(FlashcardsContract.CONTENT_URI, selection, args);
    }

    //edytowanie fiszek
    public void updateFlashcards(int flashCardsID, int flashCardsSetID, String wordPL, String wordEN) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FlashcardsContract.Columns.FLASHCARDS_SET_ID, flashCardsSetID);
        contentValues.put(FlashcardsContract.Columns.FLASHCARDS_WORD_PL, wordPL);
        contentValues.put(FlashcardsContract.Columns.FLASHCARDS_WORD_EN, wordEN);
        String selection = FlashcardsContract.Columns._ID + " =?";
        String[] args = {String.valueOf(flashCardsID)};
        int count = contentResolver.update(FlashcardsContract.CONTENT_URI, contentValues, selection, args);
    }


    // Wszystkie nowe funkcje dajemy nad tym komentarzem
    public Uri insertIntoSets(int userId, String setName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SetsContract.Columns.SETS_USER_ID, userId);
        contentValues.put(SetsContract.Columns.SETS_NAME, setName);
        Uri uri = contentResolver.insert(SetsContract.CONTENT_URI, contentValues);
        return uri;
    }

    public void querySets() {
        String[] projection = {
                SetsContract.Columns._ID,
                SetsContract.Columns.SETS_USER_ID,
                SetsContract.Columns.SETS_NAME};

        Cursor cursor = contentResolver.query(SetsContract.CONTENT_URI,
                projection,
                null,
                null,
                SetsContract.Columns._ID);

        if (cursor != null) {
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: ===========================");
            }
            cursor.close();
        }
    }

    public Uri insertIntoFlashCards(int setId, String wordPl, String wordEn) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FlashcardsContract.Columns.FLASHCARDS_SET_ID, setId);
        contentValues.put(FlashcardsContract.Columns.FLASHCARDS_WORD_PL, wordPl);
        contentValues.put(FlashcardsContract.Columns.FLASHCARDS_WORD_EN, wordEn);
        Uri uri = contentResolver.insert(FlashcardsContract.CONTENT_URI, contentValues);
        return uri;
    }

    public void queryFlashCards() {
        String[] projection = {
                FlashcardsContract.Columns._ID,
                FlashcardsContract.Columns.FLASHCARDS_SET_ID,
                FlashcardsContract.Columns.FLASHCARDS_WORD_PL,
                FlashcardsContract.Columns.FLASHCARDS_WORD_EN};

        Cursor cursor = contentResolver.query(FlashcardsContract.CONTENT_URI,
                projection,
                null,
                null,
                FlashcardsContract.Columns._ID);

        if (cursor != null) {
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: ===========================");
            }
            cursor.close();
        }
    }

}
