package andrej.com.musicmanagement.view;

import android.content.Context;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;

import javax.inject.Inject;

import andrej.com.musicmanagement.MusicManagementApplication;

/**
 * This is a ImageView class that uses IImageLoader impl for the image loading
 */
@BindingMethods({
        @BindingMethod(type = ImageLoaderImageView.class,
                attribute = "imageWidth",
                method = "setImageWidth"),
        @BindingMethod(type = ImageLoaderImageView.class,
                attribute = "imageHeight",
                method = "setImageHeight"),
        @BindingMethod(type = ImageLoaderImageView.class,
                attribute = "placeHolder",
                method = "setPlaceHolder"),
        @BindingMethod(type = ImageLoaderImageView.class,
                attribute = "placeHolderResID",
                method = "setPlaceHolderResID"),
        @BindingMethod(type = ImageLoaderImageView.class,
                attribute = "cornerRadius",
                method = "setCornerRadius"),
        @BindingMethod(type = ImageLoaderImageView.class,
                attribute = "cleanBackground",
                method = "setCleanBackground"),
        @BindingMethod(type = ImageLoaderImageView.class,
                attribute = "url",
                method = "setUrl"),
        @BindingMethod(type = ImageLoaderImageView.class,
                attribute = "uri",
                method = "setUri")
})
public class ImageLoaderImageView extends android.support.v7.widget.AppCompatImageView {

    private int imageWidth = -1;
    private int imageHeight = -1;
    private int cornerRadius;
    private Drawable placeHolderDrawable;
    private int placeHolderResID = ImageLoader.INVALID_RES_ID;
    private boolean cleanBackgroundOnLoadFinish;

    @Inject
    ImageLoader imageLoader;

    public ImageLoaderImageView(Context context) {
        super(context);
        init();
    }

    public ImageLoaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageLoaderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        MusicManagementApplication.getApplicationInjector().inject(this);
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = (int) cornerRadius;
    }

    public void setImageWidth(float width) {
        this.imageWidth = (int) width;
    }

    public void setImageHeight(float imageHeight) {
        this.imageHeight = (int) imageHeight;
    }

    public void setPlaceHolder(Drawable placeHolder) {
        this.placeHolderDrawable = placeHolder;
    }

    public void setPlaceHolderResID(int placeHolderResID) {
        this.placeHolderResID = placeHolderResID;
    }

    public void setCleanBackground(boolean value) {
        this.cleanBackgroundOnLoadFinish = value;
    }

    public void setUrl(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            if (placeHolderDrawable != null) {
                setImageDrawable(placeHolderDrawable);
            } else {
                if (placeHolderResID != ImageLoader.INVALID_RES_ID) {
                    setImageResource(placeHolderResID);
                }
            }
            return;
        }
        imageLoader.displayImage(this, createBuilder()
                .setPlaceHolder(placeHolderResID).setUrl(imageUrl));
    }

    public void setUri(Uri imageUri) {
        imageLoader.displayImage(this, createBuilder()
                .setPlaceHolder(placeHolderResID).setUri(imageUri));
    }

    protected ImageLoader.LoadingBuilder createBuilder() {
        int width = (imageWidth < 0) ? getWidth() : imageWidth;
        int height = (imageHeight < 0) ? getHeight() : imageHeight;
        ImageLoader.LoadingBuilder builder = new ImageLoader.LoadingBuilder()
                .setCornerRadius(cornerRadius)
                .setPlaceHolderDrawable(placeHolderDrawable);
        if (width > 0 && height > 0) {
            builder.setOutputBounds(
                    new ImageLoader.ImageBounds(width, height)
            );
        }
        return builder.setLoadingProcessListener(new ImageLoader.OnLoadingProcessListener() {
            @Override
            public void onLoadingStarted() {

            }

            @Override
            public void onLoadingFailed() {
                if (cleanBackgroundOnLoadFinish) {
                    setBackgroundResource(0);
                }
            }

            @Override
            public void onLoadingComplete() {
                if (cleanBackgroundOnLoadFinish) {
                    setBackgroundResource(0);
                }
            }

            @Override
            public void onLoadingCancelled() {
            }

            @Override
            public void onBitmapComplete(Bitmap bitmap) {

            }
        });
    }
}

