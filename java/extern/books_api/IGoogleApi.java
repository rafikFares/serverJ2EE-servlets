package extern.books_api;


import extern.books_api.res.google.GoogleBooksResult;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGoogleApi {

    //?q=subject:fiction&maxResults=2

    @GET("volumes")
    Single<GoogleBooksResult> getBooks(
            @Query("q") String q,
            @Query("maxResults") String maxResults);


}


