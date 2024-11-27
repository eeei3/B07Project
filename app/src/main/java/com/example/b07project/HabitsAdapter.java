package com.example.b07project;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.MyViewHolder> {
    Context context;
    ArrayList<HabitsModel> habitsModels;
    private OnHabitUpdatedListener listener;


    public HabitsAdapter(Context context, ArrayList<HabitsModel> habitsModels) {
        this.context = context;
        this.habitsModels = habitsModels;
    }

    @SuppressLint("NotifyDataSetChanged")
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
        int white = ContextCompat.getColor(context, R.color.white);
        holder.habitCard.setCardBackgroundColor(white);
        holder.cancelButton.setVisibility(GONE); //make sure cancel btn is only visible in users page

        if (!AllHabitsMenu.currentMenu[0]) {
            if (AllHabitsMenu.userHabitsModels.contains(habit)) {
                int planetzeColour2 = ContextCompat.getColor(context, R.color.planetze_colour_2);
                holder.habitCard.setCardBackgroundColor(planetzeColour2);
            }
        } else {
            holder.cancelButton.setVisibility(View.VISIBLE);

            holder.cancelButton.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove this habit? All progress will be lost.")
                        .setTitle("Confirm")
                        .setPositiveButton("No", (dialog, id) -> {
                            // Handle the "No" action
                            Toast.makeText(context, "Habit Removed", Toast.LENGTH_SHORT).show();
                            int pos = habitsModels.indexOf(habit); // Get the position of the habit in the list
                            AllHabitsMenu.userHabitsModels.remove(habit);


                            notifyItemRemoved(pos);  // Notify the adapter to remove the item at the position
                        })
                        .setNegativeButton("Yes", (dialog, id) -> {
                            // Handle the "Yes" action
                            Toast.makeText(context, "Habit Removed", Toast.LENGTH_SHORT).show();
                            int pos = habitsModels.indexOf(habit); // Get the position of the habit in the list
                            AllHabitsMenu.userHabitsModels.remove(habit);

                            notifyItemRemoved(pos);  // Notify the adapter to remove the item at the position
                        });
                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }

        holder.itemView.setOnClickListener(v -> {
            if (!AllHabitsMenu.currentMenu[0]) {
                HabitsDetailsDialogFragment habitDialog = HabitsDetailsDialogFragment.newInstance(
                        habit.getHabitDesc(),
                        habit.getImpactDesc(),
                        habit.getImage()
                );
                habitDialog.show(((AppCompatActivity) context).getSupportFragmentManager(),
                        "habit_details_dialog");
            } else {
                UserHabitsProgressDialogFragment habitDialog = new UserHabitsProgressDialogFragment();
                habitDialog.show(((AppCompatActivity) context).getSupportFragmentManager(),
                        "user_progress" );
            }
        });

        // Updating background after interaction
        if (listener != null) {
            listener.onHabitUpdated(habit);
        }
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
        ImageView cancelButton;
        CardView habitCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            habitName = itemView.findViewById(R.id.habit_name);
            habitImage = itemView.findViewById(R.id.habit_img);
            habitImpact = itemView.findViewById(R.id.habit_impact);
            habitCard = itemView.findViewById(R.id.habitCardView);
            cancelButton = itemView.findViewById(R.id.cancelImageView);
        }
    }

}
