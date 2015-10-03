package ch.christofbuechi.android_amander.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.christofbuechi.android_amander.model.FilterInclude;
import ch.christofbuechi.android_amander.model.Vehicle;

/**
 * Created by longstone on 03/10/15.
 * Sorry for this, i really hate me for that, but hey, its a 'hack' athon
 */
public class DirtyDataPersistence {
    private List<Vehicle> toProcessEntries = new LinkedList<>();
    private List<Vehicle> processedEntries = new LinkedList<>();
    public final static DirtyDataPersistence INSTANCE = new DirtyDataPersistence();

    public FilterInclude getFilter() {
        return filter;
    }

    private FilterInclude filter;

    public void addReviewedVehicle(Vehicle vehicle){
        vehicle.imageBitmaps.clear();
        this.processedEntries.add(vehicle);
    }

    public List<Vehicle> getAllReviewdVehicle(){
        return new ArrayList<>(this.processedEntries);
    }

    public void addToDoVehicle(Vehicle vehicle){
        this.toProcessEntries.add(vehicle);
    }

    public void addTodoVehicleList(List<Vehicle> list ) {
        for (Vehicle vehicle: list) {
            addToDoVehicle(vehicle);
        }
    }

    public List<Vehicle> getAllToDoVehicle(){
        return new ArrayList<>(this.toProcessEntries);
    }
    public void clearToDoVehicles(){
        this.toProcessEntries.clear();
    }


    public void setFilter(FilterInclude filter) {
        this.filter = filter;
    }
}
