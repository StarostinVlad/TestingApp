package com.starostinvlad.professional_1c.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.starostinvlad.professional_1c.R;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends IntentService {
    private static final String SCHEDULED_ACTION = "scheduled_action";
    private static String EXTRA_MESSAGE = "extra_message";
    private String TAG = getClass().getSimpleName();

    public NotificationService() {
        super("notification_service");
    }

    static void startScheduledJob(Context context, String message) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(SCHEDULED_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.getAction().equals(SCHEDULED_ACTION)) {
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            Log.d(TAG, "onHandleIntent: execute");
            new Handler(getMainLooper()).post(
                    () -> {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "professional_1c")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("TEst")
                                .setContentText("TEST!")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(this);
                        notificationManager.notify(123, builder.build());
                    }

            );
        }
    }

}
