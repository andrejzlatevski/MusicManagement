package andrej.com.musicmanagement.view;

public class GlideWrappedResourceReleaseListener implements ImageLoader.OnResourceReleaseListener {

    private GlideImageLoaderImpl mGlideImageLoader;

    public void setupWith(GlideImageLoaderImpl glideImageLoader) {
        this.mGlideImageLoader = glideImageLoader;
    }

    @Override
    public void onResourceReleased(String path) {
        mGlideImageLoader.onResourceReleased(path);
    }
}
