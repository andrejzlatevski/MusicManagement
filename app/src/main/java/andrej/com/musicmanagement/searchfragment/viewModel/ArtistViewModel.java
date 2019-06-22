package andrej.com.musicmanagement.searchfragment.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import andrej.com.musicmanagement.BR;
import andrej.com.musicmanagement.R;
import andrej.com.musicmanagement.data.artistSearchPOJO.Artist;

public class ArtistViewModel extends BaseObservable {

    private Artist artist;
    private Context mContext;

    public ArtistViewModel setArtist(Artist artist, Context context) {
        this.artist = artist;
        this.mContext = context;
        notifyPropertyChanged(BR.artistName);
        notifyPropertyChanged(BR.artistImage);
        notifyPropertyChanged(BR.artistListeners);
        notifyPropertyChanged(BR.placeholder);
        return this;
    }

    @Bindable
    public String getArtistName() {
        if (artist.getName() != null) {
            return artist.getName();
        }
        return "";
    }

    @Bindable
    public String getArtistListeners() {
        if (artist.getListeners() != null) {
            return artist.getListeners();
        }
        return "";
    }

    @Bindable
    public String getArtistImage() {
        if (artist.getImage() != null && artist.getImage().get(0) != null && artist.getImage().get(0).getText()!=null) {
            return artist.getImage().get(0).getText();
        }
        return "";
    }

    @Bindable
    public Drawable getPlaceholder(){
        return mContext.getResources().getDrawable(R.drawable.image_placeholder);
    }

}
