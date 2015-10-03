package ch.christofbuechi.android_amander;

/**
 * Created by christof on 03.10.15.
 */
import android.app.Application;
import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
}