package ch.christofbuechi.android_amander.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by christof on 03.10.15.
 */
public class ResponseDataWrapper {


    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @SerializedName("data")
    List<Vehicle> vehicles;

}
