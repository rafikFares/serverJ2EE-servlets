package extern.books_api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static database.security.AccessData.getAccessInstance;

public class GoogleApiClient {

    private static final String API_URL = "GoogleBooksApiUrl";
    private static IGoogleApi mApi = null;
    private static Retrofit mApiClient = null;

    public static IGoogleApi getApi() {
        if (mApi == null) {
            mApi = getClient(getAccessInstance().getResource(API_URL)).create(IGoogleApi.class);
        }
        return mApi;
    }


    private static Retrofit getClient(String baseUrl) {
        if (mApiClient == null) {
            mApiClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //in order for our calls to return type Observabl
                    .addConverterFactory(GsonConverterFactory.create()) //tell Retrofit which sort of converter I want it to use for serializing the JSON.
                    .build();
        }
        return mApiClient;
    }

}
