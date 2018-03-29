package com.chembrovich.bsuir.banknews.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chembrovich.bsuir.banknews.R;
import com.chembrovich.bsuir.banknews.interactor.NewsListInteractor;
import com.chembrovich.bsuir.banknews.presenter.NewsListPresenter;
import com.chembrovich.bsuir.banknews.presenter.interfaces.NewsListPresenterInterface;
import com.chembrovich.bsuir.banknews.view.interfaces.NewsListViewInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewsListFragment extends Fragment implements NewsListViewInterface {
    private NewsListPresenterInterface presenter;
    private OnNewsListFragmentInteractionListener listener;
    private RecyclerView recyclerView;

    public NewsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        setHasOptionsMenu(true);

        presenter = new NewsListPresenter(new NewsListInteractor(this.getContext()));
        presenter.attachView(this);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new NewsRecyclerViewAdapter(presenter, listener));

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                    recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);

            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_check_all:
                presenter.onCheckAllNewsAsOpenedClick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showDialogToAskForCheckAllNews() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.check_all_dialog_message);

        builder.setPositiveButton(R.string.check_all, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.onAcceptToCheckAllNewsAsOpenedClick();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.create().show();
    }

    @Override
    public void updateList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnNewsListFragmentInteractionListener) {
            listener = (OnNewsListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewsListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
        listener = null;
    }

    @Override
    public BufferedReader getReaderFromAssets(String fileName) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getContext().getAssets().open(fileName)));
        } catch (IOException | NullPointerException exception ) {
            Log.e("getReaderFromAssets", "Exception while getting news from file", exception);
        }

        return reader;
    }

    @Override
    public void makeErrorLog(String tag, String message, Exception exception) {
        Log.e(tag, message, exception);
    }

    @Override
    public void makeInfoLog(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void onNewsItemClick(String url) {
        listener.onListFragmentInteraction(url);
    }


    public interface OnNewsListFragmentInteractionListener {
        void onListFragmentInteraction(String url);
    }
}
