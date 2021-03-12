package com.starostinvlad.teamof8testingapp.TestScreen;

import com.starostinvlad.teamof8testingapp.Models.QuestionWithAnswers;

import java.util.List;

public interface TestFragmentContract {
    void fillRecycleView(List<QuestionWithAnswers> questions);

    void setActiveQuestion(QuestionWithAnswers question);

    void showLoading(boolean show);

    void disableCheckBtn();

    void showResultDialog(String message);

    void scrollToNextQuestion(int currentQuestionIndex);
}
