package mastersidi.fste.umi.ac.moroccotours;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityAddPlans extends AppCompatActivity {

    private MoroccoToursBD MoroccoToursBD;
    private ImageView back_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplans);
        back_arrow= findViewById(R.id.back_arrow);

        MoroccoToursBD = new MoroccoToursBD(this);

        Button showButton = findViewById(R.id.showButton);
        FloatingActionButton saveButton = findViewById(R.id.saveButton);

        showButton.setOnClickListener(view -> {
            // Récupérer les données de la première plan dans la base de données
            MoroccoToursBD.OuvrirBD();
            Cursor cursor = MoroccoToursBD.showPlans();
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndexTitle = cursor.getColumnIndex(MoroccoToursBD.COL_TITLE);
                int columnIndexDescription = cursor.getColumnIndex(MoroccoToursBD.COL_DESCRIPTION);

                Log.d("ActivityComment", "Column Index for Title: " + columnIndexTitle);
                Log.d("ActivityComment", "Column Index for Description: " + columnIndexDescription);

                String title = cursor.getString(columnIndexTitle);
                String description = cursor.getString(columnIndexDescription);
                // Transmettre les données à l'activité plans_layout
                Intent intent = new Intent(ActivityAddPlans.this, ShowPlans_layout.class);
                intent.putExtra("note_title", title);
                intent.putExtra("note_description", description);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No plans available", Toast.LENGTH_SHORT).show();
            }

            if (cursor != null) {
                cursor.close();
            }
            MoroccoToursBD.FermerBD();
        });

        saveButton.setOnClickListener(view -> saveNote());
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveNote() {
        EditText addNoteTitle = findViewById(R.id.addNoteTitle);
        EditText addNoteDesc = findViewById(R.id.addNoteDesc);

        String title = addNoteTitle.getText().toString();
        String description = addNoteDesc.getText().toString();

        if (!title.isEmpty() && !description.isEmpty()) {
            try {
                MoroccoToursBD.OuvrirBD();
                long result =MoroccoToursBD.insertPlans(title, description);
                if (result != -1) {
                    Toast.makeText(this, "Plan saved successfully", Toast.LENGTH_SHORT).show();
                    addNoteTitle.setText("");
                    addNoteDesc.setText("");
                } else {
                    Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();
                }
            } finally {
                MoroccoToursBD.FermerBD();
            }
        } else {
            Toast.makeText(this, "Please enter both title and description", Toast.LENGTH_SHORT).show();
        }
    }

}