package es.pmdm.filmoteca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FilmEditActivity extends AppCompatActivity {

    private int filmPosition;
    private Film film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_edit);

        // Obtener la posición de la película desde el Intent
        Intent intent = getIntent();
        filmPosition = intent.getIntExtra("FILM_POSITION", 0);

        // Obtener la película desde FilmDataSource
        film = FilmDataSource.films.get(filmPosition);

        // Referencias a los componentes
        ImageView imageViewFilm = findViewById(R.id.imageView4);
        EditText editTextTitle = findViewById(R.id.editTitulo);
        EditText editTextDirector = findViewById(R.id.editAutor);
        EditText editTextYear = findViewById(R.id.editAnyo);
        EditText editEnlace = findViewById(R.id.editEnlace);
        Spinner spinnerGenre = findViewById(R.id.spinnerGenero);
        Spinner spinnerFormat = findViewById(R.id.spinnerFormato);
        EditText editTextDescription = findViewById(R.id.editTextTextMultiLinedescrpcion);
        Button buttonCaptura = findViewById(R.id.buttonCaptutaImg);
        Button buttonSeleccionarImg = findViewById(R.id.buttonSeleccionarImg);
        Button buttonSave = findViewById(R.id.guardar);
        Button buttonCancel = findViewById(R.id.Cancelar);

        // Cargar los datos actuales de la película en los campos
        imageViewFilm.setImageResource(film.getImageResId());
        editTextTitle.setText(film.getTitle());
        editTextDirector.setText(film.getDirector());
        editTextYear.setText(String.valueOf(film.getYear()));
        editTextDescription.setText(film.getComments());
        editEnlace.setText(film.getImdbUrl());

        // Configurar los Spinners (puedes usar adaptadores para los géneros y formatos)
        spinnerGenre.setSelection(film.getGenre());
        spinnerFormat.setSelection(film.getFormat());

        //Configurar los botones seleccionar y captura con el Toast “Funcionalidad no implementada”
        buttonCaptura.setOnClickListener(view -> {
            Toast.makeText(this, "Funcionalidad no implementada.", Toast.LENGTH_SHORT).show();
        });
        buttonSeleccionarImg.setOnClickListener(view -> {
            Toast.makeText(this, "Funcionalidad no implementada", Toast.LENGTH_SHORT).show();
        });

        // Configurar el botón "Guardar"
        buttonSave.setOnClickListener(v -> {
            // Guardar los cambios en el objeto Film
            film.setTitle(editTextTitle.getText().toString());
            film.setDirector(editTextDirector.getText().toString());
            film.setYear(Integer.parseInt(editTextYear.getText().toString()));
            film.setGenre(spinnerGenre.getSelectedItemPosition());
            film.setFormat(spinnerFormat.getSelectedItemPosition());
            film.setComments(editTextDescription.getText().toString());

            // Mostrar mensaje de confirmación
            Toast.makeText(this, "Cambios aplicados correctamente.", Toast.LENGTH_SHORT).show();

            // Regresar a la actividad anterior
            finish();
        });

        // Configurar el botón "Cancelar"
        buttonCancel.setOnClickListener(v -> {
            // Mostrar mensaje de cancelación
            Toast.makeText(this, "Los cambios han sido cancelados.", Toast.LENGTH_SHORT).show();

            // Regresar sin guardar cambios
            finish();
        });
    }

}