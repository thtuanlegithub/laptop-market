package com.example.laptop_market.ultilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class PreferenceManager {
    private final SharedPreferences sharedPreferences;
    public PreferenceManager(Context context)
    {
        if(context!=null)
            sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME,Context.MODE_PRIVATE);
        else
            sharedPreferences=null;
    }
    public void putBoolean(String key, Boolean value)
    {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public Boolean getBoolean(String key)
    {
        return sharedPreferences.getBoolean(key,false);
    }
    public void putString(String key,String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString(String key)
    {
        return sharedPreferences.getString(key,null);
    }
    public void clear()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    public void putFilters(String keyName, ArrayList<String> filters) {
        StringBuilder sb = new StringBuilder();
        for (String filter : filters) {
            sb.append(filter).append(",");
        }
        String filterString = sb.toString().substring(0, sb.length() - 1);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyName, filterString);
        editor.apply();
    }

    public ArrayList<String> getFilters(String keyName) {
        ArrayList<String> listString;
        String filterString = sharedPreferences.getString(keyName, null);
        if (filterString != null && !filterString.isEmpty()) {
            listString = new ArrayList<>(Arrays.asList(filterString.split(",")));
            listString.size();
            return listString;
        }
        return null;
    }
}
