package com.starostinvlad.teamof8testingapp.ThemesScreen;

import com.starostinvlad.teamof8testingapp.Models.Theme;

import java.util.List;

public interface ThemesFragmentContract {
    void fillList(List<Theme> themes);

    void showLoading(boolean show);
}
