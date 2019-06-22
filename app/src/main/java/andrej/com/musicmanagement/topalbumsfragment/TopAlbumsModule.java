package andrej.com.musicmanagement.topalbumsfragment;

import andrej.com.musicmanagement.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class TopAlbumsModule {

    @FragmentScoped
    @Binds
    abstract TopAlbumsContract.Presenter topAlbumsPresenter(TopAlbumsPresenter presenter);
}
