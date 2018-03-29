package com.chembrovich.bsuir.banknews.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class GetDbTask extends AsyncTask<NewsDbHelper, Void, SQLiteDatabase> {
    private GetDbAsyncResponse response;

    GetDbTask(GetDbAsyncResponse response) {
        this.response = response;
    }

    @Override
    protected SQLiteDatabase doInBackground(NewsDbHelper... newsDbHelpers) {
        return newsDbHelpers[0].getWritableDatabase();
    }

    @Override
    protected void onPostExecute(SQLiteDatabase sqLiteDatabase) {
        response.getDbProcessFinish(sqLiteDatabase);
    }

    public interface GetDbAsyncResponse {
        void getDbProcessFinish(SQLiteDatabase sqLiteDatabase);
    }
}
