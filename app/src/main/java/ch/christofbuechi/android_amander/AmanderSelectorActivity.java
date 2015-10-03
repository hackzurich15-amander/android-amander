package ch.christofbuechi.android_amander;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andtinder.model.CardModel;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class AmanderSelectorActivity extends AppCompatActivity {

    private static final String API_URL = "http://4amander.cloudapp.net";
    private MyCardContainer mCardContainer;
    private MyCarCardStackAdapter adapter;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amander_selector);
        mCardContainer = (MyCardContainer) findViewById(R.id.layoutview2);
        Retrofit retrofit = new Retrofit.Builder()
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

        mCardContainer.setAdapter(adapter);


    }

    private boolean isTrainingSetAvailable() {

        return false;
    }

}
