package com.chembrovich.bsuir.banknews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chembrovich.bsuir.banknews.database.NewsContract.NewsEntry;

public class NewsDbHelper extends SQLiteOpenHelper {
    private DatabaseCreationCallback dbCreationCallback;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NewsEntry.TABLE_NAME + " (" +
                    NewsEntry._ID + " INTEGER PRIMARY KEY," +
                    NewsEntry.COLUMN_BANK_ID + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_URL + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_TEXT + TEXT_TYPE + COMMA_SEP +
                    NewsEntry.COLUMN_WAS_OPENED + INTEGER_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsEntry.TABLE_NAME;

    NewsDbHelper(Context context, DatabaseCreationCallback callback) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.dbCreationCallback = callback;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        dbCreationCallback.initializeDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    interface DatabaseCreationCallback {
        void initializeDatabase(SQLiteDatabase sqLiteDatabase);
    }
}
