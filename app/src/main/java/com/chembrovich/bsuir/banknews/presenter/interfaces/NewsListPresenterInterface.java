package com.chembrovich.bsuir.banknews.presenter.interfaces;

import com.chembrovich.bsuir.banknews.model.NewsItem;
import com.chembrovich.bsuir.banknews.view.interfaces.NewsListViewInterface;

import java.io.BufferedReader;
import java.util.List;

public interface NewsListPresenterInterface {
    void attachView(NewsListViewInterface view);

    int getNewsListSize();

    BufferedReader getReaderFromAssets(String fileName);

    String getNewsDateByPositionInList(int position);

    String getNewsTitleByPositionInList(int position);

    boolean isPromoNewsByPositionInList(int position);

    boolean isOpenedByPositionInList(int position);

    void onNewsClick(int position);

    void onCheckAllNewsAsOpenedClick();

    void onAcceptToCheckAllNewsAsOpenedClick();

    void newsListIsReady(List<NewsItem> newsList);

    void makeErrorLog(String tag, String message, Exception exception);

    void makeInfoLog(String tag, String message);

    void detachView();
}
