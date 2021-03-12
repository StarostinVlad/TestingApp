package com.starostinvlad.teamof8testingapp.ThemesScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.starostinvlad.teamof8testingapp.Adapters.TestListRvAdapter;
import com.starostinvlad.teamof8testingapp.Models.Theme;
import com.starostinvlad.teamof8testingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        progressBar = view.findViewById(R.id.pb_themes_loading);
        testRecycleView = view.findViewById(R.id.rv_test_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testRecycleView.setLayoutManager(layoutManager);

        adapter = new TestListRvAdapter();

        adapter.setOnItemClickListener(Theme -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("theme", Theme);
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