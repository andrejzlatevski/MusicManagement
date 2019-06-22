package andrej.com.musicmanagement.detailsfragment;

import andrej.com.musicmanagement.ScreenPresenter;
import andrej.com.musicmanagement.ScreenView;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;

public interface DetailsContract {

    interface View extends ScreenView<Presenter> {
        void notifyActionCompleted(String message);

        void notifyAlbumIsFavorite(boolean isFavorite);

        void notifyError(String error);
    }

    interface Presenter extends ScreenPresenter<View> {
        void saveAlbumToDatabase(Album album);

        void deleteAlbum(Album album);

        void checkIsAlbumFavorite(Album album);
    }
}
