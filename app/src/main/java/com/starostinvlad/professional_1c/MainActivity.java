package com.starostinvlad.professional_1c;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import com.appodeal.ads.Appodeal;
import com.starostinvlad.professional_1c.Services.TestJobService;

import java.util.concurrent.TimeUnit;

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

        createNotificationChannel();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        startScheduleJob();
    }

    private void createNotificationChannel() {
        CharSequence name = "notification";
        String description = "description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("professional_1c", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    void startScheduleJob() {
        ComponentName serviceComponent = new ComponentName(this, TestJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(13101997, serviceComponent);
        builder.setPeriodic(TimeUnit.HOURS.toMillis(1), TimeUnit.MINUTES.toMillis(15));
        builder.setPersisted(true);
//            builder.setMinimumLatency(TimeUnit.SECONDS.toMillis(10)); // wait at least
//            builder.setOverrideDeadline(3 * 1000); // maximum delay
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }
    }
}
