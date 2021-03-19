package com.starostinvlad.professional_1c.TestScreen;

import android.util.Log;

import com.starostinvlad.professional_1c.App;
import com.starostinvlad.professional_1c.Models.Question;
import com.starostinvlad.professional_1c.Models.QuestionWithAnswers;
import com.starostinvlad.professional_1c.Models.TestAnswers;
import com.starostinvlad.professional_1c.Models.Theme;
import com.starostinvlad.professional_1c.Database.QuestionDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class TestFragmentPresenter {
    private final TestFragmentContract view;
    private QuestionDao questionDao;
    private String TAG = getClass().getSimpleName();
    private Disposable disposable;
    private List<QuestionWithAnswers> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private boolean exam;

    TestFragmentPresenter(TestFragmentContract view) {
        this.view = view;
        questionDao = App.getDatabase().questionDao();
    }

    void loadTheme(Theme theme) {
        view.showLoading(true);
        disposable =
                questionDao
                        .getQuestionsByThemeId(theme.getId())
                        .doOnError(val -> {
                            Log.d(TAG, "loadTheme: finish with except!");
                            val.printStackTrace();
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(val -> {

                            questions.addAll(val);

                            Log.d(TAG, "loadTheme: FINISH!");
                            view.showLoading(false);
                            view.fillRecycleView(val);
                            if (!val.isEmpty())
                                view.setActiveQuestion(val.get(0));
                        }, Throwable::printStackTrace);
    }

    void onDetach() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    void loadExam() {
        view.showLoading(false);
        disposable =
                questionDao
                        .getRandomQuestions()
                        .doOnError(val -> {
                            Log.d(TAG, "loadTheme: finish with except!");
                            val.printStackTrace();
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(val -> {

                            questions.addAll(val);

                            view.showLoading(false);
                            Log.d(TAG, "loadTheme: FINISH!");

                            view.fillRecycleView(questions);
                            if (!questions.isEmpty())
                                view.setActiveQuestion(val.get(0));

                        }, Throwable::printStackTrace);
    }

    void setAnswerIndex(Integer index) {
        questions.get(currentQuestionIndex).setAnswerIndex(index);
        QuestionWithAnswers questionWithAnswers = questions.get(currentQuestionIndex);
        Question question = new Question();
        question.setId(questionWithAnswers.getId());
        question.setThemeId(questionWithAnswers.getThemeId());
        question.setTitle(questionWithAnswers.getTitle());
        question.setImage(questionWithAnswers.getImage());
        question.setCorrectAnswer(questionWithAnswers.getCorrectAnswer());
        question.setLastAnswerIndex(index);

        Single.create(emitter -> {
            try {
                questionDao.insert(question);
            } catch (Exception e) {
                emitter.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();

    }

    void setActiveQuestion(int position) {
        currentQuestionIndex = position;
        view.setActiveQuestion(questions.get(currentQuestionIndex));
    }

    void checkAnswer() {
        view.scrollToNextQuestion(currentQuestionIndex);
        if (questions.stream().filter(question -> question.getAnswerIndex() != null).count() == questions.size()) {
            long correctAnswersCount = questions.stream().filter(question -> question.getAnswerIndex().equals(question.getCorrectAnswer())).count();
            if (exam)
                Single.create(emitter -> {
                    try {
                        App.getDatabase().testAnswerDao().insert(new TestAnswers((int) correctAnswersCount));
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }).subscribeOn(Schedulers.io()).subscribe();
            view.showResultDialog("Вы верно ответили на " + correctAnswersCount + " вопросов из " + questions.size());
        } else
            view.disableCheckBtn();
    }

    void isExam(boolean exam) {
        this.exam = exam;
    }

    void loadErrors() {
        view.showLoading(false);
        disposable =
                questionDao
                        .getWrongQuestions()
                        .doOnError(val -> {
                            Log.d(TAG, "loadTheme: finish with except!");
                            val.printStackTrace();
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(val -> {


                            questions.addAll(val);

                            view.showLoading(false);
                            Log.d(TAG, "loadTheme: FINISH!");

                            if (val.isEmpty())
                                view.showResultDialog("У вас нет не правильных ответов");
                            else {
                                view.fillRecycleView(questions);
                                if (!questions.isEmpty())
                                    view.setActiveQuestion(val.get(0));
                            }

                        }, Throwable::printStackTrace);
    }
}
