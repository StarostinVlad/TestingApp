package com.starostinvlad.professional_1c.MainScreen;


import com.starostinvlad.professional_1c.Models.UserProgress;

public interface MainFragmentContract {
    void showResult(int count);

    void showProgress(UserProgress progress);

    void showLoading(boolean show);
}
