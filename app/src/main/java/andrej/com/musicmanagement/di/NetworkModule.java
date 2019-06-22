package andrej.com.musicmanagement.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import andrej.com.musicmanagement.BuildConfig;
import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.data.ApiService;
import andrej.com.musicmanagement.data.local.AlbumsDAO;
import andrej.com.musicmanagement.data.local.AlbumsDatabase;
import andrej.com.musicmanagement.manager.SchedulerProviderManager;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    SchedulerProviderManager provideSchedulerProviderManager() {
        return new SchedulerProviderManager();
    }

    @Provides
    @Singleton
    AlbumsDatabase provideRoomDatabase(Application application) {
        return Room
                .databaseBuilder(application, AlbumsDatabase.class, Constants.DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    AlbumsDAO provideAlbumDao(AlbumsDatabase albumsDatabase) {
        return albumsDatabase.albumsDAO();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
