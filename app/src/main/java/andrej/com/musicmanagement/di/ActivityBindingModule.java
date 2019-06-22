package andrej.com.musicmanagement.di;


import andrej.com.musicmanagement.main.MainActivity;
import andrej.com.musicmanagement.main.MainActivityModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

}
