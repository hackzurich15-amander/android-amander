package ch.christofbuechi.android_amander.model;

/**
 * Created by christof on 03.10.15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Data {

    @SerializedName("data")
    @Expose
    public List<Information> data = new ArrayList<Information>();
    @SerializedName("offset")
    @Expose
    public Integer offset;

}