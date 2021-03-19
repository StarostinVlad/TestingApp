package com.starostinvlad.professional_1c.ThemesScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.starostinvlad.professional_1c.Adapters.TestListRvAdapter;
import com.starostinvlad.professional_1c.Models.Theme;
import com.starostinvlad.professional_1c.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ThemesFragment extends Fragment implements ThemesFragmentContract {

    private RecyclerView testRecycleView;
    private ThemesFragmentPresenter presenter;
    private TestListRvAdapter adapter;
    private ProgressBar progressBar;

    public static void start(Context context) {
        Intent intent = new Intent(context, ThemesFragment.class);
        context.startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_themes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Темы");

        progressBar = view.findViewById(R.id.pbThemesLoading);
        testRecycleView = view.findViewById(R.id.rvTestList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testRecycleView.setLayoutManager(layoutManager);

        adapter = new TestListRvAdapter();

        adapter.setOnItemClickListener(theme -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("theme", theme);
            Navigation.findNavController(view).navigate(R.id.action_themesFragment_to_testFragment, bundle);
//            TestFragment.start(getActivity(), Theme);
        });

        testRecycleView.setAdapter(adapter);

        presenter = new ThemesFragmentPresenter(this);
        presenter.loadData();
    }


    @Override
    public void fillList(List<Theme> themes) {
        adapter.setDataList(themes);
    }

    @Override
    public void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}

