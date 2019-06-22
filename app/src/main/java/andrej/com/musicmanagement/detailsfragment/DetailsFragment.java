package andrej.com.musicmanagement.detailsfragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import andrej.com.musicmanagement.Conductor;
import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.ToolBarOwner;
import andrej.com.musicmanagement.base.BaseFragment;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import andrej.com.musicmanagement.databinding.DetailsFragmentBinding;
import andrej.com.musicmanagement.view.ImageLoader;

public class DetailsFragment extends BaseFragment<DetailsContract.Presenter> implements DetailsContract.View{

    private static final String PROVIDED_ALBUM = "provided_album";

    public static BaseFragment createInstance(Album album) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(PROVIDED_ALBUM, album);
        fragment.setArguments(args);
        return fragment;
    }

    private DetailsFragmentBinding mBindingObject;

    private Album mAlbum;
    private boolean isSaved;

    @Inject
    DetailsContract.Presenter presenter;
    @Inject
    ToolBarOwner toolBarOwner;
    @Inject
    Conductor<BaseFragment> conductor;
    @Inject
    ImageLoader imageLoader;

    @Inject
    public DetailsFragment() {
    }

    @Override
    public String getScreenTag() {
        return null;
    }

    @Nullable
    @Override
    protected DetailsContract.Presenter providePresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected View bindView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBindingObject = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false);
        mAlbum = getArguments().getParcelable(PROVIDED_ALBUM);
        if(mAlbum==null){
            conductor.goBack();
        }
        toolBarOwner.showTitle(mAlbum.getName());
        toolBarOwner.showBackButton();
        isSaved = false;
        presenter.checkIsAlbumFavorite(mAlbum);
        mBindingObject.albumName.setText(mAlbum.getName());
        mBindingObject.albumPlaycount.setText(String.valueOf(mAlbum.getPlaycount()));
        imageLoader.displayImage(mBindingObject.albumImg, new ImageLoader.LoadingBuilder().setUrl(mAlbum.getAlbumImage().get(0).getText()).setPlaceHolder(R.drawable.image_placeholder));
        mBindingObject.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSaved = !isSaved;
                if(isSaved){
                    presenter.saveAlbumToDatabase(mAlbum);
                }else {
                    presenter.deleteAlbum(mAlbum);
                }
                mBindingObject.btnFavorite.setSelected(isSaved);
            }
        });
        return mBindingObject.getRoot();
    }

    @Override
    public void notifyActionCompleted(String message) {
        showMessage(message);
    }

    @Override
    public void notifyAlbumIsFavorite(boolean isFavorite) {
        isSaved = isFavorite;
        mBindingObject.btnFavorite.setSelected(isSaved);
    }

    @Override
    public void notifyError(String error) {
        Log.d(Constants.ERROR_TAG,error);
    }
}