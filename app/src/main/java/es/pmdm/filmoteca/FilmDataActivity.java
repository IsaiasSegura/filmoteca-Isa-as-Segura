package es.pmdm.filmoteca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.pmdm.filmoteca.Film;
import es.pmdm.filmoteca.FilmDataSource;
import es.pmdm.filmoteca.R;

public class FilmDataActivity extends AppCompatActivity {

    private int filmPosition;  // Para almacenar la posición de la película seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_data);  // Nombre del layout

        // Obtener la posición de la película desde el Intent
        Intent intent = getIntent();
        filmPosition = intent.getIntExtra("FILM_POSITION", 0);  // Obtenemos la posición

        // Obtener la película desde FilmDataSource
        Film film = FilmDataSource.films.get(filmPosition);

        // Configurar la interfaz de usuario con los detalles de la película
        ImageView imageViewFilm = findViewById(R.id.imageData);
        TextView textTitle = findViewById(R.id.dataTitulo);
        TextView textDirector = findViewById(R.id.dataDirector);
        TextView textYear = findViewById(R.id.dataAnyo);
        TextView dataGeneroFormato =findViewById(R.id.dataGeneroFormato);
        TextView editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
        Button buttonImdb = findViewById(R.id.button4);
        Button buttonBack = findViewById(R.id.button5);
        Button buttonEdit = findViewById(R.id.button6);

        // Cargar la imagen de la película
        imageViewFilm.setImageResource(film.getImageResId());

        // Mostrar el título, director y año
        textTitle.setText(film.getTitle());
        textDirector.setText(film.getDirector());
        textYear.setText(String.valueOf(film.getYear()));
        dataGeneroFormato.setText(film.dataGeneroFormato());
        editTextTextMultiLine.setText(film.getComments());

        editTextTextMultiLine.setFocusable(false);
        editTextTextMultiLine.setFocusableInTouchMode(false);
        editTextTextMultiLine.setClickable(false);

        // Configurar el botón "Ver en IMDb"
        buttonImdb.setOnClickListener(v -> {
            Intent imdbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(film.getImdbUrl()));
            startActivity(imdbIntent);  // Abrimos IMDb
        });

        // Configurar el botón "Volver"
        buttonBack.setOnClickListener(v -> finish());  // Volver a la lista de películas

        // Configurar el botón "Editar Película"

        buttonEdit.setOnClickListener(v -> {
            Intent editIntent = new Intent(FilmDataActivity.this, FilmEditActivity.class);
            editIntent.putExtra("FILM_POSITION", filmPosition);  // Pasamos la posición para editar
            startActivity(editIntent);  // Abrir actividad para editar
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Film film = FilmDataSource.films.get(filmPosition);
        // Configurar la interfaz de usuario con los detalles de la película
        ImageView imageViewFilm = findViewById(R.id.imageData);
        TextView textTitle = findViewById(R.id.dataTitulo);
        TextView textDirector = findViewById(R.id.dataDirector);
        TextView textYear = findViewById(R.id.dataAnyo);
        TextView dataGeneroFormato =findViewById(R.id.dataGeneroFormato);
        TextView editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);

        // Cargar la imagen de la película
        imageViewFilm.setImageResource(film.getImageResId());

        // Mostrar el título, director y año
        textTitle.setText(film.getTitle());
        textDirector.setText(film.getDirector());
        textYear.setText(String.valueOf(film.getYear()));
        dataGeneroFormato.setText(film.dataGeneroFormato());
        editTextTextMultiLine.setText(film.getComments());
    }
}
