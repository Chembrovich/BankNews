package com.chembrovich.bsuir.banknews.view.interfaces;

import java.io.BufferedReader;

public interface NewsListViewInterface {
    BufferedReader getReaderFromAssets(String fileName);

    void onNewsItemClick(String url);

    void showDialogToAskForCheckAllNews();

    void updateList();

    void makeErrorLog(String tag, String message, Exception exception);

    void makeInfoLog(String tag, String message);
}
