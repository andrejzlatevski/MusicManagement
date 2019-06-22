package andrej.com.musicmanagement.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import andrej.com.musicmanagement.Constants;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.databinding.ItemAlbumBinding;
import andrej.com.musicmanagement.main.viewModel.AlbumViewModel;
import andrej.com.musicmanagement.view.ImageLoader;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Holder> {

    public interface OnClickListener {
        //void onItemClick(Album album);
    }

    private final LayoutInflater layoutInflater;
    private final ImageLoader imageLoader;
    private final Context mContext;
    private final ImageLoader.LoadingBuilder imageLoadingBuilder = new ImageLoader.LoadingBuilder()
            .setClearPrevState(true);
    private OnClickListener onItemClickListener;
    //private List<Album> mData = new ArrayList<>();

    public MainAdapter(Context context, ImageLoader imageLoader) {
        this.layoutInflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;
        this.mContext = context;
    }

    public MainAdapter setClickListener(OnClickListener clickListener) {
        this.onItemClickListener = clickListener;
        return this;
    }

    /*public void add(Album album) {

    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void remove(Album album) {

    }*/

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemAlbumBinding) DataBindingUtil.inflate(layoutInflater,
                R.layout.item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        AlbumViewModel viewModel = holder.bindingObject.getModel();
        if (viewModel == null) {
            viewModel = new AlbumViewModel();
        }
        holder.bindingObject.executePendingBindings();
    }

    /*@Override
    public long getItemId(int position) {
        if (position==0) {
            return 0;
        }
        return mData.get(position).getMbid().hashCode();
    }*/

    @Override
    public int getItemCount() {
        return 0;
    }

    final class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemAlbumBinding bindingObject;

        public Holder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            bindingObject = binding;

        }

        @Override
        public void onClick(View view) {

        }
    }
}