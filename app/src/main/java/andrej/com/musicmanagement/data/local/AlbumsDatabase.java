package andrej.com.musicmanagement.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import andrej.com.musicmanagement.utils.ArtistConverter;
import andrej.com.musicmanagement.utils.ListConverter;

@Database(entities = {Album.class}, version = Constants.DATABASE_VERSION)
@TypeConverters({ListConverter.class, ArtistConverter.class})
public abstract class AlbumsDatabase extends RoomDatabase {

    public abstract AlbumsDAO albumsDAO();
}
