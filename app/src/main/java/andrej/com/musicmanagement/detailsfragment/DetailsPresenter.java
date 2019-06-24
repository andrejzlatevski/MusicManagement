package andrej.com.musicmanagement.detailsfragment;

import android.content.Context;

import javax.inject.Inject;

import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.base.BaseScreenPresenter;
import andrej.com.musicmanagement.data.AlbumsRepository;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import andrej.com.musicmanagement.manager.SchedulerProviderManager;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class DetailsPresenter extends BaseScreenPresenter<DetailsContract.View> implements DetailsContract.Presenter {

    private final AlbumsRepository albumsRepository;
    private final SchedulerProviderManager schedulerProviderManager;
    private final CompositeDisposable compositeDisposable;
    private final Context context;

    @Inject
    public DetailsPresenter(AlbumsRepository albumsRepository, SchedulerProviderManager schedulerProviderManager, CompositeDisposable compositeDisposable, Context context) {
        this.albumsRepository = albumsRepository;
        this.compositeDisposable = compositeDisposable;
        this.schedulerProviderManager = schedulerProviderManager;
        this.context = context;
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
    public void saveAlbumToDatabase(final Album album) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                albumsRepository.insertAlbumInDB(album);
            }
        }).subscribeOn(schedulerProviderManager.io())
                .observeOn(schedulerProviderManager.ui())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (getView()!=null){
                            getView().notifyAlbumIsFavorite(true);
                            getView().notifyActionCompleted(context.getResources().getString(R.string.album_saved));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView()!=null){
                            getView().notifyAlbumIsFavorite(false);
                            getView().notifyError(e.getMessage());
                            getView().notifyActionCompleted(context.getResources().getString(R.string.error_saving));
                        }
                    }
                });
    }

    @Override
    public void deleteAlbum(final Album album) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                albumsRepository.deleteAlbum(album);
            }
        }).subscribeOn(schedulerProviderManager.io())
          .observeOn(schedulerProviderManager.ui())
          .subscribe(new CompletableObserver() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onComplete() {
                   if (getView()!=null){
                       getView().notifyAlbumIsFavorite(false);
                       getView().notifyActionCompleted(context.getResources().getString(R.string.album_deleted));
                   }
              }

              @Override
              public void onError(Throwable e) {
                  if (getView()!=null){
                      getView().notifyAlbumIsFavorite(true);
                      getView().notifyError(e.getMessage());
                      getView().notifyActionCompleted(context.getResources().getString(R.string.error_deleting));
                  }
              }
          });
    }

    @Override
    public void checkIsAlbumFavorite(final Album album) {
        compositeDisposable.addAll(albumsRepository
                .checkAlbumFavorite(album.getMbid())
                .subscribeOn(schedulerProviderManager.io())
                .observeOn(schedulerProviderManager.ui())
                .subscribe(new Consumer<Album>() {
                    @Override
                    public void accept(Album album) throws Exception {
                        if(getView()!=null){
                            if(album!=null){
                                getView().notifyAlbumIsFavorite(true);
                            }else {
                                getView().notifyAlbumIsFavorite(false);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(getView()!=null){
                            getView().notifyAlbumIsFavorite(false);
                            getView().notifyError(throwable.getMessage());
                        }
                    }
                }));
    }
}
