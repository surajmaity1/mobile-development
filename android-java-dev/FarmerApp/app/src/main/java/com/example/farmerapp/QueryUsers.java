package com.example.farmerapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

public class QueryUsers {
    private static final String LOG_TAG = QueryUsers.class.getSimpleName();
    public static List<Users> fetchUsersData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making http request",e);
        }
        List<Users> users = extractFeatureFromJson(jsonResponse);
        return users;
    }
    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Problem building the url: ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";
        if (url == null)
            return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If the request was successful (response code 200)
            // then read the input stream and parse the response
            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG,"Error response code: "+urlConnection.getResponseCode());
            }
        }
        catch (IOException e){
            Log.e(LOG_TAG,"Problem receiving the users JSON result",e);
        }
        finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static List<Users> extractFeatureFromJson(String usersJSON){
        // if JSON STRING IS NULL OR EMPTY
        if (TextUtils.isEmpty(usersJSON))
            return null;
        // Create an empty ArrayList that we can start adding users to
        List<Users> users = new ArrayList<>();
        try{
            // create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(usersJSON);
            JSONArray usersArray = baseJsonResponse.getJSONArray("data");

            for (int i=0;i<usersArray.length();i++){
                JSONObject currentUser = usersArray.getJSONObject(i);
                JSONObject dataObject = currentUser.getJSONObject("data");

                int id=dataObject.getInt("id");
                String name = dataObject.getString("name");
                String imageUrl = dataObject.getString("image");
                String college = dataObject.getString("college");
                Double roll = dataObject.getDouble("roll");

                Users users1 = new Users(id,name,imageUrl,college,roll);
                users.add(users1);
            }
        }catch (JSONException e){
            Log.e("QueryUsers", "Problem parsing the Users JSON results", e);

        }
        return users;
    }
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }
}
