package ch.christofbuechi.android_amander.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christof on 03.10.15.
 */
public class TrainingSetRequestWrapper {

    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("trainingSet")
    @Expose
    private List<TrainingSet> trainingSet = new ArrayList<TrainingSet>();

    public TrainingSetRequestWrapper(Integer offset, Integer count, List<TrainingSet> trainingSet) {
        this.offset = offset;
        this.count = count;
        this.trainingSet = trainingSet;
    }

    public List<TrainingSet> getTrainingSet() {
        return trainingSet;
    }

    public void setTrainingSet(List<TrainingSet> trainingSet) {
        this.trainingSet = trainingSet;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
