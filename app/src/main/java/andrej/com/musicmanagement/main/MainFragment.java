package andrej.com.musicmanagement.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import andrej.com.musicmanagement.Conductor;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.ToolBarOwner;
import andrej.com.musicmanagement.base.BaseFragment;
import andrej.com.musicmanagement.data.ApiService;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import andrej.com.musicmanagement.databinding.MainFragmentBinding;
import andrej.com.musicmanagement.decor.VerticalSpaceItemDecoration;
import andrej.com.musicmanagement.detailsfragment.DetailsFragment;
import andrej.com.musicmanagement.searchfragment.SearchFragment;
import andrej.com.musicmanagement.topalbumsfragment.TopAlbumsAdapter;
import andrej.com.musicmanagement.view.ImageLoader;

public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View, TopAlbumsAdapter.OnClickListener {

    public static BaseFragment createInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private MainFragmentBinding mBindingObject;
    private TopAlbumsAdapter mAlbumsAdapter;

    @Inject
    MainContract.Presenter presenter;
    @Inject
    ToolBarOwner toolBarOwner;
    @Inject
    ApiService apiService;
    @Inject
    Conductor<BaseFragment> conductor;
    @Inject
    ImageLoader imageLoader;

    @Inject
    public MainFragment() {
    }

    @Override
    public String getScreenTag() {
        return null;
    }

    @Nullable
    @Override
    protected MainContract.Presenter providePresenter() {
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
        mBindingObject = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        toolBarOwner.showTitle(getString(R.string.saved_albums));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mBindingObject.albums.setLayoutManager(llm);
        mBindingObject.albums.addItemDecoration(new VerticalSpaceItemDecoration((int) getResources().getDimension(R.dimen.margin_small)));
        mBindingObject.albums.setAdapter(mAlbumsAdapter = new TopAlbumsAdapter(getContext(), imageLoader).setClickListener(this));
        presenter.getAllAlbums();
        return mBindingObject.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                conductor.goTo(SearchFragment.createInstance());
                return true;
        }
        return false;
    }

    @Override
    public void notifyAlbumsLoaded(List<Album> albums) {
        if (albums.size() > 0) {
            mBindingObject.emptyState.setVisibility(View.GONE);
        }
        mAlbumsAdapter.add(albums);
    }

    @Override
    public void notifyAlbimsLoadingError(String error) {
        showMessage(error);
    }

    @Override
    public void onItemClick(Album album) {
        conductor.goTo(DetailsFragment.createInstance(album));
    }
}
