package ch.christofbuechi.android_amander;

import android.content.Intent;
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


    private static final String TAG ="AmanderSelectorActivity" ;
    private MyCardContainer mCardContainer;
    private MyCarCardStackAdapter adapter;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getName(), "Start Activity");
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

            final MyCarCardModel cardModel = new MyCarCardModel( vehicle.brand, decriptionFromVehicle("Preis :" +vehicle.price +" Chf", "Beschreibung:\n"+vehicle.modelDe, "Betriebsstoff: "+getGasolineDescription(vehicle.fuelType), "PS: "+vehicle.powerHp), resources.getDrawable(R.drawable.picture1));
            cardModel.setVehicle(vehicle);
            cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                @Override
                public void onLike() {
                    vehicle.match = 1.0;
                    checkEmpty();
                    addToDone(vehicle);
                }

                @Override
                public void onDislike() {
                    vehicle.match = 0.0;
                    checkEmpty();
                    addToDone(vehicle);
                }
            });

            adapter.add(cardModel);
        }
        mCardContainer.setAdapter(adapter);
    }

    private String getGasolineDescription(String fuelType) {
        switch(fuelType){
            case "B":return "Benzin";
            case "D":return "Diesel";
            case "E":return "Elektrisch";
            case "G":return "Gas & Benzin";
            case "PH":return "Plugin-Hybrid";
        }
        return "Impossibru!!! :"+fuelType;
    }

    private void addToDone(Vehicle vehicle) {
        DirtyDataPersistence.INSTANCE.addReviewedVehicle(vehicle);
    }

    private void checkEmpty() {
        adapter.pop();
        Log.i(TAG, "elements in adapter "+adapter.getCount());
       if( adapter.isEmpty()){
           Intent intent = new Intent(AmanderSelectorActivity.this, LoadingScreenActivity.class);
           startActivity(intent);
       }
    }

    private String decriptionFromVehicle(String... magic) {
        String str = TextUtils.join("\n", magic);
        return str;
    }



    private List<Vehicle> load() {
        return DirtyDataPersistence.INSTANCE.getAllReviewdVehicle();
    }

}
