package andrej.com.musicmanagement.topalbumsfragment;

import javax.inject.Inject;

import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.base.BaseScreenPresenter;
import andrej.com.musicmanagement.data.ApiService;
import andrej.com.musicmanagement.data.topAlbumsPOJO.ExampleAlbums;
import andrej.com.musicmanagement.manager.SchedulerProviderManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class TopAlbumsPresenter extends BaseScreenPresenter<TopAlbumsContract.View> implements TopAlbumsContract.Presenter {

    private ApiService apiService;
    private final SchedulerProviderManager schedulerProviderManager;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public TopAlbumsPresenter(ApiService apiService, SchedulerProviderManager schedulerProviderManager, CompositeDisposable compositeDisposable) {
        this.apiService = apiService;
        this.compositeDisposable = compositeDisposable;
        this.schedulerProviderManager = schedulerProviderManager;
    }


    @Override
    protected void onViewDetached() {
    }

    @Override
    protected void onViewAttached() {
    }

    @Override
    protected void onScreenDestroyed() {
    }

    @Override
    public void getTopAlbums(String artist) {
        compositeDisposable.addAll(apiService.getTopAlbums(Constants.ALBUM_SEARCH,artist,Constants.API_KEY,Constants.FORMAT)
                .subscribeOn(schedulerProviderManager.io())
                .observeOn(schedulerProviderManager.ui())
                .subscribe(new Consumer<ExampleAlbums>() {
                    @Override
                    public void accept(ExampleAlbums response) throws Exception {
                        if(getView()!=null){
                            if(response !=null && response.getTopalbums()!=null && response.getTopalbums().getAlbum()!= null){
                                getView().notifyAlbumsLoaded(response.getTopalbums().getAlbum());
                            }else {
                                getView().hideProgressDialog();
                                getView().showMessage(R.string.artist_not_found);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable t) throws Exception {
                        if(getView()!=null){
                            getView().notifySearchFailure(t.getMessage());
                        }
                    }
                }));
    }
}