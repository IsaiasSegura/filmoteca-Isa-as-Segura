package es.pmdm.filmoteca;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        registerForContextMenu(list);
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
                    2025,
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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // Inflar el menú contextual
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position; // Obtener la posición del elemento seleccionado

        if (item.getItemId() == R.id.menu_delete) {
            // Eliminar la película seleccionada
            FilmDataSource.films.remove(position);
            miAdaptador.notifyDataSetChanged(); // Actualizar el adaptador
            showCustomToast("Película eliminada");
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        miAdaptador.notifyDataSetChanged();
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_personalizado, findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}