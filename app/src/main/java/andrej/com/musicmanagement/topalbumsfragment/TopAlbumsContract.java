package andrej.com.musicmanagement.topalbumsfragment;

import java.util.List;

import andrej.com.musicmanagement.ScreenPresenter;
import andrej.com.musicmanagement.ScreenView;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;

public interface TopAlbumsContract {

    interface View extends ScreenView<Presenter> {

        void notifyAlbumsLoaded(List<Album> artists);

        void notifySearchFailure(String error);
    }

    interface Presenter extends ScreenPresenter<View> {

        void getTopAlbums(String artist);
    }
}
