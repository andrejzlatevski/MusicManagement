package andrej.com.musicmanagement.searchfragment;

import andrej.com.musicmanagement.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class SearchModule {

    @FragmentScoped
    @Binds
    abstract SearchContract.Presenter searchPresenter(SearchPresenter presenter);
}
