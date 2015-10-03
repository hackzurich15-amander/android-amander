package ch.christofbuechi.android_amander.model;

/**
 * Created by christof on 03.10.15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RequestData {

    @SerializedName("trainingSet")
    @Expose
    public List<Wrapper> data = new ArrayList<Wrapper>();
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("filterInclude")
    @Expose
    public FilterInclude filterInclude;

    public static List<Wrapper> fromVehicles(List<Vehicle> vehicles) {
        List<Wrapper> wrapped = new ArrayList<>(vehicles.size());
        for (Vehicle vehicle : vehicles) {
            wrapped.add(Wrapper.fromVehicle(vehicle));
        }
        return  wrapped;
    }
}