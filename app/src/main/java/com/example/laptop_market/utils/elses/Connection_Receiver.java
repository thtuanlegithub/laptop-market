package com.example.laptop_market.utils.elses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.laptop_market.utils.tables.Constants;

public class Connection_Receiver extends BroadcastReceiver {
    private PreferenceManager preferenceManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean internetConnection = isConnected(context);
        intent.putExtra(Constants.KEY_INTERNET_CONNECTION,internetConnection);

    }
    public boolean isConnected(Context context)
    {
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo !=null && networkInfo.isConnected());
        }catch (NullPointerException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
