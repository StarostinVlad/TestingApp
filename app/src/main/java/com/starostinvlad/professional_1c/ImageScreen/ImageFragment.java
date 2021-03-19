package com.starostinvlad.professional_1c.ImageScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.starostinvlad.professional_1c.R;
import com.stfalcon.imageviewer.StfalconImageViewer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

public class ImageFragment extends DialogFragment {

    ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageQuestion);


        List<String> images = new ArrayList<>();
        images.add(getArguments().getString("IMAGE"));
        new StfalconImageViewer.Builder<>(getContext(), images, (imageView1, image) -> {
            Picasso.get().load(image).placeholder(getResources().getDrawable(R.drawable.ic_launcher_foreground)).into(imageView1);
        }).show();

        SwipeDismissBehavior<View> swipeDismissBehavior = new SwipeDismissBehavior<>();
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);

        CoordinatorLayout.LayoutParams coordinatorParams =
                (CoordinatorLayout.LayoutParams) imageView.getLayoutParams();

        coordinatorParams.setBehavior(swipeDismissBehavior);

        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                Snackbar.make(view, "DISMISS", Snackbar.LENGTH_INDEFINITE).show();
                Navigation.findNavController(view).popBackStack();
            }

            @Override
            public void onDragStateChanged(int state) {
            }
        });

    }
}
