package mastersidi.fste.umi.ac.moroccotours;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.NoteViewHolder> {

    private Cursor cursor;

    public PlansAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    public void setCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            int columnIndexTitle = cursor.getColumnIndex(MoroccoToursBD.COL_TITLE);
            int columnIndexDescription = cursor.getColumnIndex(MoroccoToursBD.COL_DESCRIPTION);

            Log.d("ActivityComment", "Column Index for Title: " + columnIndexTitle);
            Log.d("ActivityComment", "Column Index for Description: " + columnIndexDescription);

            String title = cursor.getString(columnIndexTitle);
            String description = cursor.getString(columnIndexDescription);
            holder.noteTitleTextView.setText(title);
            holder.noteDescTextView.setText(description);
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitleTextView;
        TextView noteDescTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.noteTitle);
            noteDescTextView = itemView.findViewById(R.id.noteDesc);
        }
    }
}
