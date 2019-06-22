package andrej.com.musicmanagement.data.topAlbumsPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleAlbums {

    @SerializedName("topalbums")
    @Expose
    private TopAlbums topalbums;

    public TopAlbums getTopalbums() {
        return topalbums;
    }

    public void setTopalbums(TopAlbums topalbums) {
        this.topalbums = topalbums;
    }

}
