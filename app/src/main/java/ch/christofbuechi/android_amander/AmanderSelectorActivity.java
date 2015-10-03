package ch.christofbuechi.android_amander;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.andtinder.model.CardModel;

import java.util.List;

import ch.christofbuechi.android_amander.model.Vehicle;
import ch.christofbuechi.android_amander.services.DirtyDataPersistence;

public class AmanderSelectorActivity extends AppCompatActivity {


    private MyCardContainer mCardContainer;
    private MyCarCardStackAdapter adapter;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amander_selector);
        mCardContainer = (MyCardContainer) findViewById(R.id.layoutview2);
        resources = getResources();




        List<Vehicle> list = DirtyDataPersistence.INSTANCE.getAllToDoVehicle();
        addToModel(list);


    }


    private void addToModel(List<Vehicle> freshVehicles) {
        adapter = new MyCarCardStackAdapter(this);
        for (final Vehicle vehicle : freshVehicles) {
            Log.d(this.getClass().getName(), "Fetched Vehicle: " + vehicle.brand);

            final MyCarCardModel cardModel = new MyCarCardModel(vehicle.brand, decriptionFromVehicle(vehicle.price + "", vehicle.modelDe, vehicle.fuelType, vehicle.powerHp + ""), resources.getDrawable(R.drawable.picture1));
            cardModel.setVehicle(vehicle);
            cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                @Override
                public void onLike() {
                    vehicle.match = 1.0;
                }

                @Override
                public void onDislike() {
                    vehicle.match = 0.0;
                }
            });
            adapter.add(cardModel);
        }
        mCardContainer.setAdapter(adapter);
    }

    private String decriptionFromVehicle(String... magic) {
        return TextUtils.join("\n", magic);
    }



    private List<Vehicle> load() {
        return DirtyDataPersistence.INSTANCE.getAllReviewdVehicle();
    }

}
