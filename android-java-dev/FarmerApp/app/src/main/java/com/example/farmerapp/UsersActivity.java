package com.example.farmerapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.Loader;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.widget.TextView;

import android.net.ConnectivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Users>>{
    private static final String USER_REQUEST_URL =
            "https://developapi.herokuapp.com/api/users/?format=json";

    private UsersAdapter mUsersAdapter;

    private TextView mEmptyStateTextView;
    private static final int USER_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        ListView userListView = (ListView) findViewById(R.id.list);
        mUsersAdapter = new UsersAdapter(this, new ArrayList<Users>());
        userListView.setAdapter(mUsersAdapter);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        userListView.setEmptyView(mEmptyStateTextView);

        userListView.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Users currentUser = mUsersAdapter.getItem(position);
                Uri userUri = Uri.parse(currentUser.getImageUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, userUri);
                startActivity(websiteIntent);
            }
        }));
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(USER_LOADER_ID,null, this);

        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }
    }
    @Override
    public Loader<List<Users>> onCreateLoader(int i, Bundle bundle){
        return new UsersLoader(this,USER_REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<Users>> loader, List<Users> users){
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_users);
        // clear the adapter of previous earthquake data
        mUsersAdapter.clear();
        /**
         * If there is a valid list of {@link Earthquake}s, then add them to the
         * adapter's data set . This will trigger the ListView to update.
         */
        if (users!=null&& !users.isEmpty())
            mUsersAdapter.addAll(users);
    }
    @Override
    public void onLoaderReset(Loader<List<Users>> loader){
        // Loader reset, so we can clear out our existing data
        mUsersAdapter.clear();
    }

}
