package andrej.com.musicmanagement.data.topAlbumsPOJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import andrej.com.musicmanagement.Constants;

@Entity(tableName = Constants.TABLE_NAME)
public class Album implements Parcelable{

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("playcount")
    @Expose
    @ColumnInfo(name = "playcount")
    private Integer playcount;

    @SerializedName("mbid")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mbid")
    private String mbid;

    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    private String url;

    @SerializedName("artist")
    @Expose
    @ColumnInfo(name = "artist")
    private AlbumArtist albumArtist;

    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    private List<AlbumImage> albumImage = null;

    public Album() {
    }

    protected Album(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            playcount = null;
        } else {
            playcount = in.readInt();
        }
        mbid = in.readString();
        url = in.readString();
        in.readList(albumImage,AlbumImage.class.getClassLoader());
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlaycount() {
        return playcount;
    }

    public void setPlaycount(Integer playcount) {
        this.playcount = playcount;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AlbumArtist getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(AlbumArtist albumArtist) {
        this.albumArtist = albumArtist;
    }

    public List<AlbumImage> getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(List<AlbumImage> albumImage) {
        this.albumImage = albumImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (playcount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(playcount);
        }
        dest.writeString(mbid);
        dest.writeString(url);
        dest.writeList(albumImage);
    }
}
