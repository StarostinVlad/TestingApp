package com.starostinvlad.teamof8testingapp.MainScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.starostinvlad.teamof8testingapp.Counter;
import com.starostinvlad.teamof8testingapp.ProgressButton;
import com.starostinvlad.teamof8testingapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class MainFragment extends Fragment implements MainFragmentContract {

    ProgressButton loadBtn;
    private MainFragmentPresenter presenter;
    private Button button, examBtn;
    private ProgressBar progressBar;
    private int progress;
    private TextView textProgress, progressPerExam, readyForExamInPercent;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        presenter = new MainFragmentPresenter(this);
        button = view.findViewById(R.id.theme_btn);
        examBtn = view.findViewById(R.id.exam_btn);
        progressBar = view.findViewById(R.id.progress_user);
        textProgress = view.findViewById(R.id.text_progress);
        progressPerExam = view.findViewById(R.id.progressPerExam);
        readyForExamInPercent = view.findViewById(R.id.readyForExamInPercent);
        loadBtn = view.findViewById(R.id.load_btn);


        presenter.loadStatistic();

        loadBtn.setOnButtonClickListener(v -> presenter.loadAll());

        examBtn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_testFragment)
        );

        button.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_themesFragment)
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
    public void showProgress(Counter progress) {

        textProgress.setText(progress.toString());
        progressPerExam.setText(
                getResources().getString(R.string.progressPerExam,
                        progress.getAnswersPerExam())
        );

        if (progress.getAnswersPerExam() > 0)
            readyForExamInPercent.setText(
                    getResources().getString(
                            R.string.readyForExamInpercent,
                            ((float) progress.getCorrectCount() / (float) progress.getTotalCount() * (float) progress.getAnswersPerExam() / 14 * 100)
                    )
            );

        progressBar.setProgress((int) progress.getCorrectCount());
        if (progress.getCorrectCount() < 300)
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.red_progress));
        else if (progress.getCorrectCount() < 500)
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.orange_progress));
        else
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
    }

    @Override
    public void showLoading(boolean show) {
        loadBtn.showLoading(show);
    }
}
