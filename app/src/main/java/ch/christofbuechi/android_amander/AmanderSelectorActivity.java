package ch.christofbuechi.android_amander;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.andtinder.model.CardModel;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import ch.christofbuechi.android_amander.model.RequestData;
import ch.christofbuechi.android_amander.model.ResponseDataWrapper;
import ch.christofbuechi.android_amander.model.FilterInclude;
import ch.christofbuechi.android_amander.model.Vehicle;
import ch.christofbuechi.android_amander.model.Wrapper;
import ch.christofbuechi.android_amander.services.Azure;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AmanderSelectorActivity extends AppCompatActivity {
    private static final String VEHICLES_KEY = "VEHICLES_LEARNED";
    SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

    private static final String API_URL = "http://amander.azurewebsites.net";
    private MyCardContainer mCardContainer;
    private MyCarCardStackAdapter adapter;
    private Resources resources;
    private Retrofit retrofit;
    private FilterInclude filter;
    private RequestData wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amander_selector);
        mCardContainer = (MyCardContainer) findViewById(R.id.layoutview2);

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        Intent intent = getIntent();
        int maxprice = intent.getIntExtra("price", 0);
        int minps = intent.getIntExtra("ps", 0);
        String brand = intent.getStringExtra("brand");

        filter = new FilterInclude(brand, minps, maxprice);
        if (isTrainingSetAvailable()) {
            wrapper = setupRequestWithTrainingsSet(load());
        } else {
            wrapper = setupInitialRequestObject(10, filter, new ArrayList<Wrapper>());
        }

        resources = getResources();
        adapter = new MyCarCardStackAdapter(this);


    }

    private RequestData setupRequestWithTrainingsSet(List<Vehicle> vehicle) {
        RequestData requestData = new RequestData();
        requestData.count = 10;
        requestData.filterInclude = filter;
        requestData.data = RequestData.fromVehicles(vehicle);
        return requestData;
    }

    @Override
    protected void onDestroy() {
        //todo: write Trainingsset to sharedPref
        save();
        super.onDestroy();
    }

    private void fetchinitialDataTrainingSet() {

        Azure azure = retrofit.create(Azure.class);


        Call<ResponseDataWrapper> call = azure.loadRepo(wrapper);
        call.enqueue(new Callback<ResponseDataWrapper>() {
            @Override
            public void onResponse(Response<ResponseDataWrapper> response, Retrofit retrofit) {
                // Log.d("CallBack", " response is " + response);
                List<Vehicle> freshVehicles = ((ResponseDataWrapper) response.body()).getVehicles();
                for (Vehicle vehicle : freshVehicles) {
                    final MyCarCardModel cardModel = new MyCarCardModel(vehicle.brand, decriptionFromVehicle(vehicle.price + "", vehicle.modelDe, vehicle.fuelType, vehicle.powerHp + ""), resources.getDrawable(R.drawable.picture1));
                    cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                        @Override
                        public void onLike() {
                            cardModel.setLike(true);
                        }

                        @Override
                        public void onDislike() {
                            cardModel.setLike(false);

                        }
                    });
                    adapter.add(cardModel);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Throwable t) {

                //  Log.d("CallBack", " Throwable is " + t);
            }
        });

        mCardContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String decriptionFromVehicle(String... magic) {
        return TextUtils.join("\n", magic);
    }

    private RequestData setupInitialRequestObject(int count, FilterInclude filter, List<Wrapper> list) {
        RequestData requestData = new RequestData();
        requestData.count = count;
        requestData.filterInclude = filter;
        requestData.data = list;
        return requestData;
    }

    private boolean isTrainingSetAvailable() {
        return load().isEmpty();
    }

    private void save(List<Vehicle> vehicles) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(vehicles); // myObject - instance of MyObject
        prefsEditor.putString(VEHICLES_KEY, json);
        prefsEditor.commit();
    }

    private List<Vehicle> load() {
        Gson gson = new Gson();
        String json = mPrefs.getString(VEHICLES_KEY, "[]");
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Vehicle>>() {
        }.getType();
        return gson.fromJson(json, type);

    }

}
