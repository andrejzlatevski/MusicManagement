package andrej.com.musicmanagement.main;


import andrej.com.musicmanagement.Conductor;
import andrej.com.musicmanagement.ToolBarOwner;
import andrej.com.musicmanagement.base.BaseFragment;
import andrej.com.musicmanagement.detailsfragment.DetailsFragment;
import andrej.com.musicmanagement.detailsfragment.DetailsModule;
import andrej.com.musicmanagement.di.ActivityScoped;
import andrej.com.musicmanagement.di.FragmentScoped;
import andrej.com.musicmanagement.searchfragment.SearchFragment;
import andrej.com.musicmanagement.searchfragment.SearchModule;
import andrej.com.musicmanagement.topalbumsfragment.TopAlbumsFragment;
import andrej.com.musicmanagement.topalbumsfragment.TopAlbumsModule;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @Provides
    @ActivityScoped
    static Conductor<BaseFragment> provideConductor(MainActivity activity) {
        return activity;
    }

    @Provides
    @ActivityScoped
    static ToolBarOwner provideToolBarOwner(MainActivity activity) {
        return activity;
    }

    @FragmentScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainFragment addMainFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = DetailsModule.class)
    abstract DetailsFragment addDetailsFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = TopAlbumsModule.class)
    abstract TopAlbumsFragment addTopAlbumsFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchFragment addSearchFragment();
}
