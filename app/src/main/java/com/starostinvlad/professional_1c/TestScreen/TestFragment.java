package com.starostinvlad.professional_1c.TestScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.squareup.picasso.Picasso;
import com.starostinvlad.professional_1c.Adapters.AnswersRvAdapter;
import com.starostinvlad.professional_1c.Adapters.QuestionListRvAdapter;
import com.starostinvlad.professional_1c.Models.QuestionWithAnswers;
import com.starostinvlad.professional_1c.Models.Theme;
import com.starostinvlad.professional_1c.R;
import com.stfalcon.imageviewer.StfalconImageViewer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestFragment extends Fragment implements TestFragmentContract {
    private static final String EXTRA = "theme";
    private final String TAG = getClass().getSimpleName();
    private RecyclerView questionsRV, answersRV;
    private LinearLayoutManager gridLayoutManager;
    private TestFragmentPresenter testFragmentPresenter;
    private QuestionListRvAdapter questionListRvAdapter;
    private TextView txtQuestion;
    private ImageView imgQuestion;
    private AnswersRvAdapter answersRvAdapter;
    private Button btnCheck;
    private ProgressBar progressBar;
    private boolean exam;

    public static void start(Context context, Theme theme) {
        Intent intent = new Intent(context, TestFragment.class);
        intent.putExtra(EXTRA, theme);
        context.startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TestFragment.class);
        context.startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        testFragmentPresenter = new TestFragmentPresenter(this);

        Appodeal.setBannerViewId(R.id.appodealBannerView);
        if (getActivity() != null) {
            Appodeal.show(getActivity(), Appodeal.BANNER_VIEW);
            Appodeal.show(getActivity(), Appodeal.INTERSTITIAL);
        }

        questionsRV = view.findViewById(R.id.rv_questions);
        answersRV = view.findViewById(R.id.rv_answers);
        txtQuestion = view.findViewById(R.id.txt_question);
        imgQuestion = view.findViewById(R.id.img_question);
        btnCheck = view.findViewById(R.id.btn_check);
        progressBar = view.findViewById(R.id.pb_loading);

        if (getArguments().containsKey(EXTRA)) {
            Theme theme = (Theme) getArguments().getSerializable(EXTRA);
            Log.d(TAG, "onViewCreated: themeID:" + theme.getId());
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(theme.getTitle());
            testFragmentPresenter.loadTheme(theme);
            exam = false;
        } else if (getArguments().containsKey("ERRORS")) {
            boolean errors = getArguments().getBoolean("ERRORS");
            Log.d(TAG, "onViewCreated: themeID:" + errors);
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("????????????");
            testFragmentPresenter.loadErrors();
            exam = false;
        } else {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("??????????????");
            testFragmentPresenter.loadExam();
            exam = true;
        }

        testFragmentPresenter.isExam(exam);

        gridLayoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        questionsRV.setLayoutManager(gridLayoutManager);

        questionListRvAdapter = new QuestionListRvAdapter(exam);
        questionListRvAdapter.setOnItemClickListener(val -> testFragmentPresenter.setActiveQuestion(val));
        questionsRV.setAdapter(questionListRvAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        answersRV.setLayoutManager(linearLayoutManager);

        answersRvAdapter = new AnswersRvAdapter(exam);

        answersRV.setAdapter(answersRvAdapter);
        answersRV.setNestedScrollingEnabled(false);


        btnCheck.setOnClickListener(view1 -> {
            testFragmentPresenter.checkAnswer();
        });

    }


    @Override
    public void onDestroy() {
        testFragmentPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void fillRecycleView(List<QuestionWithAnswers> questions) {
        questionListRvAdapter.setDataList(questions);
    }

    @Override
    public void setActiveQuestion(QuestionWithAnswers question) {
        imgQuestion.setVisibility(View.GONE);
        txtQuestion.setVisibility(View.GONE);
        if (!question.getTitle().isEmpty()) {
            txtQuestion.setText(question.getTitle());
            txtQuestion.setVisibility(View.VISIBLE);
        }
        if (!question.getImage().isEmpty()) {
            Log.d(TAG, "setActiveQuestion: image: file:///android_asset" + question.getImage());
//            Picasso.get().load("http://10.81.16.60:5000" + question.getImage()).into(imgQuestion);
            String imagePath = "file:///android_asset" + question.getImage();
            Picasso.get().load(imagePath).into(imgQuestion);
            List<String> images = Collections.singletonList(imagePath);
            StfalconImageViewer.Builder<String> img = new StfalconImageViewer.Builder<>(getContext(), images, (imageView1, image) -> {
                Picasso
                        .get()
                        .load(image)
                        .placeholder(
                                getResources()
                                        .getDrawable(R.drawable.ic_launcher_foreground)
                        )
                        .into(imageView1);
            });
            imgQuestion.setOnClickListener(view -> {
                img.show(true);
            });
            imgQuestion.setVisibility(View.VISIBLE);
        }

        answersRvAdapter.setDataList(question);
        answersRvAdapter.setOnItemClickListener(index -> {
            testFragmentPresenter.setAnswerIndex(index);
            answersRvAdapter.selectAnswer(index);
            btnCheck.setEnabled(true);
        });

    }

    @Override
    public void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void disableCheckBtn() {
        btnCheck.setEnabled(false);
    }

    @Override
    public void showResultDialog(String message) {
        new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                .setNegativeButton("??????????????",
                        (dialogInterface, i) ->
                                Navigation.findNavController(Objects.requireNonNull(getView())).popBackStack())
                .show();

        btnCheck.setText("??????????????");
        btnCheck.setOnClickListener(view1 -> {
            Navigation.findNavController(Objects.requireNonNull(getView())).popBackStack();
        });
    }

    @Override
    public void scrollToNextQuestion(int currentQuestionIndex) {
        if (currentQuestionIndex < questionListRvAdapter.getScrollPosition())
            questionsRV.scrollToPosition(questionListRvAdapter.getScrollPosition());
        if (questionsRV.findViewHolderForAdapterPosition(questionListRvAdapter.getNextPosition()) != null)
            questionsRV.findViewHolderForAdapterPosition(questionListRvAdapter.getNextPosition()).itemView.performClick();
    }
}
