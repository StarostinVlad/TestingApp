package com.starostinvlad.teamof8testingapp.MainScreen;

import android.util.Log;

import com.starostinvlad.teamof8testingapp.App;
import com.starostinvlad.teamof8testingapp.Models.TestAnswers;
import com.starostinvlad.teamof8testingapp.QuestionDao;
import com.starostinvlad.teamof8testingapp.RestClient;
import com.starostinvlad.teamof8testingapp.TestAnswerDao;
import com.starostinvlad.teamof8testingapp.ThemeDao;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class MainFragmentPresenter {
    private final String TAG = getClass().getSimpleName();
    private final MainFragmentContract view;
    private Disposable disposable;
    private ThemeDao themeDao;

    MainFragmentPresenter(MainFragmentContract view) {
        this.view = view;
    }

    void onDetach() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    void loadAll() {
        view.showLoading(true);
        themeDao = App.getDatabase().themeDao();
        disposable = RestClient.instance()
                .loadAll()
                .doOnError(Throwable::printStackTrace)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        val -> {
                            Log.d(TAG, "loadAll: loaded");
                            view.showLoading(false);
                        },
                        Throwable::printStackTrace
                );
    }

    void loadStatistic() {
        QuestionDao questionsDao = App.getDatabase().questionDao();
        TestAnswerDao testAnswerDao = App.getDatabase().testAnswerDao();
//        Single.create(emitter -> {
//            try {
//                questionsDao.deleteAll();
//            } catch (Exception e) {
//                emitter.onError(e);
//            }
//        }).subscribeOn(Schedulers.io()).subscribe();
        Single.zip(
                questionsDao.getCorrectAndTotalQuestionsCount(),
                testAnswerDao.getAnswers(),
                ((counter, answers) -> {
                    Log.d(TAG, "loadStatistic: answers " + answers.size());
                    if (!answers.isEmpty())
                        counter.setAnswersPerExam(
                                (
                                        answers
                                                .stream()
                                                .map(TestAnswers::getCountCorrectAnswers)
                                                .mapToInt(Integer::intValue)
                                                .sum() / answers.size()
                                )
                        );
                    return counter;
                })
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::showProgress,
                        Throwable::printStackTrace
                )
                .isDisposed();
    }
}
