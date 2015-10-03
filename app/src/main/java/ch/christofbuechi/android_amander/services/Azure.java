package ch.christofbuechi.android_amander.services;

import java.util.List;

import ch.christofbuechi.android_amander.model.DataWrapper;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by christof on 03.10.15.
 */
public interface Azure {
    @GET("/repos/")
    Call<DataWrapper> trainignset();

    @POST("/repos/")
    Call<List<DataWrapper>> trainignset(@Path("owner") String owner, @Path("repo") String repo);
}