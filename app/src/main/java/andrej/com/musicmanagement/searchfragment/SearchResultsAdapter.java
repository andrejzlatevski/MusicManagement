package andrej.com.musicmanagement.searchfragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.data.artistSearchPOJO.Artist;
import andrej.com.musicmanagement.databinding.ItemSearchBinding;
import andrej.com.musicmanagement.searchfragment.viewModel.ArtistViewModel;
import andrej.com.musicmanagement.view.ImageLoader;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.Holder> {

    public interface OnClickListener {
        void onItemClick(Artist artist);
    }

    private final LayoutInflater layoutInflater;
    private final ImageLoader imageLoader;
    private final Context mContext;
    private final ImageLoader.LoadingBuilder imageLoadingBuilder = new ImageLoader.LoadingBuilder()
            .setClearPrevState(true);
    private SearchResultsAdapter.OnClickListener onItemClickListener;
    private List<Artist> mData = new ArrayList<>();

    public SearchResultsAdapter(Context context, ImageLoader imageLoader) {
        this.layoutInflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;
        this.mContext = context;
    }

    public SearchResultsAdapter setClickListener(SearchResultsAdapter.OnClickListener clickListener) {
        this.onItemClickListener = clickListener;
        return this;
    }

    public void add(List<Artist> artists) {
        mData.clear();
        mData.addAll(artists);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public SearchResultsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultsAdapter.Holder((ItemSearchBinding) DataBindingUtil.inflate(layoutInflater,
                R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(final SearchResultsAdapter.Holder holder, final int position) {
        ArtistViewModel viewModel = holder.bindingObject.getModel();
        if (viewModel == null) {
            viewModel = new ArtistViewModel();
        }
        holder.bindingObject.setModel(viewModel.setArtist(mData.get(position),mContext));
        holder.bindingObject.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    final class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemSearchBinding bindingObject;

        public Holder(ItemSearchBinding binding) {
            super(binding.getRoot());
            bindingObject = binding;
            bindingObject.rootItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(mData.get(getAdapterPosition()));
            }
        }
    }
}