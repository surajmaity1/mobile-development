package com.example.farmerapp;

import android.content.Context;

import android.content.AsyncTaskLoader;

import java.util.List;

public class UsersLoader extends AsyncTaskLoader<List<Users>> {
    private String mUrl;

    public UsersLoader(Context context, String url){
        super(context);
        mUrl=url;
    }
    @Override
    protected void onStartLoading(){
        forceLoad();
    }
    @Override
    public List<Users> loadInBackground(){
        if (mUrl==null)
            return null;
        // Perform the network request, parse the response, and extract a list
        List<Users> users = QueryUsers.fetchUsersData(mUrl);
        return users;
    }
}
