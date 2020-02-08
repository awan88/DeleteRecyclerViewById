package awan.project.deleterecyclerviewbyid.Api_interface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://www.murianews.com/")
                    .build();
        }
        return retrofit;
    }

}
