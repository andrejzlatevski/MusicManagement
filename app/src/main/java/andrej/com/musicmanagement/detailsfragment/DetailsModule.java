package andrej.com.musicmanagement.detailsfragment;

import andrej.com.musicmanagement.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class DetailsModule {

    @FragmentScoped
    @Binds
    abstract DetailsContract.Presenter detailsPresenter(DetailsPresenter presenter);
}
