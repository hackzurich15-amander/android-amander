package ch.christofbuechi.android_amander;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.andtinder.model.CardModel;

import ch.christofbuechi.android_amander.model.Vehicle;

/**
 * Created by christof on 03.10.15.
 */
public class MyCarCardModel extends CardModel {


    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    Vehicle vehicle;


    public MyCarCardModel(AmanderSelectorActivity amanderSelectorActivity, String audi, String s, Drawable drawable) {
        super();
    }



    public MyCarCardModel(String title, String description, Drawable cardImage) {
        super(title, description, cardImage);
    }

    public MyCarCardModel(String title, String description, Bitmap cardImage) {
        super(title, description, cardImage);
    }





    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

}
