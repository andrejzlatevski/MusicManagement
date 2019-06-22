package andrej.com.musicmanagement.data;

import java.util.List;

import javax.inject.Inject;

import andrej.com.musicmanagement.data.local.AlbumsDAO;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import io.reactivex.Single;

public class AlbumsRepository {

    private final ApiService apiService;
    private final AlbumsDAO albumsDAO;

    @Inject
    AlbumsRepository(ApiService apiService, AlbumsDAO albumsDAO) {
        this.apiService = apiService;
        this.albumsDAO = albumsDAO;
    }

    public Single<List<Album>> getAlbumsFromDB() {
        return albumsDAO.getAllAlbums();
    }

    public void insertAlbumInDB(Album album) {
        albumsDAO.insertAlbum(album);
    }

    public void deleteAlbum(Album album){
        albumsDAO.deleteAlbum(album);
    }

    public Single<Album> checkAlbumFavorite(String id){
        return albumsDAO.isAlbumInFavorites(id);
    }
}
