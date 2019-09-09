package com.montini.sporty.room;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriTypeConverter {
    @TypeConverter
    public static Uri toUri(String value) {
        return Uri.parse(value);
    }

    @TypeConverter
    public static String toString(Uri value) {
        return value.toString();
    }
}
