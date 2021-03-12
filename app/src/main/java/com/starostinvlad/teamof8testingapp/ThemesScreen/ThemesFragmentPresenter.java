package com.starostinvlad.teamof8testingapp.ThemesScreen;

import com.starostinvlad.teamof8testingapp.App;
import com.starostinvlad.teamof8testingapp.ThemeDao;

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

        disposable =
//                RestClient.instance()
//                        .getThemes()
//                        .retryWhen(f -> f.take(2).delay(1, TimeUnit.SECONDS))
//                        .switchIfEmpty(
                themeDao.getThemes()
//                        )
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
