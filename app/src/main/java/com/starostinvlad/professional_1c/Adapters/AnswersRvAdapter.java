package com.starostinvlad.professional_1c.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.starostinvlad.professional_1c.Models.Answer;
import com.starostinvlad.professional_1c.Models.QuestionWithAnswers;
import com.starostinvlad.professional_1c.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AnswersRvAdapter extends RecyclerView.Adapter<AnswersRvAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private int selectedPosition = -1;
    private List<Answer> answers;
    private OnItemClickListener onItemClickListener;
    private boolean exam, firstly;

    public AnswersRvAdapter(boolean exam) {
        this.exam = exam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.answer_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public void setDataList(QuestionWithAnswers question) {
        this.answers = question.getAnswers();
        if (question.getAnswerIndex() != null) {
            selectedPosition = question.getAnswerIndex();
            firstly = false;
        } else {
            selectedPosition = -1;
            firstly = true;
        }
        Log.d(TAG, "setDataList: firstly: " + firstly);
        selectAnswer(selectedPosition);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: pos: " + position);
        holder.cardView.setBackgroundResource(R.drawable.item_background);
        holder.titleView.setText(answers.get(position).getTitle());
        holder.cardView.setSelected(position == selectedPosition);
        if (!exam) {
            Log.d(TAG, "onBindViewHolder: is exam");
            if (selectedPosition > -1) {
                if (position == selectedPosition)
                    holder.cardView.setBackgroundResource(R.drawable.wrong_item_background);
                if (answers.get(position).isCorrect())
                    holder.cardView.setBackgroundResource(R.drawable.correct_item_background);
                firstly = false;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (answers == null) {
            return -1;
        }
        return answers.size();
    }

    public void setOnItemClickListener(AnswersRvAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void selectAnswer(Integer index) {
        notifyDataSetChanged();
        if (firstly)
            selectedPosition = index;
    }

    public interface OnItemClickListener {
        void onItemClick(Integer answerIndex);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = getClass().getSimpleName();

        private TextView titleView;
        private CardView cardView;

        ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.text_answer_title);
            cardView = view.findViewById(R.id.card_answer);
            cardView.setBackgroundResource(R.drawable.item_background);

            itemView.setOnClickListener(view1 -> onItemClickListener.onItemClick(getAdapterPosition()));

        }
    }
}