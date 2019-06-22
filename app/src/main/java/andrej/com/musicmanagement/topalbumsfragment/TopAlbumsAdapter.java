package andrej.com.musicmanagement.topalbumsfragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;
import andrej.com.musicmanagement.databinding.ItemTopAlbumBinding;
import andrej.com.musicmanagement.topalbumsfragment.viewModel.TopAlbumViewModel;
import andrej.com.musicmanagement.view.ImageLoader;

public class TopAlbumsAdapter extends RecyclerView.Adapter<TopAlbumsAdapter.Holder> {

    public interface OnClickListener {
        void onItemClick(Album album);
    }

    private final LayoutInflater layoutInflater;
    private final ImageLoader imageLoader;
    private final Context mContext;
    private final ImageLoader.LoadingBuilder imageLoadingBuilder = new ImageLoader.LoadingBuilder()
            .setClearPrevState(true);
    private TopAlbumsAdapter.OnClickListener onItemClickListener;
    private List<Album> mData = new ArrayList<>();

    public TopAlbumsAdapter(Context context, ImageLoader imageLoader) {
        this.layoutInflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;
        this.mContext = context;
    }

    public TopAlbumsAdapter setClickListener(TopAlbumsAdapter.OnClickListener clickListener) {
        this.onItemClickListener = clickListener;
        return this;
    }

    public void add(List<Album> albums) {
        mData.clear();
        mData.addAll(albums);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public TopAlbumsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopAlbumsAdapter.Holder((ItemTopAlbumBinding) DataBindingUtil.inflate(layoutInflater,
                R.layout.item_top_album, parent, false));
    }

    @Override
    public void onBindViewHolder(final TopAlbumsAdapter.Holder holder, final int position) {
        TopAlbumViewModel viewModel = holder.bindingObject.getModel();
        if (viewModel == null) {
            viewModel = new TopAlbumViewModel();
        }
        holder.bindingObject.setModel(viewModel.setArtist(mData.get(position),mContext));
        holder.bindingObject.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    final class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemTopAlbumBinding bindingObject;

        public Holder(ItemTopAlbumBinding binding) {
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