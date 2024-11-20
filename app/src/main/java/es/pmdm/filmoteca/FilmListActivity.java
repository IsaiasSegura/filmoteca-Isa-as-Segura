package es.pmdm.filmoteca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FilmListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        if (!FilmDataSource.iniciado) {
            FilmDataSource.Initialize();
        }
        ListView list = findViewById(R.id.listaPelis);
        FilmAdapter miAdaptador = new FilmAdapter(this, R.layout.item_film, FilmDataSource.films);
        list.setAdapter(miAdaptador);

        list.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(FilmListActivity.this, FilmDataActivity.class);
            intent.putExtra("FILM_POSITION", position);
            startActivity(intent);
        });

    }
}