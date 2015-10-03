package ch.christofbuechi.android_amander.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by christof on 03.10.15.
 */
public class ResponseDataWrapper {


    @SerializedName("data")
    List<Vehicle> vehicles;

}
