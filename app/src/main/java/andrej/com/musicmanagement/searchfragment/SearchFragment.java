package andrej.com.musicmanagement.searchfragment;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import javax.inject.Inject;

import andrej.com.musicmanagement.Conductor;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.ToolBarOwner;
import andrej.com.musicmanagement.base.BaseFragment;
import andrej.com.musicmanagement.data.artistSearchPOJO.Artist;
import andrej.com.musicmanagement.databinding.SearchFragmentBinding;
import andrej.com.musicmanagement.decor.VerticalSpaceItemDecoration;
import andrej.com.musicmanagement.topalbumsfragment.TopAlbumsFragment;
import andrej.com.musicmanagement.view.ImageLoader;

public class SearchFragment extends BaseFragment<SearchContract.Presenter> implements SearchContract.View, SearchResultsAdapter.OnClickListener{

    public static BaseFragment createInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private SearchFragmentBinding mBindingObject;
    private SearchView searchView;

    private SearchResultsAdapter mResultsAdapter;

    @Inject
    SearchContract.Presenter presenter;
    @Inject
    ToolBarOwner toolBarOwner;
    @Inject
    Conductor<BaseFragment> conductor;
    @Inject
    ImageLoader imageLoader;

    @Inject
    public SearchFragment() {
    }

    @Override
    public String getScreenTag() {
        return null;
    }

    @Nullable
    @Override
    protected SearchContract.Presenter providePresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResultsAdapter = new SearchResultsAdapter(getContext(), imageLoader)
                .setClickListener(this);
        setHasOptionsMenu(true);
    }

    @NonNull
    @Override
    protected View bindView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBindingObject = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        toolBarOwner.showToolBar();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mBindingObject.artists.setLayoutManager(llm);
        mBindingObject.artists.addItemDecoration(new VerticalSpaceItemDecoration((int) getResources().getDimension(R.dimen.margin_small)));
        mBindingObject.artists.setAdapter(mResultsAdapter);
        if(mResultsAdapter.getItemCount()>0){
            mBindingObject.emptyState.setVisibility(View.GONE);
        }
        return mBindingObject.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                conductor.goBack();
                return false;
            }
        });
        if (searchView != null) {
            EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchEditText.setTextColor(Color.WHITE);
            searchEditText.setHintTextColor(Color.GRAY);
            searchEditText.setHint(getResources().getString(R.string.search));
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconified(false);
            searchItem.expandActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                boolean isQueryTextSubmit = false;

                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (!searchView.isIconified()) {
                        isQueryTextSubmit = true;
                        searchView.setQuery(query, false);
                        isQueryTextSubmit = false;
                        presenter.getArtist(query);
                        showProgressDialog("",getResources().getString(R.string.searching_artists));
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    if (isQueryTextSubmit)
                        return false;
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void notifyArtistsLoaded(List<Artist> artists) {
        hideProgressDialog();
        if(artists.size()>0){
            mBindingObject.emptyState.setVisibility(View.GONE);
        }
        mResultsAdapter.add(artists);
    }

    @Override
    public void notifySearchFailure(String error) {
        hideProgressDialog();
        showMessage(error);
    }

    @Override
    public void onItemClick(Artist artist) {
        conductor.goTo(TopAlbumsFragment.createInstance(artist.getName()));
    }
}