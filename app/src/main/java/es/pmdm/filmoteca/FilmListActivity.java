package es.pmdm.filmoteca;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FilmListActivity extends AppCompatActivity {
    private FilmAdapter miAdaptador;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        if (!FilmDataSource.iniciado) {
            FilmDataSource.Initialize();
        }
        list = findViewById(R.id.listaPelis);
        miAdaptador = new FilmAdapter(this, R.layout.item_film, FilmDataSource.films);
        list.setAdapter(miAdaptador);

        list.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(FilmListActivity.this, FilmDataActivity.class);
            intent.putExtra("FILM_POSITION", position);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId(); // Obtener el ID del elemento seleccionado

        if (itemId == R.id.menu_acercaDe) {
            // Abrir la actividad AboutActivity
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;

        } else if (itemId == R.id.menu_anyadirPeli) {
            // Añadir una nueva película con datos predeterminados
            Film newFilm = new Film(
                    R.drawable.cinema,
                    "Nueva Película",
                    2024,
                    "Director Desconocido",
                    Film.GENRE_ACTION,
                    Film.FORMAT_DVD,
                    "https://www.imdb.com",
                    "Descripción de la nueva película."
            );
            FilmDataSource.films.add(0, newFilm);
            miAdaptador.notifyDataSetChanged(); // Notificar al adaptador para actualizar la lista
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}