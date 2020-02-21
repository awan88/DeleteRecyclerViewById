package awan.project.deleterecyclerviewbyid.Api_interface;

import java.util.List;

import awan.project.deleterecyclerviewbyid.model.WPAuthor;
import awan.project.deleterecyclerviewbyid.model.WPPost;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitArrayApi {

    @GET("wp-json/wp/v2/posts?")
    Call<List<WPAuthor>> getPostAuthor(@Query("author[0]") Integer author, @Query("per_page") Integer perpage);

    @GET("wp-json/wp/v2/posts?per_page=1")
    Call<List<WPPost>> getPostHeader();

    @GET("wp-json/wp/v2/posts?per_page=40")
    Call<List<WPPost>> getPostInfo();

}
