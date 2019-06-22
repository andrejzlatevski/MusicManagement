package andrej.com.musicmanagement.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import andrej.com.musicmanagement.data.topAlbumsPOJO.AlbumArtist;

public class ArtistConverter {

    @TypeConverter
    public static AlbumArtist fromStringToArtist(String value) {
        Type listType = new TypeToken<AlbumArtist>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArtistToString(AlbumArtist artist) {
        Gson gson = new Gson();
        return gson.toJson(artist);
    }
}
