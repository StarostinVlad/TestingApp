package com.starostinvlad.teamof8testingapp.MainScreen;


import com.starostinvlad.teamof8testingapp.Counter;

public interface MainFragmentContract {
    void showResult(int count);

    void showProgress(Counter progress);

    void showLoading(boolean show);
}
