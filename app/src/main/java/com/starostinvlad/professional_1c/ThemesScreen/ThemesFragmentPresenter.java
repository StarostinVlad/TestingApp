package com.starostinvlad.professional_1c.ThemesScreen;

import com.starostinvlad.professional_1c.App;
import com.starostinvlad.professional_1c.Database.ThemeDao;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


class ThemesFragmentPresenter {
    private final String TAG = getClass().getSimpleName();
    private final ThemesFragmentContract view;
    private Disposable disposable;
    private ThemeDao themeDao;

    ThemesFragmentPresenter(ThemesFragmentContract view) {
        this.view = view;
    }

    void loadData() {
        themeDao = App.getDatabase().themeDao();
        view.showLoading(true);

        disposable = themeDao.getThemes()
                .toObservable()
                .flatMapIterable(val -> val)
                .flatMap(val -> App.getDatabase().questionDao().getCorrectAndTotalQuestionsCountByThemeId(val.getId()).toObservable(),
                        (theme, counter) -> {
                            theme.setUserProgress(counter);
                            return theme;
                        })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(val -> {
                            view.fillList(val);
                            view.showLoading(false);
                        }
                        ,
                        throwable -> {
                            view.showLoading(false);
                            throwable.printStackTrace();
                        }
                );
    }

    void onDetach() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
