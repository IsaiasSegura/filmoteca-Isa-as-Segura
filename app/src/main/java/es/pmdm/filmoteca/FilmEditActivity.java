package es.pmdm.filmoteca;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class FilmEditActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
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
        film = FilmDataSource.getFilm(filmPosition);

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

        // Configurar los Spinners
        spinnerGenre.setSelection(film.getGenre());
        spinnerFormat.setSelection(film.getFormat());

        // Configurar los botones seleccionar y captura con el Toast
        buttonCaptura.setOnClickListener(view -> {
            if (checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)) {
                openCamera();
            }
        });

        buttonSeleccionarImg.setOnClickListener(view ->
                showCustomToast("Funcionalidad no implementada"));

        // Configurar el botón "Guardar"
        buttonSave.setOnClickListener(v -> {
            // Guardar los cambios en el objeto Film
            film.setTitle(editTextTitle.getText().toString());
            film.setDirector(editTextDirector.getText().toString());
            if (editTextYear.getText().toString().isEmpty()) {
                editTextYear.setText("0");
            }
            film.setYear(Integer.parseInt(editTextYear.getText().toString()));
            film.setGenre(spinnerGenre.getSelectedItemPosition());
            film.setFormat(spinnerFormat.getSelectedItemPosition());
            film.setComments(editTextDescription.getText().toString());

            FilmDataSource.setFilm(filmPosition, film);
            // Mostrar mensaje de confirmación
            showCustomToast("Cambios aplicados correctamente.");

            // Regresar a la actividad anterior
            finish();
        });

        // Configurar el botón "Cancelar"
        buttonCancel.setOnClickListener(v -> {
            // Mostrar mensaje de cancelación
            showCustomToast("Los cambios han sido cancelados.");

            // Regresar sin guardar cambios
            finish();
        });
    }

    private boolean checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                showCustomToast( "Permiso de cámara denegado");
            }
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
