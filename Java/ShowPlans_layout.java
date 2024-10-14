package mastersidi.fste.umi.ac.moroccotours;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPlans_layout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlansAdapter noteAdapter;
    private ImageView back_arrow,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showplans);

        recyclerView = findViewById(R.id.recyclerView);
        back_arrow= findViewById(R.id.back_arrow);
        delete= findViewById(R.id.delete);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new PlansAdapter(null);
        recyclerView.setAdapter(noteAdapter);

        Intent intent = getIntent();
        if (intent != null) {

            String title = intent.getStringExtra("note_title");
            String description = intent.getStringExtra("note_description");

            TextView noteTitleTextView = findViewById(R.id.noteTitle);
            TextView noteDescTextView = findViewById(R.id.noteDesc);
            if (noteTitleTextView != null && noteDescTextView != null) {
                noteTitleTextView.setText(title);
                noteDescTextView.setText(description);
            } else {
                Toast.makeText(this, "TextViews not found", Toast.LENGTH_SHORT).show();
            }
        }

        loadAllNotes();
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoroccoToursBD database = new MoroccoToursBD(ShowPlans_layout.this);
                database.clearAllPlans();
                database.FermerBD();
                finish();
                Toast.makeText(ShowPlans_layout.this, "All plans have been deleted", Toast.LENGTH_SHORT).show();

            }
        });
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadAllNotes() {
        MoroccoToursBD noteBd = new MoroccoToursBD(this);
        noteBd.OuvrirBD();
        Cursor cursor = noteBd.showPlans();
        noteAdapter.setCursor(cursor);
    }
}
