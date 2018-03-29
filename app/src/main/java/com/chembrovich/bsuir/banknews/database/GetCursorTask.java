package com.chembrovich.bsuir.banknews.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class GetCursorTask extends AsyncTask<SQLiteDatabase, Void, Cursor> {
    private GetCursorAsyncResponse response;
    private String tableName;
    private SQLiteDatabase sqLiteDatabase;

    GetCursorTask(GetCursorAsyncResponse response,  String tableName) {
        this.response = response;
        this.tableName = tableName;
    }


    @Override
    protected Cursor doInBackground(SQLiteDatabase... sqLiteDatabases) {
        this.sqLiteDatabase = sqLiteDatabases[0];
        return sqLiteDatabase.query(tableName, null, null, null, null, null,null);
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        response.getCursorProcessFinish(cursor, sqLiteDatabase);
    }

    public interface GetCursorAsyncResponse {
        void getCursorProcessFinish(Cursor cursor, SQLiteDatabase sqLiteDatabase);
    }
}
