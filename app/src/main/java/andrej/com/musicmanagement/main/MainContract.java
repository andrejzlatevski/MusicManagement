package andrej.com.musicmanagement.main;

import java.util.List;

import andrej.com.musicmanagement.ScreenPresenter;
import andrej.com.musicmanagement.ScreenView;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;

public interface MainContract {
    interface View extends ScreenView<Presenter> {
        void notifyAlbumsLoaded(List<Album> albums);

        void notifyAlbimsLoadingError(String error);
    }

    interface Presenter extends ScreenPresenter<View> {
        void getAllAlbums();
    }
}
