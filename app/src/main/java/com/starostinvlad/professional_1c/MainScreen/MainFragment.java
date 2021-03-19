package com.starostinvlad.professional_1c.MainScreen;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.starostinvlad.professional_1c.Models.UserProgress;
import com.starostinvlad.professional_1c.Views.ProgressButton;
import com.starostinvlad.professional_1c.R;
import com.starostinvlad.professional_1c.Services.TestJobService;
import com.starostinvlad.professional_1c.ThemesScreen.CustomProgressBar;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class MainFragment extends Fragment implements MainFragmentContract {

    ProgressButton loadBtn;
    private MainFragmentPresenter presenter;
    private Button button, examBtn, errorsBtn;
    private CustomProgressBar progressBar;
    private int progress;
    private TextView textProgress, progressPerExam, readyForExamInPercent;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        createNotificationChannel();

        presenter = new MainFragmentPresenter(this);
        button = view.findViewById(R.id.theme_btn);
        examBtn = view.findViewById(R.id.exam_btn);

        errorsBtn = view.findViewById(R.id.errors_btn);

        progressBar = view.findViewById(R.id.progress_user);
        textProgress = view.findViewById(R.id.text_progress);
        progressPerExam = view.findViewById(R.id.progressPerExam);
        readyForExamInPercent = view.findViewById(R.id.readyForExamInPercent);
        loadBtn = view.findViewById(R.id.load_btn);


        presenter.loadStatistic();

        loadBtn.setOnButtonClickListener(v -> {
            ComponentName serviceComponent = new ComponentName(getContext(), TestJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(13101997, serviceComponent);
            builder.setPeriodic(TimeUnit.MINUTES.toMillis(5));
            builder.setPersisted(true);
//            builder.setMinimumLatency(TimeUnit.MINUTES.toMillis(15)); // wait at least
//            builder.setOverrideDeadline(3 * 1000); // maximum delay
            //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
            //builder.setRequiresDeviceIdle(true); // device should be idle
            //builder.setRequiresCharging(false); // we don't care if the device is charging or not
            JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (jobScheduler != null) {
                jobScheduler.schedule(builder.build());
            }
//            presenter.loadAll();
        });

        examBtn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_testFragment)
        );

        button.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_themesFragment)
        );

        errorsBtn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("ERRORS", true);
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_testFragment, bundle);
        });

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notification";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("professional_1c", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showResult(int count) {
        String message = String.format("Загруженно : %s тем", count);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(UserProgress progress) {

        textProgress.setText(progress.toString());
        progressPerExam.setText(
                getResources().getString(R.string.progressPerExam,
                        progress.getAnswersPerExam())
        );

        progressBar.setMax((int) progress.getTotalCount());

        if (progress.getAnswersPerExam() > 0)
            readyForExamInPercent.setText(
                    getResources().getString(
                            R.string.readyForExamInpercent,
                            ((float) progress.getCorrectCount() / (float) progress.getTotalCount() * (float) progress.getAnswersPerExam() / 14 * 100)
                    )
            );
        progressBar.setProgress((int) progress.getCorrectCount());
    }

    @Override
    public void showLoading(boolean show) {
        loadBtn.showLoading(show);
    }
}
