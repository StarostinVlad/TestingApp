package com.starostinvlad.teamof8testingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.behavior.SwipeDismissBehavior;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

public class SwipableFragmentDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_fragment, null, false);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view).create();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                view.getLayoutParams();
        SwipeDismissBehavior<View> behavior = new SwipeDismissBehavior<>();
        behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);

        behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {

            @Override
            public void onDismiss(final View view) {
                dialog.dismiss();
            }

            @Override
            public void onDragStateChanged(int i) {}

        });

        params.setBehavior(behavior);
        return dialog;
    }
}
