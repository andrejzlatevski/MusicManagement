package andrej.com.musicmanagement.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public final class GlideImageLoaderImpl implements ImageLoader, ImageLoader.OnResourceReleaseListener {

    private final Glide mGlide;
    private final RequestManager requestManager;
    private final Set<String> mCachedImages = new HashSet<>();

    @Inject
    public GlideImageLoaderImpl(Context context) {
        mGlide = Glide.get(context);
        requestManager = Glide.with(context);
    }

    @Override
    public void displayImage(ImageView view, final LoadingBuilder loadingBuilder) {
        RequestBuilder request = createRequest(loadingBuilder);
        RequestOptions requestOptions = createRequestOptions(loadingBuilder, view);
        if (loadingBuilder.isClearPrevState()) {
            requestManager.clear(view);
        } else {
            requestOptions = requestOptions.placeholder(view.getDrawable());
        }
        request.listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                if (loadingBuilder.getLoadingProcessListener() != null) {
                    loadingBuilder.getLoadingProcessListener().onLoadingFailed();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                if (loadingBuilder.getLoadingProcessListener() != null) {
                    loadingBuilder.getLoadingProcessListener().onLoadingComplete();
                }
                if (model instanceof File) {
                    mCachedImages.add(((File) model).getPath());
                } else {
                    mCachedImages.add((String) model);
                }
                return false;
            }
        });
        if (requestOptions.getTransformations().size() == 0) {
            requestOptions = requestOptions.fitCenter();
        }
        request.apply(requestOptions).into(view);
    }

    @Override
    public boolean isCached(String url) {
        return mCachedImages.contains(url);
    }

    @Override
    public boolean isCached(Uri uri) {
        return mCachedImages.contains(uri.getPath());
    }

    @Override
    public void loadBitmap(final LoadingBuilder loadingBuilder) {

    }

    @Override
    public Bitmap getBitmap(LoadingBuilder loadingBuilder) {
        RequestBuilder request = createBitmapRequest(loadingBuilder);
        RequestOptions requestOptions = createBitmapOptions(loadingBuilder);
        FutureTarget bitmapTarget;
        bitmapTarget = request.apply(requestOptions).submit();
        Bitmap result = null;
        try {
            result = (Bitmap) bitmapTarget.get();
        } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onResourceReleased(String path) {
        mCachedImages.remove(path);
    }

    private RequestBuilder createRequest(LoadingBuilder loadingBuilder) {
        return (loadingBuilder.getUri() != null) ? requestManager.load(loadingBuilder.getUri().getPath()) : requestManager.load(loadingBuilder.getUrl());
    }

    private RequestBuilder createBitmapRequest(LoadingBuilder loadingBuilder) {
        return (loadingBuilder.getUri() != null) ? requestManager.asBitmap().load(loadingBuilder.getUri().getPath()) : requestManager.asBitmap().load(loadingBuilder.getUrl());
    }

    private RequestOptions createRequestOptions(LoadingBuilder loadingBuilder, ImageView view) {
        RequestOptions requestOptions = new RequestOptions();
        if (loadingBuilder.getPlaceHolder() != INVALID_RES_ID) {
            requestOptions = requestOptions.placeholder(loadingBuilder.getPlaceHolder());
        } else if (loadingBuilder.getPlaceHolderDrawable() != null) {
            requestOptions = requestOptions.placeholder(loadingBuilder.getPlaceHolderDrawable());
        }
        if (loadingBuilder.getOutputBounds() != null) {
            requestOptions = requestOptions.override(loadingBuilder.getOutputBounds().getWidth(), loadingBuilder.getOutputBounds().getHeight());
        }
        if (loadingBuilder.isCenterCrop() || (view != null && view.getScaleType() == ImageView.ScaleType.CENTER_CROP)) {
            requestOptions = requestOptions.centerCrop();
        }
        if (loadingBuilder.isFitCenter() || (view != null && view.getScaleType() == ImageView.ScaleType.FIT_CENTER)) {
            requestOptions = requestOptions.fitCenter();
        }
        Collection<Transformation<Bitmap>> transformations = new ArrayList<>();
        if (loadingBuilder.isUseBlurEffect()) {
            transformations.add(new BlurTransformation());
        }
        if (loadingBuilder.getCornerRadius() > 0) {
            transformations.add(new RoundedCornersTransformation(loadingBuilder.getCornerRadius(), 0));
        }
        if (loadingBuilder.isCircle()) {
            requestOptions = requestOptions.circleCrop();
        }
        if (!transformations.isEmpty()) {
            requestOptions = requestOptions.transform(new MultiTransformation<>(transformations));
        }
        return requestOptions;
    }

    private RequestOptions createBitmapOptions(LoadingBuilder loadingBuilder) {
        RequestOptions requestOptions = new RequestOptions();
        Collection<Transformation<Bitmap>> transformations = new ArrayList<>();
        if (loadingBuilder.getOutputBounds() != null) {
            transformations.add(new CropTransformation(loadingBuilder.getOutputBounds().getWidth(), loadingBuilder.getOutputBounds().getHeight()));
        }
        if (loadingBuilder.isCenterCrop()) {
            transformations.add(new CenterCrop());
        }
        if (loadingBuilder.isFitCenter()) {
            transformations.add(new FitCenter());
        }
        if (loadingBuilder.isUseBlurEffect()) {
            transformations.add(new BlurTransformation());
        }
        if (loadingBuilder.isCircle()) {
            transformations.add(new CircleCrop());
        }
        if (loadingBuilder.getCornerRadius() > 0) {
            transformations.add(new RoundedCornersTransformation(loadingBuilder.getCornerRadius(), 0));
        }
        if (!transformations.isEmpty()) {
            requestOptions = requestOptions.transform(new MultiTransformation<>(transformations));
        }
        return requestOptions;
    }
}

