package ch.christofbuechi.android_amander.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christof on 03.10.15.
 */
public class TrainingSet {

    @SerializedName("input")
    @Expose
        private Wrapper input;


    public TrainingSet(Wrapper input) {
        this.input = input;
    }
}
