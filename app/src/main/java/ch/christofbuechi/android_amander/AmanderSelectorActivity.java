package ch.christofbuechi.android_amander;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andtinder.model.CardModel;

import java.util.ArrayList;
import java.util.List;

import ch.christofbuechi.android_amander.model.Data;
import ch.christofbuechi.android_amander.model.DataWrapper;
import ch.christofbuechi.android_amander.model.FilterInclude;
import ch.christofbuechi.android_amander.model.Wrapper;
import ch.christofbuechi.android_amander.services.Azure;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class AmanderSelectorActivity extends AppCompatActivity {

    private static final String API_URL = "http://amander.azurewebsites.net";
    private MyCardContainer mCardContainer;
    private MyCarCardStackAdapter adapter;
    private Resources resources;
    private Retrofit retrofit;
    private FilterInclude filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amander_selector);
        mCardContainer = (MyCardContainer) findViewById(R.id.layoutview2);
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        resources = getResources();
        adapter = new MyCarCardStackAdapter(this);

        //check if trainingset is available
        if (isTrainingSetAvailable()) {
            //Todo: read trainingset from sharedstorage
        } else {
            fetchDataTrainingSet();
        }

    }

    private void fetchDataTrainingSet() {

        Data wrapper = setupInitialRequestObject(10, filter, new ArrayList<Wrapper>());

        Azure azure = retrofit.create(Azure.class);
        Call<DataWrapper> call = azure.getTrainingSet(wrapper);



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

    private Data setupInitialRequestObject(int count, FilterInclude filter, List<Wrapper> list) {
        Data data = new Data();
        data.count = count;
        data.filterInclude = filter;
        data.data = list;
        return data;
    }

    private boolean isTrainingSetAvailable() {

        return false;
    }

}
