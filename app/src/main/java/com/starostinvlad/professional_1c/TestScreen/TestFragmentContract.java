package com.starostinvlad.professional_1c.TestScreen;

import com.starostinvlad.professional_1c.Models.QuestionWithAnswers;

import java.util.List;

public interface TestFragmentContract {
    void fillRecycleView(List<QuestionWithAnswers> questions);

    void setActiveQuestion(QuestionWithAnswers question);

    void showLoading(boolean show);

    void disableCheckBtn();

    void showResultDialog(String message);

    void scrollToNextQuestion(int currentQuestionIndex);
}
