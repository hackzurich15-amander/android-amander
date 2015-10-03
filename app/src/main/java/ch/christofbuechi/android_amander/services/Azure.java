package ch.christofbuechi.android_amander.services;

import java.util.List;

import ch.christofbuechi.android_amander.model.Data;
import ch.christofbuechi.android_amander.model.DataWrapper;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by christof on 03.10.15.
 */
public interface Azure {
    @GET("/magic/")
    Call<DataWrapper> trainignset();

    @POST("/magic/")
    Call<DataWrapper> getTrainingSet(@Body Data wrapper);

    @POST("/repos/")
    Call<List<DataWrapper>> trainignset(@Path("owner") String owner, @Path("repo") String repo);
}