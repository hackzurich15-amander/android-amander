package ch.christofbuechi.android_amander.services;

import java.util.List;

import ch.christofbuechi.android_amander.model.DataWrapper;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by christof on 03.10.15.
 */
public interface Azure {
    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<DataWrapper>> contributors(@Path("owner") String owner, @Path("repo") String repo);
}