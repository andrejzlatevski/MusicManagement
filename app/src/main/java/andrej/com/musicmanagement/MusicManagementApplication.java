package andrej.com.musicmanagement;

import android.content.Context;

import andrej.com.musicmanagement.di.ApplicationComponent;
import andrej.com.musicmanagement.di.DaggerApplicationComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MusicManagementApplication extends DaggerApplication {

    private static MusicManagementApplication sInstance;

    public static Context getInstance() {
        return sInstance;
    }

    private ApplicationComponent mAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        mAppComponent = DaggerApplicationComponent.builder().application(this).build();
        mAppComponent.inject(this);
        return mAppComponent;
    }

    public static ApplicationComponent getApplicationInjector() {
        return sInstance.mAppComponent;
    }
}
