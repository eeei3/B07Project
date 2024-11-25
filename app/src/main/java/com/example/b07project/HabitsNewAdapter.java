package com.example.b07project;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
public class HabitsNewAdapter extends RecyclerView.Adapter<HabitsNewAdapter.MyViewHolder> {
    Context context;
    ArrayList<HabitsNewModel> habitsModels;


    public HabitsNewAdapter(Context context, ArrayList<HabitsNewModel> habitsModels) {
        this.context = context;
        this.habitsModels = habitsModels;
    }

    public void setHabitsModels(ArrayList<HabitsNewModel> habitsModels) {
        this.habitsModels = habitsModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HabitsNewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.habits_item, parent, false);
        return new HabitsNewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitsNewAdapter.MyViewHolder holder, int position) {
        holder.habitName.setText(habitsModels.get(position).getHabitName());
        holder.habitImage.setImageResource(habitsModels.get(position).getImage());
        holder.habitImpact.setText(habitsModels.get(position).getImpact());
    }

    @Override
    public int getItemCount() {
        return habitsModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing views from habits_recycler_view_row
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
