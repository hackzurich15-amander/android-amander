package ch.christofbuechi.android_amander;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import ch.christofbuechi.android_amander.model.FilterInclude;
import ch.christofbuechi.android_amander.model.RequestData;
import ch.christofbuechi.android_amander.model.ResponseDataWrapper;
import ch.christofbuechi.android_amander.model.Vehicle;
import ch.christofbuechi.android_amander.services.Azure;
import ch.christofbuechi.android_amander.services.DirtyDataPersistence;
import ch.christofbuechi.android_amander.services.PictureFetchTask;
import ch.christofbuechi.android_amander.services.ProcessFinishedCallback;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by christof on 03.10.15.
 */
public class LoadingScreen extends AppCompatActivity {
    private static final String API_URL = "http://amander.azurewebsites.net";
    private Retrofit retrofit;
    private ProgressDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            int maxprice = intent.getIntExtra("price", 0);
            int minps = intent.getIntExtra("ps", 0);
            String brand = intent.getStringExtra("brand");
            DirtyDataPersistence.INSTANCE.setFilter(new FilterInclude(brand, minps, maxprice));
        }

        mDialog = new ProgressDialog(getApplicationContext());
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(false);

        setContentView(R.layout.activity_loading_indicator);

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        fetchinitialDataTrainingSet();
    }


    private void fetchinitialDataTrainingSet() {
        mDialog.show();


        Azure azure = retrofit.create(Azure.class);

        Call<ResponseDataWrapper> call = azure.loadRepo(setupRequestWithTrainingsSet());
        call.enqueue(new Callback<ResponseDataWrapper>() {
            @Override
            public void onResponse(Response<ResponseDataWrapper> response, Retrofit retrofit) {
                final List<Vehicle> freshVehicles = response.body().getVehicles();

                PictureFetchTask task = new PictureFetchTask(freshVehicles, new ProcessFinishedCallback() {
                    @Override
                    public void processFinished() {
                        DirtyDataPersistence.INSTANCE.addTodoVehicleList(freshVehicles);
                        mDialog.dismiss();

                        Intent intent = new Intent(LoadingScreen.this, AmanderSelectorActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                task.execute();


            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("CallBack", " Throwable is " + t);
            }
        });

    }

    private RequestData setupRequestWithTrainingsSet() {
        RequestData requestData = new RequestData();
        requestData.count = 10;
        requestData.filterInclude = DirtyDataPersistence.INSTANCE.getFilter();
//        requestData.data = RequestData.fromVehicles(vehicle);
        return requestData;
    }
}
