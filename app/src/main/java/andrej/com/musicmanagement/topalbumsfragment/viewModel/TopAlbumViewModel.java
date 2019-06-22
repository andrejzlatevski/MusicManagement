package andrej.com.musicmanagement.topalbumsfragment.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import andrej.com.musicmanagement.BR;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.data.topAlbumsPOJO.Album;

public class TopAlbumViewModel extends BaseObservable {

    private Album album;
    private Context mContext;

    public TopAlbumViewModel setArtist(Album album, Context context) {
        this.album = album;
        this.mContext = context;
        notifyPropertyChanged(BR.albumName);
        notifyPropertyChanged(BR.albumImage);
        notifyPropertyChanged(BR.albumPlayCount);
        notifyPropertyChanged(BR.placeholder);
        return this;
    }

    @Bindable
    public String getAlbumName() {
        if (album.getName() != null) {
            return album.getName();
        }
        return "";
    }

    @Bindable
    public String getAlbumPlayCount() {
        if (album.getPlaycount() != null) {
            return String.valueOf(album.getPlaycount());
        }
        return "";
    }

    @Bindable
    public String getAlbumImage() {
        if (album.getAlbumImage() != null && album.getAlbumImage().get(0) != null && album.getAlbumImage().get(0).getText()!=null) {
            return album.getAlbumImage().get(0).getText();
        }
        return "";
    }

    @Bindable
    public Drawable getPlaceholder(){
        return mContext.getResources().getDrawable(R.drawable.image_placeholder);
    }
}
