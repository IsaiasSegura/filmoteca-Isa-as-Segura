package es.pmdm.filmoteca;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox checkBoxRegistrationEnabled;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        checkBoxRegistrationEnabled = findViewById(R.id.checkBoxRegistrationEnabled);

        // Cargar el valor actual de la preferencia
        boolean isRegistrationEnabled = sharedPreferences.getBoolean("registrationEnabled", true);
        checkBoxRegistrationEnabled.setChecked(isRegistrationEnabled);

        // Guardar el valor cuando el CheckBox cambie
        checkBoxRegistrationEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("registrationEnabled", isChecked);
            editor.apply();
        });
    }
}