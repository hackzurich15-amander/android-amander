package ch.christofbuechi.android_amander.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christof on 03.10.15.
 */
public class Output {

    public Integer getMatch() {
        return match;
    }

    public Boolean getMatchBoolean() {
        return match == 1 ? true : false;
    }

    public void setMatch(Integer match) {
        this.match = match;
    }

    public void setMatchBoolean(Boolean match) {
        this.match = match ? 1 : 0;
    }

    @SerializedName("match")
    @Expose
    private Integer match;
}
