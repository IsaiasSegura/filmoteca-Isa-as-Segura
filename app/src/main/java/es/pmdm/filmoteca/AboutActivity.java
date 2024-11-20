package es.pmdm.filmoteca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);


        // Configuración del botón "Ir al sitio web"
        Button buttonWebsite = findViewById(R.id.button);
        buttonWebsite.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com"));
            startActivity(intent);
        });

        // Configuración del botón "Soporte"
        Button buttonSupport = findViewById(R.id.button2);
        buttonSupport.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:filmoteca@pmdm.es")); // Email de destino
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Filmoteca"); // Asunto del correo
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Texto del correo de soporte"); // Cuerpo del correo
            startActivity(emailIntent);
        });

        // Configuración del botón "Volver"
        Button buttonBack = findViewById(R.id.button3);
        buttonBack.setOnClickListener(view -> finish()); // Finaliza la actividad y regresa a la anterior
    }

    // Control del botón de retroceso en la Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Finaliza la actividad
        return true;
    }
}