package com.starostinvlad.professional_1c.ThemesScreen;

import com.starostinvlad.professional_1c.Models.Theme;

import java.util.List;

public interface ThemesFragmentContract {
    void fillList(List<Theme> themes);

    void showLoading(boolean show);
}
