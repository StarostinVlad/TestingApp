package com.starostinvlad.professional_1c.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starostinvlad.professional_1c.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class ProgressButton extends LinearLayout implements View.OnClickListener {

    CardView cardView;
    ProgressBar progressBar;
    TextView textView;
    private OnClickListener onClickListener;
    private String defualtText;

    public ProgressButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ProgressButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);
        if (arr.hasValueOrEmpty(R.styleable.ProgressButton_cardBackground))
            cardView.setBackground(arr.getDrawable(R.styleable.ProgressButton_cardBackground));
        defualtText = String.valueOf(arr.getText(R.styleable.ProgressButton_btnText));
        if (!defualtText.isEmpty())
            textView.setText(defualtText);
    }

    private void init(Context context) {
        inflate(context, R.layout.progress_button, this);
        cardView = findViewById(R.id.progress_card);
        cardView.setOnClickListener(this);
        progressBar = findViewById(R.id.progress_custom);
        progressBar.setVisibility(GONE);
        textView = findViewById(R.id.progress_text);
    }

    public void showLoading(boolean show) {
        progressBar.setVisibility(show ? VISIBLE : GONE);
        textView.setText(show ? "Загрузка..." : defualtText);
    }

    @Override
    public void onClick(View view) {
        onClickListener.onButtonClick(view.getId());
    }

    public void setOnButtonClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onButtonClick(int tabId);
    }
}
