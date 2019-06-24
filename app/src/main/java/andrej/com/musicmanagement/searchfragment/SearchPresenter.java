package andrej.com.musicmanagement.searchfragment;

import javax.inject.Inject;

import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.base.BaseScreenPresenter;
import andrej.com.musicmanagement.data.ApiService;
import andrej.com.musicmanagement.data.artistSearchPOJO.Example;
import andrej.com.musicmanagement.manager.SchedulerProviderManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SearchPresenter extends BaseScreenPresenter<SearchContract.View> implements SearchContract.Presenter {


    private ApiService apiService;
    private final SchedulerProviderManager schedulerProviderManager;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public SearchPresenter(ApiService apiService, SchedulerProviderManager schedulerProviderManager, CompositeDisposable compositeDisposable) {
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
    public void getArtist(String artist) {
        compositeDisposable.addAll(apiService.getArtist(Constants.ARTIST_SEARCH,artist,Constants.API_KEY,Constants.FORMAT)
                .subscribeOn(schedulerProviderManager.io())
                .observeOn(schedulerProviderManager.ui())
                .subscribe(new Consumer<Example>() {
                    @Override
                    public void accept(Example example) throws Exception {
                        if(example!=null && example.getResults()!=null && example.getResults().getArtistmatches()!=null && example.getResults().getArtistmatches().getArtist()!=null){
                            getView().notifyArtistsLoaded(example.getResults().getArtistmatches().getArtist());
                        }else {
                            getView().hideProgressDialog();
                            getView().showMessage(R.string.artist_not_found);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(getView()!=null){
                            getView().notifySearchFailure(throwable.getMessage());
                        }
                    }
                }));
    }
}