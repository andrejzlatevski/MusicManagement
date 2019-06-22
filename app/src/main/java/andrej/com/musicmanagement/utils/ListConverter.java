package andrej.com.musicmanagement.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import andrej.com.musicmanagement.data.topAlbumsPOJO.AlbumImage;

public class ListConverter {
    @TypeConverter
    public static List<AlbumImage> fromStringtoArray(String value) {
        Gson gson = new Gson();
        if (value == null) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<List<AlbumImage>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListToString(List<AlbumImage> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
