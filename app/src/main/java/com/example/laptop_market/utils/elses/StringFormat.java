package com.example.laptop_market.utils.elses;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringFormat {
    public static String getReadableDateTime(Date date)
    {
        return new SimpleDateFormat("hh:mm a - dd, MMMM, yyyy ", Locale.getDefault()).format(date);
    }
}
