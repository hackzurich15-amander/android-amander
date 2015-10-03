package ch.christofbuechi.android_amander.model;

/**
 * Created by christof on 03.10.15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Data {

    @SerializedName("trainingSet")
    @Expose
    public List<Wrapper> data = new ArrayList<Wrapper>();
    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("filterInclude")
    @Expose
    public FilterInclude filterInclude;



}