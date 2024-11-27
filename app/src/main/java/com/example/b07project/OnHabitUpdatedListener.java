package com.example.b07project;

/**
 * Interface for listening to updates on habit data. This interface is to be implemented by
 * activities or fragments that need to respond to changes in habit data.
 */
public interface OnHabitUpdatedListener {
    /**
     * Called when a habit has been updated.
     *
     * @param habit The updated habit object containing the latest data.
     *              This object represents the habit that was modified,
     *              allowing the listener to take appropriate action.
     */
    void onHabitUpdated(HabitsModel habit);
}
