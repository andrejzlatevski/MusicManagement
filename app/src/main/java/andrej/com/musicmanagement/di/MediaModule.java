package andrej.com.musicmanagement.di;

import javax.inject.Singleton;

import andrej.com.musicmanagement.view.GlideImageLoaderImpl;
import andrej.com.musicmanagement.view.GlideWrappedResourceReleaseListener;
import andrej.com.musicmanagement.view.ImageLoader;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MediaModule {

    private static GlideWrappedResourceReleaseListener mWrappedResourceReleaseListener;

    @Provides
    @Singleton
    public static ImageLoader provideImageLoader(GlideImageLoaderImpl imageLoader) {
        if (mWrappedResourceReleaseListener == null) {
            mWrappedResourceReleaseListener = new GlideWrappedResourceReleaseListener();
        }
        mWrappedResourceReleaseListener.setupWith(imageLoader);
        return imageLoader;
    }

    @Provides
    @Singleton
    public static ImageLoader.OnResourceReleaseListener provideImageLoaderResourceReleaseListener() {
        return mWrappedResourceReleaseListener = new GlideWrappedResourceReleaseListener();
    }
}
