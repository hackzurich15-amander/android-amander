package ch.christofbuechi.android_amander.services;

import ch.christofbuechi.android_amander.model.Data;
import ch.christofbuechi.android_amander.model.DataWrapper;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by christof on 03.10.15.
 */
public interface Azure {
//    @GET("/magic/")
//    Call<DataWrapper> trainignset();

    @POST("/magic")
    Call<DataWrapper> loadRepo(@Body Data wrapper);

//    @POST("/repos/")
//    Call<List<DataWrapper>> trainignset(@Path("owner") String owner, @Path("repo") String repo);
}