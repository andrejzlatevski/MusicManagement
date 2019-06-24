package andrej.com.musicmanagement.topalbumsfragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import andrej.com.musicmanagement.Conductor;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.ToolBarOwner;
import andrej.com.musicmanagement.base.BaseFragment;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import andrej.com.musicmanagement.databinding.TopAlbumsFragmentBinding;
import andrej.com.musicmanagement.decor.VerticalSpaceItemDecoration;
import andrej.com.musicmanagement.detailsfragment.DetailsFragment;
import andrej.com.musicmanagement.view.ImageLoader;

public class TopAlbumsFragment extends BaseFragment<TopAlbumsContract.Presenter> implements TopAlbumsContract.View, TopAlbumsAdapter.OnClickListener {

    private static final String PROVIDED_ARTIST = "provided_artist";

    public static BaseFragment createInstance(String artistName) {
        TopAlbumsFragment fragment = new TopAlbumsFragment();
        Bundle args = new Bundle();
        args.putString(PROVIDED_ARTIST, artistName);
        fragment.setArguments(args);
        return fragment;
    }

    private TopAlbumsFragmentBinding mBindingObject;
    private String artist;

    private TopAlbumsAdapter mAlbumsAdapter;

    @Inject
    TopAlbumsContract.Presenter presenter;
    @Inject
    ToolBarOwner toolBarOwner;
    @Inject
    ImageLoader imageLoader;
    @Inject
    Conductor<BaseFragment> conductor;

    @Inject
    public TopAlbumsFragment() {
    }

    @Override
    public String getScreenTag() {
        return null;
    }

    @Nullable
    @Override
    protected TopAlbumsContract.Presenter providePresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @NonNull
    @Override
    protected View bindView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBindingObject = DataBindingUtil.inflate(inflater, R.layout.top_albums_fragment, container, false);
        artist = getArguments().getString(PROVIDED_ARTIST, "");
        toolBarOwner.showTitle(getString(R.string.search_albums, artist));
        toolBarOwner.showBackButton();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mBindingObject.albums.setLayoutManager(llm);
        mBindingObject.albums.addItemDecoration(new VerticalSpaceItemDecoration((int) getResources().getDimension(R.dimen.margin_small)));
        mBindingObject.albums.setAdapter(mAlbumsAdapter = new TopAlbumsAdapter(getContext(), imageLoader).setClickListener(this));
        presenter.getTopAlbums(artist);
        showProgressDialog("", getResources().getString(R.string.searching_albums));
        return mBindingObject.getRoot();
    }

    @Override
    public void notifyAlbumsLoaded(List<Album> albums) {
        hideProgressDialog();
        mAlbumsAdapter.add(albums);
    }

    @Override
    public void notifySearchFailure(String error) {
        hideProgressDialog();
        showMessage(error);
    }

    @Override
    public void onItemClick(Album album) {
        conductor.goTo(DetailsFragment.createInstance(album));
    }
}