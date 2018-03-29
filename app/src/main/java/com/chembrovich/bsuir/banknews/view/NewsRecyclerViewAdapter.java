package com.chembrovich.bsuir.banknews.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chembrovich.bsuir.banknews.R;
import com.chembrovich.bsuir.banknews.presenter.interfaces.NewsListPresenterInterface;
import com.chembrovich.bsuir.banknews.view.NewsListFragment.OnNewsListFragmentInteractionListener;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    private final NewsListPresenterInterface presenter;
    private final OnNewsListFragmentInteractionListener fragmentInteractionListener;
    
    NewsRecyclerViewAdapter(NewsListPresenterInterface presenter,
                            OnNewsListFragmentInteractionListener listener) {
        this.presenter = presenter;
        fragmentInteractionListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.dateView.setText(presenter.getNewsDateByPositionInList(position));
        holder.titleView.setText(presenter.getNewsTitleByPositionInList(position));

        if (presenter.isPromoNewsByPositionInList(position)) {
            holder.promoView.setVisibility(View.VISIBLE);
        } else {
            holder.promoView.setVisibility(View.INVISIBLE);
        }

        if (presenter.isOpenedByPositionInList(position)) {
            holder.isOpenedCircle.setVisibility(View.INVISIBLE);
        } else {
            holder.isOpenedCircle.setVisibility(View.VISIBLE);
        }

        holder.listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != fragmentInteractionListener) {
                    holder.isOpenedCircle.setVisibility(View.INVISIBLE);
                    presenter.onNewsClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.getNewsListSize();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View listItemView;
        final TextView dateView;
        final TextView titleView;
        final TextView promoView;
        final View isOpenedCircle;

        ViewHolder(View view) {
            super(view);
            listItemView = view;
            dateView = view.findViewById(R.id.news_list_item_date);
            titleView = view.findViewById(R.id.news_list_item_title);
            promoView = view.findViewById(R.id.promo_text_view);
            isOpenedCircle = view.findViewById(R.id.circle);
        }
    }
}
