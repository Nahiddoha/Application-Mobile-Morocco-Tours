package mastersidi.fste.umi.ac.moroccotours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    DataClass androidData;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String username = getIntent().getStringExtra("USERNAME");

        // Find the TextView by ID
        TextView welcomeTextView = findViewById(R.id.textwelecom);

        // Set the welcome message dynamically
        String welcomeMessage = "Welcome To Morocco " + username;
        welcomeTextView.setText(welcomeMessage);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        FloatingActionButton addCommentButton = findViewById(R.id.addCommentButton);

        addCommentButton.setOnClickListener(view -> {
            // Start the ActivityComment when the button is clicked
            Intent intent = new Intent(MainActivity.this, ActivityAddPlans.class);
            startActivity(intent);
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

        androidData = new DataClass("Jemaa el fna, Marrakech", R.string.jemaa_el_fna, R.drawable.lfenna, "31.621996", "-7.986997");
        dataList.add(androidData);

        androidData = new DataClass("jardin_majorelle,marrakech", R.string.jardin_majorelle,  R.drawable.jardin_majorelle,  " 31.6341", "-7.9888");
        dataList.add(androidData);

        androidData = new DataClass(" hotel Bin lwidiane,bin lwidiane", R.string.hotel_bin_lwidiane, R.drawable.bin_lwidiane,  "32.2503", " -8.5923");
        dataList.add(androidData);

        androidData = new DataClass("Ourika,marrakech", R.string.ourika,  R.drawable.ourika, " 31.4262", "-7.8246");
        dataList.add(androidData);

        androidData = new DataClass("Merzouga,Rissani", R.string.merzouga,  R.drawable.merzouga, " 31.0994", "-4.0113");
        dataList.add(androidData);

        androidData = new DataClass("Meski,Errachidia", R.string.meski,  R.drawable.meski, "31.9055", "-4.4446");
        dataList.add(androidData);

        androidData = new DataClass("sidi bouzid,EL jadida", R.string.sidi_bouzid,  R.drawable.sidibouzid, "33.2184", "-8.5003");
        dataList.add(androidData);

        androidData = new DataClass("morocco mall,Casablanca", R.string.morocco_mall,  R.drawable.moroccomall, "33.5951", "-7.6330");
        dataList.add(androidData);


        adapter = new MyAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);
    }


    private void searchList(String text){
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList){
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (dataSearchList.isEmpty()){
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }

}