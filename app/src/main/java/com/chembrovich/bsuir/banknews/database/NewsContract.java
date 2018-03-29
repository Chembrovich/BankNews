package com.chembrovich.bsuir.banknews.database;

import android.provider.BaseColumns;

public final class NewsContract {
    public NewsContract() {
    }

    static abstract class NewsEntry implements BaseColumns {
        static final String TABLE_NAME = "news";
        static final String COLUMN_BANK_ID = "newsId";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_DATE = "date";
        static final String COLUMN_TYPE = "type";
        static final String COLUMN_URL = "url";
        static final String COLUMN_TEXT = "text";
        static final String COLUMN_WAS_OPENED = "wasOpened";
        static final String IS_OPENED_FLAG = " 1 ";
    }
}
