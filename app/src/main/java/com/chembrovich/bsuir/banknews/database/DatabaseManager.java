package com.chembrovich.bsuir.banknews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chembrovich.bsuir.banknews.database.interfaces.DatabaseManagerInterface;
import com.chembrovich.bsuir.banknews.model.NewsItem;
import com.chembrovich.bsuir.banknews.database.NewsContract.NewsEntry;
import com.chembrovich.bsuir.banknews.presenter.interfaces.NewsListPresenterInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements DatabaseManagerInterface, NewsDbHelper.DatabaseCreationCallback,
                                        GetDbTask.GetDbAsyncResponse, GetCursorTask.GetCursorAsyncResponse {
    private NewsDbHelper dbHelper;
    private NewsListPresenterInterface presenter;
    private List<NewsItem> newsList;

    public DatabaseManager(Context context) {
        dbHelper = new NewsDbHelper(context, this);
    }

    @Override
    public void attachPresenter(NewsListPresenterInterface presenter) {
        this.presenter = presenter;
    }

    @Override
    public List<NewsItem> getNewsList() {
        if (newsList == null) {
            newsList = new ArrayList<>();

            GetDbTask getDbTask = new GetDbTask(this);
            getDbTask.execute(dbHelper);
        }
        return newsList;
    }

    @Override
    public void setIsOpened(final int position) {
        new Thread(new Runnable() {
            public void run() {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String sqlScript = "UPDATE " + NewsEntry.TABLE_NAME +
                        " SET " + NewsEntry.COLUMN_WAS_OPENED + " = " + NewsEntry.IS_OPENED_FLAG +
                        " WHERE " + NewsEntry.COLUMN_BANK_ID + " = '" + newsList.get(position).getId() + "'";
                db.execSQL(sqlScript);
                db.close();
            }
        }).start();
    }

    @Override
    public void setIsOpenedToAll() {
        new Thread(new Runnable() {
            public void run() {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String sqlScript = "UPDATE " + NewsEntry.TABLE_NAME +
                        " SET " + NewsEntry.COLUMN_WAS_OPENED + " = "+ NewsEntry.IS_OPENED_FLAG;
                db.execSQL(sqlScript);
                db.close();
            }
        }).start();
    }

    private void readNewsFromFile() {
        Gson gson = new Gson();
        Type newsListType = new TypeToken<ArrayList<NewsItem>>(){}.getType();

        try {
            final String  NEWS_FILE_NAME = "news";
            Reader reader = presenter.getReaderFromAssets(NEWS_FILE_NAME);
            newsList = gson.fromJson(reader, newsListType);
            reader.close();
        } catch (IOException exception) {
            presenter.makeErrorLog("initNewsList", "IOException in DatabaseManager while initialize news list", exception);
        }
    }

    private void readNewsFromDatabase(SQLiteDatabase db) {
        GetCursorTask getCursorTask = new GetCursorTask(this, NewsEntry.TABLE_NAME);
        getCursorTask.execute(db);
    }

    @Override
    public void initializeDatabase(SQLiteDatabase sqLiteDatabase) {
        readNewsFromFile();

        if (!newsList.isEmpty()) {
            ContentValues values = new ContentValues();

            for (NewsItem newsItem : newsList) {
                values.put(NewsEntry.COLUMN_BANK_ID, newsItem.getId());
                values.put(NewsEntry.COLUMN_TITLE, newsItem.getTitle());
                values.put(NewsEntry.COLUMN_DATE, newsItem.getDate());
                values.put(NewsEntry.COLUMN_TYPE, newsItem.getType());
                values.put(NewsEntry.COLUMN_URL, newsItem.getUrl());
                values.put(NewsEntry.COLUMN_TEXT, newsItem.getText());
                values.put(NewsEntry.COLUMN_WAS_OPENED, newsItem.getWasOpenedAsInteger());

                sqLiteDatabase.insert(NewsEntry.TABLE_NAME, null, values);

                values.clear();
            }
        }
        presenter.makeInfoLog("initializeDatabase", "DB news table is created");
    }

    @Override
    public void getDbProcessFinish(SQLiteDatabase sqLiteDatabase) {
        if (newsList.isEmpty()) {
            readNewsFromDatabase(sqLiteDatabase);

            if (!newsList.isEmpty()) {
                presenter.newsListIsReady(newsList);
            }

        } else {
            presenter.newsListIsReady(newsList);
        }
    }

    @Override
    public void getCursorProcessFinish(Cursor cursor, SQLiteDatabase sqLiteDatabase) {
        if (cursor.moveToFirst()) {
            NewsItem newsItem;

            int idColumnIndex = cursor.getColumnIndexOrThrow(NewsEntry.COLUMN_BANK_ID);
            int dateColumnIndex = cursor.getColumnIndexOrThrow(NewsEntry.COLUMN_DATE);
            int textColumnIndex = cursor.getColumnIndexOrThrow(NewsEntry.COLUMN_TEXT);
            int titleColumnIndex = cursor.getColumnIndexOrThrow(NewsEntry.COLUMN_TITLE);
            int typeColumnIndex = cursor.getColumnIndexOrThrow(NewsEntry.COLUMN_TYPE);
            int urlColumnIndex = cursor.getColumnIndexOrThrow(NewsEntry.COLUMN_URL);
            int wasOpenedColumnIndex = cursor.getColumnIndexOrThrow(NewsEntry.COLUMN_WAS_OPENED);

            do {
                newsItem = new NewsItem();

                newsItem.setId(cursor.getString(idColumnIndex));
                newsItem.setDate(cursor.getString(dateColumnIndex));
                newsItem.setText(cursor.getString(textColumnIndex));
                newsItem.setTitle(cursor.getString(titleColumnIndex));
                newsItem.setType(cursor.getString(typeColumnIndex));
                newsItem.setUrl(cursor.getString(urlColumnIndex));
                newsItem.setWasOpened(cursor.getInt(wasOpenedColumnIndex));

                newsList.add(newsItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        presenter.newsListIsReady(newsList);
    }
}
