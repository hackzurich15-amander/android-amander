package ch.christofbuechi.android_amander;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import ch.christofbuechi.android_amander.model.Wrapper;
import ch.christofbuechi.android_amander.services.Azure;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AmanderSelectorActivity extends AppCompatActivity {

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

        filter = new FilterInclude(brand,minps, maxprice);
        wrapper = setupInitialRequestObject(10, filter, new ArrayList<Wrapper>());


        resources = getResources();
        adapter = new MyCarCardStackAdapter(this);

        //check if trainingset is available
        if (isTrainingSetAvailable()) {
            //Todo: read trainingset from sharedstorage
        } else {
            fetchinitialDataTrainingSet();
        }

    }

    @Override
    protected void onDestroy() {
        //todo: write Trainingsset to sharedPref
        super.onDestroy();
    }

    private void fetchinitialDataTrainingSet() {

        Azure azure = retrofit.create(Azure.class);


        Call<ResponseDataWrapper> call = azure.loadRepo(wrapper);
        call.enqueue(new Callback<ResponseDataWrapper>() {
            @Override
            public void onResponse(Response<ResponseDataWrapper> response, Retrofit retrofit) {
                Log.d("CallBack", " response is " + response);
            }

            @Override
            public void onFailure(Throwable t) {


                Log.d("CallBack", " Throwable is " +t);
            }
        });





//        azure.getTrainingSet(wrapper, new Callback<DataWrapper>() {
//            @Override
//            public void onResponse(Response<DataWrapper> response, Retrofit retrofit) {
//                Log.d(this.getClass().getName(), "Response successfull");
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.d(this.getClass().getName(), "Response unsuccesfull");
//
//            }
//        });


        final MyCarCardModel cardModel = new MyCarCardModel("Audi", "Description goes here", resources.getDrawable(R.drawable.picture1));
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

        final MyCarCardModel cardModel1 = new MyCarCardModel("Mercedes", "Description goes here", resources.getDrawable(R.drawable.picture1));
        cardModel1.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                cardModel.setLike(true);
            }

            @Override
            public void onDislike() {
                cardModel.setLike(false);

            }
        });
        adapter.add(cardModel1);

        mCardContainer.setAdapter(adapter);


    }

    private RequestData setupInitialRequestObject(int count, FilterInclude filter, List<Wrapper> list) {
        RequestData requestData = new RequestData();
        requestData.count = count;
        requestData.filterInclude = filter;
        requestData.data = list;
        return requestData;
    }

    private boolean isTrainingSetAvailable() {
        //todo: check persisted json model

        Gson gson = new Gson();


        return false;
    }

}
