package ch.christofbuechi.android_amander.model;

/**
 * Created by christof on 03.10.15.
 */
import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Vehicle {

    @SerializedName("vin")
    @Expose
    public String vin;
    @SerializedName("brand")
    @Expose
    public String brand;
    @SerializedName("model_de")
    @Expose
    public String modelDe;
    @SerializedName("engine_de")
    @Expose
    public String engineDe;
    @SerializedName("fuel_type")
    @Expose
    public String fuelType;
    @SerializedName("engine_capacity")
    @Expose
    public Integer engineCapacity;
    @SerializedName("power_hp")
    @Expose
    public Integer powerHp;
    @SerializedName("sale_type")
    @Expose
    public String saleType;
    @SerializedName("emissions")
    @Expose
    public Integer emissions;
    @SerializedName("additional_title")
    @Expose
    public String additionalTitle;
    @SerializedName("mileage")
    @Expose
    public Integer mileage;
    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("seats")
    @Expose
    public Integer seats;
    @SerializedName("sport_score")
    @Expose
    public Integer sportScore;
    @SerializedName("family_score")
    @Expose
    public Integer familyScore;
    @SerializedName("eco_score")
    @Expose
    public Integer ecoScore;
    @SerializedName("price_score")
    @Expose
    public Integer priceScore;
    @SerializedName("offroad_score")
    @Expose
    public Integer offroadScore;
    @SerializedName("design_score")
    @Expose
    public Integer designScore;
    @SerializedName("match")
    @Expose
    public Double match;

    @SerializedName("imageUrl")
    @Expose
    public List<String> imageUrls;


    public transient List<Bitmap> imageBitmaps;


}