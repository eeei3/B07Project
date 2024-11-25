package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.MyViewHolder> {
    Context context;
    ArrayList<HabitsModel> habitsModels;


    public HabitsAdapter(Context context, ArrayList<HabitsModel> habitsModels) {
        this.context = context;
        this.habitsModels = habitsModels;
    }

    public void setHabitsModels(ArrayList<HabitsModel> habitsModels) {
        this.habitsModels = habitsModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HabitsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.habits_item_row, parent, false);
        return new HabitsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitsAdapter.MyViewHolder holder, int position) {
        HabitsModel habit = habitsModels.get(position);
        holder.habitName.setText(habit.getHabitName());
        holder.habitImage.setImageResource(habit.getImage());
        holder.habitImpact.setText(habit.getImpact());

        holder.itemView.setOnClickListener(v -> {
            HabitsDetailsDialogFragment habitDialog = HabitsDetailsDialogFragment.newInstance(
                    habit.getHabitDesc(),
                    habit.getImpactDesc(),
                    habit.getImage()
            );
            habitDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "habit_details_dialog");
        });
    }

    @Override
    public int getItemCount() {
        return habitsModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing views from rows
        ImageView habitImage;
        TextView habitName;
        TextView habitImpact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            habitName = itemView.findViewById(R.id.habit_name);
            habitImage = itemView.findViewById(R.id.habit_img);
            habitImpact = itemView.findViewById(R.id.habit_impact);

        }
    }

}
