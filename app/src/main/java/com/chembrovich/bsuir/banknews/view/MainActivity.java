package com.chembrovich.bsuir.banknews.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.chembrovich.bsuir.banknews.R;

public class MainActivity extends AppCompatActivity implements NewsListFragment.OnNewsListFragmentInteractionListener,
        NewsDetailInfoFragment.OnNewsDetailInfoFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            NewsListFragment newsFragment = new NewsListFragment();
            newsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, newsFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(String url) {
        NewsDetailInfoFragment newsDetailInfoFragment = NewsDetailInfoFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, newsDetailInfoFragment).addToBackStack(null).commit();

    }

    @Override
    public void onOpenInBrowserClick(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
