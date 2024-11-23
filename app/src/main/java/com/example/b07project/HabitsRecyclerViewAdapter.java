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

public class HabitsRecyclerViewAdapter extends RecyclerView.Adapter<HabitsRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<HabitsModel> habitsModels;

    public HabitsRecyclerViewAdapter(Context context, ArrayList<HabitsModel> habitsModels) {
        this.context = context;
        this.habitsModels = habitsModels;
    }

    @NonNull
    @Override
    public HabitsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // in charge of inflating layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.habits_recycler_view_row, parent, false);
        return new HabitsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitsRecyclerViewAdapter.MyViewHolder holder, int position) {
        // assign values to views created in habits_recycler_view_row xml file
        // dependent on the position of the RecyclerView
        holder.habitName.setText(habitsModels.get(position).getHabitName());
        holder.habitImage.setImageResource(habitsModels.get(position).getImage());
        holder.habitSwitch.setChecked(habitsModels.get(position).isSwitchChecked());

        if (habitsModels.get(position).isSwitchChecked()) {
            holder.habitCardView.setCardBackgroundColor(
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.planetze_colour_3)
            );
        }

        // Listener for the MaterialSwitch
        holder.habitSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update the item's checked state
            habitsModels.get(position).setSwitchChecked(isChecked);

            // Change tint based on switch state
            if (isChecked) {
                holder.habitCardView.setCardBackgroundColor(
                        ContextCompat.getColor(holder.itemView.getContext(), R.color.planetze_colour_3)
                );
            } else {
                holder.habitCardView.setCardBackgroundColor(
                        ContextCompat.getColor(holder.itemView.getContext(), R.color.planetze_colour_4)
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        // how many items to be displayed
        return habitsModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing views from habits_recycler_view_row
        ImageView habitImage;
        TextView habitName;
        SwitchMaterial habitSwitch;
        CardView habitCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            habitImage = itemView.findViewById(R.id.habitsImageView);
            habitName = itemView.findViewById(R.id.habitsTextView);
            habitSwitch = itemView.findViewById(R.id.habitsSwitch);
            habitCardView = itemView.findViewById(R.id.habitCardView);

        }
    }
}
