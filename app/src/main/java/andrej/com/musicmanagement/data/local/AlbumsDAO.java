package andrej.com.musicmanagement.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import io.reactivex.Single;

@Dao
public interface AlbumsDAO {

    @Query("SELECT * FROM " + Constants.TABLE_NAME)
    Single<List<Album>> getAllAlbums();

    @Query("SELECT * FROM " + Constants.TABLE_NAME + " WHERE mbid = :id")
    Single<Album> isAlbumInFavorites(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbum(Album album);

    @Delete
    void deleteAlbum(Album album);
}
