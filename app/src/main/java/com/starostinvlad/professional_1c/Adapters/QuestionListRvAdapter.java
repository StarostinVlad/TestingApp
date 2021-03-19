package com.starostinvlad.professional_1c.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starostinvlad.professional_1c.Models.QuestionWithAnswers;
import com.starostinvlad.professional_1c.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionListRvAdapter extends RecyclerView.Adapter<QuestionListRvAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Boolean exam;
    private List<QuestionWithAnswers> questions;
    private OnItemClickListener onItemClickListener;
    private int selectPosition = 0;

    public QuestionListRvAdapter(Boolean exam) {
        this.exam = exam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    public void setDataList(List<QuestionWithAnswers> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(String.valueOf(i + 1));
        Log.d(TAG, "onBindViewHolder: change " + i);
        viewHolder.cardView.setBackgroundResource(R.drawable.default_item_background);
        if (questions.get(i).getAnswerIndex() != null)
            if (!exam) {
                if (questions.get(i).getAnswerIndex().equals(questions.get(i).getCorrectAnswer()))
                    viewHolder.cardView.setBackgroundResource(R.drawable.correct_item_background);
                else
                    viewHolder.cardView.setBackgroundResource(R.drawable.wrong_item_background);
            } else {
                viewHolder.cardView.setBackgroundResource(R.drawable.answered_item_background);
            }
        if (selectPosition == i)
            viewHolder.cardView.setBackgroundResource(R.drawable.current_item_background);
    }

    @Override
    public int getItemCount() {
        if (questions == null) {
            return -1;
        }
        return questions.size();
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    // сеттер для колбека
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Integer getScrollPosition() {
        if (getSelectPosition() + 3 < getItemCount())
            return getSelectPosition() + 3;
        else return getItemCount() - 1;
    }


    public Integer getNextPosition() {
        if (getSelectPosition() + 1 < getItemCount())
            return getSelectPosition() + 1;
        else return getItemCount() - 1;
    }

    // интерфейс колбек
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = getClass().getSimpleName();

        private final TextView titleView;
        private final CardView cardView;

        ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.text_question_title);
            cardView = view.findViewById(R.id.card_question);
            itemView.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                if (position != selectPosition) {
                    onItemClickListener.onItemClick(position);
                    notifyItemChanged(position);
                    notifyItemChanged(selectPosition);
                    selectPosition = position;
                }

            });
        }
    }
}