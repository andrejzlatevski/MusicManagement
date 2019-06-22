package andrej.com.musicmanagement.main;

import java.util.List;

import javax.inject.Inject;

import andrej.com.musicmanagement.base.BaseScreenPresenter;
import andrej.com.musicmanagement.data.AlbumsRepository;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import andrej.com.musicmanagement.manager.SchedulerProviderManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public final class MainPresenter extends BaseScreenPresenter<MainContract.View> implements MainContract.Presenter {

    private final AlbumsRepository albumsRepository;
    private final SchedulerProviderManager schedulerProviderManager;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public MainPresenter(AlbumsRepository albumsRepository, SchedulerProviderManager schedulerProviderManager, CompositeDisposable compositeDisposable) {
        this.albumsRepository = albumsRepository;
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
    public void getAllAlbums() {
        compositeDisposable.addAll(albumsRepository
                .getAlbumsFromDB()
                .subscribeOn(schedulerProviderManager.io())
                .observeOn(schedulerProviderManager.ui())
                .subscribe(new Consumer<List<Album>>() {
            @Override
            public void accept(List<Album> albums) throws Exception {
                if(getView()!=null){
                    getView().notifyAlbumsLoaded(albums);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if(getView()!=null){
                    getView().notifyAlbimsLoadingError(throwable.getMessage());
                }
            }
        }));
    }
}
