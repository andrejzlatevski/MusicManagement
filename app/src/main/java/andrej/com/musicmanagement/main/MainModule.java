package andrej.com.musicmanagement.main;

import andrej.com.musicmanagement.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainModule {

    @FragmentScoped
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);

}
