package com.starostinvlad.teamof8testingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starostinvlad.teamof8testingapp.Models.Theme;
import com.starostinvlad.teamof8testingapp.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TestListRvAdapter extends RecyclerView.Adapter<TestListRvAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<Theme> themes;
    private OnItemClickListener onItemClickListener;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.test_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    public void setDataList(List<Theme> themes) {
        this.themes = themes;
        notifyDataSetChanged();
    }

    // сеттер для колбека
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(themes.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        if (themes == null) {
            return -1;
        }
        return themes.size();
    }

    // интерфейс колбек
    public interface OnItemClickListener {
        void onItemClick(Theme Theme);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = getClass().getSimpleName();

        private final TextView titleView;

        ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.text_test_title);
            view.setOnClickListener(v -> {
                onItemClickListener.onItemClick(themes.get(getAdapterPosition()));
            });
        }
    }
}