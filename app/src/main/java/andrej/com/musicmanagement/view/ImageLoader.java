package andrej.com.musicmanagement.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import andrej.com.musicmanagement.Constants;

public interface ImageLoader {
    int INVALID_RES_ID = 0;

    void displayImage(ImageView view, LoadingBuilder loadingBuilder);

    void loadBitmap(LoadingBuilder loadingBuilder);

    @Nullable
    Bitmap getBitmap(LoadingBuilder loadingBuilder);

    boolean isCached(String url);

    boolean isCached(Uri uri);

    interface OnLoadingProcessListener {
        void onLoadingStarted();

        void onLoadingFailed();

        void onLoadingComplete();

        void onLoadingCancelled();

        void onBitmapComplete(Bitmap bitmap);
    }

    interface OnResourceReleaseListener {
        void onResourceReleased(String path);
    }

    final class LoadingBuilder {
        String url;
        Uri uri;
        Drawable placeHolderDrawable;
        OnLoadingProcessListener loadingProcessListener;
        ImageBounds outputBounds;
        int placeHolder;
        int cornerRadius;
        int orientation = Constants.EMPTY_INT_VALUE;
        boolean clearPrevState = true;
        boolean useBlurEffect;
        boolean centerCrop;
        boolean fitCenter;
        boolean isCircle;

        public int getOrientation() {
            return orientation;
        }

        public LoadingBuilder setOrientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public boolean isCircle() {
            return isCircle;
        }

        public LoadingBuilder setCircle(boolean circle) {
            isCircle = circle;
            return this;
        }

        public boolean isClearPrevState() {
            return clearPrevState;
        }

        public LoadingBuilder setClearPrevState(boolean clearPrevState) {
            this.clearPrevState = clearPrevState;
            return this;
        }

        public boolean isFitCenter() {
            return fitCenter;
        }

        public LoadingBuilder fitCenter(boolean fitCenter) {
            this.fitCenter = fitCenter;
            return this;
        }

        public boolean isCenterCrop() {
            return centerCrop;
        }

        public LoadingBuilder centerCrop(boolean centerCrop) {
            this.centerCrop = centerCrop;
            return this;
        }

        public boolean isUseBlurEffect() {
            return useBlurEffect;
        }

        public LoadingBuilder setUseBlurEffect(boolean useBlurEffect) {
            this.useBlurEffect = useBlurEffect;
            return this;
        }

        public int getCornerRadius() {
            return cornerRadius;
        }

        public LoadingBuilder setCornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        public Drawable getPlaceHolderDrawable() {
            return placeHolderDrawable;
        }

        public LoadingBuilder setPlaceHolderDrawable(Drawable placeHolderDrawable) {
            this.placeHolderDrawable = placeHolderDrawable;
            return this;
        }

        public LoadingBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public LoadingBuilder setUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public LoadingBuilder setPlaceHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public LoadingBuilder setLoadingProcessListener(OnLoadingProcessListener loadingProcessListener) {
            this.loadingProcessListener = loadingProcessListener;
            return this;
        }

        public LoadingBuilder setOutputBounds(ImageBounds outputBounds) {
            this.outputBounds = outputBounds;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Uri getUri() {
            return uri;
        }

        public int getPlaceHolder() {
            return placeHolder;
        }

        public OnLoadingProcessListener getLoadingProcessListener() {
            return loadingProcessListener;
        }

        public ImageBounds getOutputBounds() {
            return outputBounds;
        }
    }

    final class ImageBounds {
        protected int height = -1;
        protected int width = -1;

        public ImageBounds(int width, int height) {
            this.height = height;
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }
}
