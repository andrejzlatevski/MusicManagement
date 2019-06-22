package andrej.com.musicmanagement.data;

import andrej.com.musicmanagement.data.artistSearchPOJO.Example;
import andrej.com.musicmanagement.data.topAlbumsPOJO.ExampleAlbums;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/2.0/")
    Single<Example> getArtist(@Query("method") String method, @Query("artist") String artist, @Query("api_key") String apiKey, @Query("format") String format);

    @GET("/2.0/")
    Single<ExampleAlbums> getTopAlbums(@Query("method") String method, @Query("artist") String artist, @Query("api_key") String apiKey, @Query("format") String format);
}
