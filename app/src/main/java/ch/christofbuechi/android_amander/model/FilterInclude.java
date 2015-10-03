package ch.christofbuechi.android_amander.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christof on 03.10.15.
 */
public class FilterInclude {

    @SerializedName("brand")
    @Expose
    public String brand;
    @SerializedName("psMin")
    @Expose
    public Integer psMin;
    @SerializedName("priceMax")
    @Expose
    public Integer priceMax;

    public FilterInclude(String brand, Integer psMin, Integer priceMax) {
        this.brand = brand;
        this.psMin = psMin;
        this.priceMax = priceMax;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPsMin() {
        return psMin;
    }

    public void setPsMin(Integer psMin) {
        this.psMin = psMin;
    }

    public Integer getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }
}
