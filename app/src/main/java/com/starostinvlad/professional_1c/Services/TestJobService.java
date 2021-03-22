package com.starostinvlad.professional_1c.Services;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Bundle;
import android.util.Log;

import com.starostinvlad.professional_1c.App;
import com.starostinvlad.professional_1c.Database.AppDatabase;
import com.starostinvlad.professional_1c.R;

import java.util.Locale;
import java.util.Random;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestJobService extends JobService {
    private static final String TAG = "SyncService";
    private static final String TITLE = "Пора учиться!";

    private Disposable disposable;
    private final String ERRORS_MSG = "У вас %d ошибок, самое время их исправить",
            THEME_MSG = "Проверьте свои знания по теме",
            EXAM_MESSAGE = "Самое время пройти экзамен";
    private AppDatabase appDb;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob:");
        appDb = App.getDatabase();
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                loadExam();
                break;
            case 1:
                loadTheme();
                break;
            default:
                loadErrors();
                break;
        }

        return true;
    }

    void loadExam() {
        PendingIntent pendingIntent = new NavDeepLinkBuilder(this)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.testFragment)
                .createPendingIntent();
        showNotification(EXAM_MESSAGE, pendingIntent);
    }

    void loadErrors() {
        disposable = appDb.questionDao().getWrongQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(val ->
                {
                    Bundle args = new Bundle();
                    args.putBoolean("ERRORS", true);
                    PendingIntent pendingIntent = new NavDeepLinkBuilder(this)
                            .setGraph(R.navigation.nav_graph)
                            .setDestination(R.id.testFragment)
                            .setArguments(args)
                            .createPendingIntent();

                    showNotification(
                            String.format(
                                    Locale.getDefault(),
                                    ERRORS_MSG,
                                    val.size()
                            ),
                            pendingIntent);
                }, Throwable::printStackTrace);
    }

    void loadTheme() {
        disposable = appDb.themeDao().getRandomTheme()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(val ->
                {
                    Bundle args = new Bundle();
                    args.putSerializable("theme", val);
                    PendingIntent pendingIntent = new NavDeepLinkBuilder(this)
                            .setGraph(R.navigation.nav_graph)
                            .setDestination(R.id.testFragment)
                            .setArguments(args)
                            .createPendingIntent();
                    showNotification(
                            THEME_MSG,
                            pendingIntent
                    );
                }, Throwable::printStackTrace);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        disposable.dispose();
        return true;
    }

    void showNotification(String content, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "professional_1c")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(TITLE)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
        if (disposable != null)
            disposable.dispose();
        stopSelf();
    }

}