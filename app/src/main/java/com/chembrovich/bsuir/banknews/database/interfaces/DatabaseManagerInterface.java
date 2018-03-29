package com.chembrovich.bsuir.banknews.database.interfaces;

import com.chembrovich.bsuir.banknews.model.NewsItem;
import com.chembrovich.bsuir.banknews.presenter.interfaces.NewsListPresenterInterface;

import java.util.List;

public interface DatabaseManagerInterface {
    List<NewsItem> getNewsList();

    void attachPresenter(NewsListPresenterInterface presenter);

    void setIsOpened(int position);

    void setIsOpenedToAll();
}
