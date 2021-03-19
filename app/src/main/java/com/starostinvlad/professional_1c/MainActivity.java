package com.starostinvlad.professional_1c;

import android.os.Bundle;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.utils.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Appodeal.initialize(this, "d602f62a335d68bd4ecc0154d3e541a918f2fcb805c76e0f", Appodeal.BANNER | Appodeal.INTERSTITIAL, false);
//        Appodeal.setAutoCache(Appodeal.INTERSTITIAL, false);
        Appodeal.setTesting(BuildConfig.DEBUG);

//        Appodeal.setLogLevel(Log.LogLevel.verbose);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }
}
