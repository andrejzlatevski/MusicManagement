package andrej.com.musicmanagement.searchfragment;

import java.util.List;

import andrej.com.musicmanagement.ScreenPresenter;
import andrej.com.musicmanagement.ScreenView;
import andrej.com.musicmanagement.data.artistSearchPOJO.Artist;

public interface SearchContract {

    interface View extends ScreenView<Presenter> {

        void notifyArtistsLoaded(List<Artist> artists);

        void notifySearchFailure(String error);
    }

    interface Presenter extends ScreenPresenter<View> {

        void getArtist(String artist);
    }
}
