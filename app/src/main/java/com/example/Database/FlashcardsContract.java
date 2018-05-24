package com.example.Database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.Database.AppProvider.CONTENT_AUTHORITY;
import static com.example.Database.AppProvider.CONTENT_AUTHORITY_URI;

public class FlashcardsContract {
    static final String TABLE_NAME = "Flashcards";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String FLASHCARDS_SET_ID = "Set_id";
        public static final String FLASHCARDS_WORD_PL = "Word_pl";
        public static final String FLASHCARDS_WORD_EN = "Word_en";

        private Columns() {

        }
    }

    /**
     * The URI to access the Flashcards tables
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildFlashcardUri(long taskId) {
        return ContentUris.withAppendedId(CONTENT_URI, taskId);
    }

    public static long getFlashcardId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
