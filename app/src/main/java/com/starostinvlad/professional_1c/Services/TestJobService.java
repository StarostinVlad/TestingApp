package com.starostinvlad.professional_1c.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.starostinvlad.professional_1c.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TestJobService extends JobService {
    private static final String TAG = "SyncService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob:");
//        NotificationService.startScheduledJob(getApplicationContext(), "Start service");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "professional_1c")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("TEst")
                .setContentText("TEST!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(123, builder.build());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}