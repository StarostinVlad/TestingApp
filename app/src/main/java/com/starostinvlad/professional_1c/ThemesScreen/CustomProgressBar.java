package com.starostinvlad.professional_1c.ThemesScreen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.starostinvlad.professional_1c.R;

public class CustomProgressBar extends ProgressBar {

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public synchronized void setProgress(int progress) {
//        Random random = new Random();
//        progress = random.nextInt(getMax());
        super.setProgress(progress);
        if (progress <= getMax() * 0.3) {
            setProgressDrawable(getResources().getDrawable(R.drawable.red_progress, getResources().newTheme()));
        } else if (progress <= getMax() * 0.6) {
            setProgressDrawable(getResources().getDrawable(R.drawable.orange_progress, getResources().newTheme()));
        } else {
            setProgressDrawable(getResources().getDrawable(R.drawable.green_progress, getResources().newTheme()));
        }
    }
}
