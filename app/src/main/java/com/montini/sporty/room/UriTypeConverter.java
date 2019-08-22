package com.montini.sporty.room;

import android.arch.persistence.room.TypeConverter;
import android.net.Uri;

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
