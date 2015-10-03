package ch.christofbuechi.android_amander;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.andtinder.model.CardModel;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import ch.christofbuechi.android_amander.model.FilterInclude;
import ch.christofbuechi.android_amander.model.RequestData;
import ch.christofbuechi.android_amander.model.ResponseDataWrapper;
import ch.christofbuechi.android_amander.model.Vehicle;
import ch.christofbuechi.android_amander.model.Wrapper;
import ch.christofbuechi.android_amander.services.Azure;
import ch.christofbuechi.android_amander.services.DirtyDataPersistence;
import ch.christofbuechi.android_amander.services.PictureFetchTask;
import ch.christofbuechi.android_amander.services.ProcessFinishedCallback;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AmanderSelectorActivity extends AppCompatActivity {
    private static final String VEHICLES_KEY = "VEHICLES_LEARNED";


    private static final String API_URL = "http://amander.azurewebsites.net";
    private MyCardContainer mCardContainer;
    private MyCarCardStackAdapter adapter;
    private Resources resources;
    private Retrofit retrofit;
    private FilterInclude filter;
    private RequestData wrapper;
    private SharedPreferences mPrefs;

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

        fetchinitialDataTrainingSet();


    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        mPrefs = getPreferences(MODE_PRIVATE);
        return super.onCreateView(name, context, attrs);
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
        super.onDestroy();
    }

    private void fetchinitialDataTrainingSet() {

        Azure azure = retrofit.create(Azure.class);


        Call<ResponseDataWrapper> call = azure.loadRepo(wrapper);
        call.enqueue(new Callback<ResponseDataWrapper>() {
            @Override
            public void onResponse(Response<ResponseDataWrapper> response, Retrofit retrofit) {
                final List<Vehicle> freshVehicles = response.body().getVehicles();

                PictureFetchTask task = new PictureFetchTask(freshVehicles, new ProcessFinishedCallback() {
                    @Override
                    public void processFinished() {
                        addToModel(freshVehicles);
                    }
                });
                task.execute();


            }

            @Override
            public void onFailure(Throwable t) {

                //  Log.d("CallBack", " Throwable is " + t);
            }
        });




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
        if (load() != null) {
            return load().isEmpty();
        }
        return false;
    }

    private void save(Vehicle vehicle) {
        DirtyDataPersistence.INSTANCE.addReviewedVehicle(vehicle);
    }

    private List<Vehicle> load() {
        return DirtyDataPersistence.INSTANCE.getAllReviewdVehicle();
    }

}
