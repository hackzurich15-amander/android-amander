package ch.christofbuechi.android_amander.services;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ch.christofbuechi.android_amander.model.Vehicle;

/**
 * Created by christof on 03.10.15.
 */
public class PictureFetchTask extends AsyncTask<Void, Void, List<Vehicle>> {

    private static final String TAG = "PictureFetchTask";
    private final ArrayList<Vehicle> freshVehicles;
    private final ProcessFinishedCallback callback;
    private Activity act;

    public PictureFetchTask(Activity act, List<Vehicle> freshVehicles, ProcessFinishedCallback callback) {
        this.act = act;
        this.freshVehicles = new ArrayList<>(freshVehicles);
        this.callback = callback;
    }

    @Override
    protected List<Vehicle> doInBackground(Void... params) {
        Log.i(TAG, "loading started");
        ArrayList<Vehicle> passed = freshVehicles;


        for (Vehicle v : passed) {
            v.imageBitmaps = new ArrayList<>();

            for (int i = 0; i < v.imageUrls.size(); i++) {
                try {
                    v.imageBitmaps.add(getBitmap(new URL(v.imageUrls.get(i))));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(this.getClass().getName(), "All Vehicle Pictures loaded");


        return passed;
    }

    private Bitmap getBitmap(URL url) {

        Log.d(this.getClass().getName(), "Fetch Bitmap from URL: " + url);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15 * 1000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    private Bitmap getBitmapWithGlide(URL url) {
        Log.d(this.getClass().getName(), "Fetch Bitmap from URL: " + url);
        try {
            return Glide.
                    with(act).
                    load(url).
                    asBitmap().
                    into(300, 300).
                    get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Vehicle> vehicles) {
        Log.i(TAG, "onPostExecute");
        callback.processFinished();


    }
}
