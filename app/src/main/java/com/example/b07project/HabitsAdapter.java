package com.example.b07project;

import static android.view.View.GONE;
import android.annotation.SuppressLint;
import android.content.Context;
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

/**
 * HabitsAdapter class is responsible for the creation and behaviour of the adapter for HabitsModel.
 *
 * @see HabitsModel
 * @see HabitsMenu
 * @see HabitsDetailsDialogFragment
 * @see UserHabitsProgressDialogFragment
 */
public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Goal> habitsModels;

    /**
     * Constructor for HabitsAdapter.
     *
     * @param context the context to be set as the adapter's field.
     * @param habitsModels the ArrayList of HabitsModels to be set as the adapter's field.
     */
    public HabitsAdapter(Context context, ArrayList<Goal> habitsModels) {
        this.context = context;
        this.habitsModels = habitsModels;
    }

    /**
     * Method to set the field habitsModels and notify the adapter of the dataset changes.
     *
     * @param habitsModels the ArrayList of HabitsModels to be set as the adapter's field
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setHabitsModels(ArrayList<Goal> habitsModels) {
        this.habitsModels = habitsModels;
        notifyDataSetChanged();
    }

    /**
     * Called when a new ViewHolder is needed to represent an item in the RecyclerView.
     * This method inflates the item layout and creates a new instance of `MyViewHolder`
     * to hold the item view.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new instance of `MyViewHolder` which holds the view for an individual habit item.
     */
    @NonNull
    @Override
    public HabitsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.habits_item_row, parent, false);
        return new HabitsAdapter.MyViewHolder(view);
    }

    /**
     * Binds the data of a habit item to the views in the `ViewHolder` at the specified position
     * in the data set. This method updates the contents of the `ViewHolder` based on the habit's
     * properties and handles user interactions such as showing or hiding the cancel button and
     * displaying a confirmation dialog for habit removal.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HabitsAdapter.MyViewHolder holder, int position) {
        // set the components for the current habit
        Goal habit = habitsModels.get(position);
        holder.habitName.setText(habit.getName());
        holder.habitImage.setImageResource(habit.getImage());
        holder.habitImpact.setText(habit.getImpact());

        // define default look for certain components
        int white = ContextCompat.getColor(context, R.color.white);
        holder.habitCard.setCardBackgroundColor(white);
        holder.cancelButton.setVisibility(GONE);

        // define behaviour/look of components other than the cancel button (if visible)
        if (!HabitsMenu.currentMenu[0]) {
            // if on All Habits menu, correctly display adopted habits, if any
            if (HabitsMenu.userGoals.contains(habit)) {
                int planetzeColour2 = ContextCompat.getColor(context, R.color.planetze_colour_2);
                holder.habitCard.setCardBackgroundColor(planetzeColour2);
            }
        } else {
            // if on User's Habits menu, allow option to delete an adopted habit
            holder.cancelButton.setVisibility(View.VISIBLE);

            // if cancel habit is clicked, launch a confirmation dialog
            holder.cancelButton.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove this habit? All progress will be lost.")
                        .setTitle("Confirm")
                        .setPositiveButton("No", (dialog, id) -> {
                            // no action is carried out
                        })
                        .setNegativeButton("Yes", (dialog, id) -> {
                            // Handle the "Yes" action
                            int pos = habitsModels.indexOf(habit);
                            HabitsMenu.userGoals.remove(habit);
                            // Notify the adapter to remove the item at the position
                            notifyItemRemoved(pos);
                            Toast.makeText(context, "Habit Removed", Toast.LENGTH_SHORT).show();
                        });
                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }

        // define behaviour if the habit item is clicked
        holder.itemView.setOnClickListener(v -> {
            if (!HabitsMenu.currentMenu[0]) {
                // if on All Habits menu, launch a dialog to elaborate on the habit's details
                HabitsDetailsDialogFragment habitDialog = HabitsDetailsDialogFragment.newInstance(
                        habit.getHabitDesc(),
                        habit.getImpactDesc(),
                        habit.getImage()
                );
                habitDialog.show(((AppCompatActivity) context).getSupportFragmentManager(),
                        "habit_details_dialog");
            } else {
                // if on User's Habits menu, launch a dialog to display and log the habit's activities
                UserHabitsProgressDialogFragment habitDialog = UserHabitsProgressDialogFragment.newInstance(habit.getName());
                habitDialog.show(((AppCompatActivity) context).getSupportFragmentManager(),
                        "user_progress" );
            }
        });

    }

    /**
     * Method to determine the number of items to be displayed for the RecyclerView.
     * The size of the list of all HabitsModels is chosen.
     *
     * @return number of items to be displayed for the RecyclerView.
     */
    @Override
    public int getItemCount() {
        return habitsModels.size();
    }

    /**
     * ViewHolder class for binding habit data to the corresponding views within a RecyclerView.
     * This class holds references to the views for each habit item in the list. The views can be
     * accessed and updated when the RecyclerView is scrolled or data is bound to the item.
     *
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing views from rows
        ImageView habitImage;
        TextView habitName;
        TextView habitImpact;
        ImageView cancelButton;
        CardView habitCard;

        /**
         * Constructor for initializing the ViewHolder by binding the views in the provided itemView.
         * This constructor finds the views within the item layout. These fields are later used
         * to bind data to the views when the RecyclerView is scrolled and items are recycled.
         *
         * @param itemView The view representing an individual item in the RecyclerView. This view
         *                 is passed to the ViewHolder to extract and bind the individual views
         */
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
