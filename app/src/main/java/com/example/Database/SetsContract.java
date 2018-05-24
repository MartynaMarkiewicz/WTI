package com.example.Database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.Database.AppProvider.CONTENT_AUTHORITY;
import static com.example.Database.AppProvider.CONTENT_AUTHORITY_URI;

public class SetsContract {
    static final String TABLE_NAME = "Sets";

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String SETS_USER_ID = "User_id";
        public static final String SETS_NAME = "Name";

        private Columns() {

        }
    }

    /**
     * The URI to access the Sets tables
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildSetUri(long taskId) {
        return ContentUris.withAppendedId(CONTENT_URI, taskId);
    }

    public static long getSetId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
