package ch.christofbuechi.android_amander.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christof on 03.10.15.
 */
public class Wrapper {




    @SerializedName("input")
    @Expose
    private Input input;

    @SerializedName("output")
    @Expose
    private Output output;
}
