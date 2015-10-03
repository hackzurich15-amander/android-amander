package ch.christofbuechi.android_amander.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ch.christofbuechi.android_amander.model.Vehicle;

/**
 * Created by christof on 03.10.15.
 */
public class PictureFetchTask extends AsyncTask<List<Vehicle>, Void, List<Vehicle>> {


    @Override
    protected List<Vehicle> doInBackground(List<Vehicle>... params) {

        ArrayList<Vehicle> passed = (ArrayList<Vehicle>) params[0];



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

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
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
}
