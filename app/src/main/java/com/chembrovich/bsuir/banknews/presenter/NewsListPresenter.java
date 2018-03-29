package com.chembrovich.bsuir.banknews.presenter;

import com.chembrovich.bsuir.banknews.database.interfaces.DatabaseManagerInterface;
import com.chembrovich.bsuir.banknews.interactor.interfaces.NewsListInteractorInterface;
import com.chembrovich.bsuir.banknews.model.NewsItem;
import com.chembrovich.bsuir.banknews.presenter.interfaces.NewsListPresenterInterface;
import com.chembrovich.bsuir.banknews.view.interfaces.NewsListViewInterface;

import java.io.BufferedReader;

import java.util.List;

public class NewsListPresenter implements NewsListPresenterInterface {
    private NewsListViewInterface view;
    private List<NewsItem> newsList;
    private DatabaseManagerInterface databaseManager;

    public NewsListPresenter(NewsListInteractorInterface interactor) {
        this.databaseManager = interactor.getNewsListDbManager();
        databaseManager.attachPresenter(this);
    }

    @Override
    public void attachView(NewsListViewInterface view) {
        this.view = view;
        newsList = databaseManager.getNewsList();
        newsList.size();
    }

    @Override
    public BufferedReader getReaderFromAssets(String fileName) {
        return view.getReaderFromAssets(fileName);
    }

    @Override
    public void makeErrorLog(String tag, String message, Exception exception) {
        view.makeErrorLog(tag, message, exception);
    }

    @Override
    public void makeInfoLog(String tag, String message) {
        view.makeInfoLog(tag, message);
    }

    @Override
    public int getNewsListSize() {
        return newsList.size();
    }

    @Override
    public String getNewsDateByPositionInList(int position) {
        return newsList.get(position).getDate();
    }

    @Override
    public String getNewsTitleByPositionInList(int position) {
        return newsList.get(position).getTitle();
    }

    @Override
    public boolean isPromoNewsByPositionInList(int position) {
        final String promo = "PROMO";
        return newsList.get(position).getType().equals(promo);
    }

    @Override
    public boolean isOpenedByPositionInList(int position) {
        return newsList.get(position).getWasOpenedAsBoolean();
    }

    @Override
    public void onNewsClick(int position) {
        String url = newsList.get(position).getUrl();

        if(!newsList.get(position).getWasOpenedAsBoolean()) {
            newsList.get(position).setWasOpened(true);
            databaseManager.setIsOpened(position);
        }

        view.onNewsItemClick(url);
    }

    @Override
    public void onCheckAllNewsAsOpenedClick() {
        view.showDialogToAskForCheckAllNews();
    }

    @Override
    public void onAcceptToCheckAllNewsAsOpenedClick() {
        for (int i = 0; i < newsList.size(); i++) {
            newsList.get(i).setWasOpened(true);
        }

        databaseManager.setIsOpenedToAll();
        view.updateList();
    }

    @Override
    public void newsListIsReady(List<NewsItem> newsList) {
        this.newsList = newsList;

        if (view != null) {
            view.updateList();
        }
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
