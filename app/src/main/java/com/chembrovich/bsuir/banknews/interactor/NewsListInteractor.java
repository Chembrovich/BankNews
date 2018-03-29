package com.chembrovich.bsuir.banknews.interactor;

import android.content.Context;

import com.chembrovich.bsuir.banknews.database.interfaces.DatabaseManagerInterface;
import com.chembrovich.bsuir.banknews.interactor.interfaces.NewsListInteractorInterface;
import com.chembrovich.bsuir.banknews.database.DatabaseManager;

public class NewsListInteractor implements NewsListInteractorInterface {
    private Context context;

    public NewsListInteractor(Context context) {
        this.context = context;
    }

    @Override
    public DatabaseManagerInterface getNewsListDbManager() {
        return new DatabaseManager(context);
    }
}
