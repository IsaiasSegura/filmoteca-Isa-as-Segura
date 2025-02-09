package es.pmdm.filmoteca;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class FilmListActivity extends AppCompatActivity {
    private static final int PERMISSION_SEND_SMS = 1;
    private FilmAdapter miAdaptador;
    private ListView list;
    private String selectedFilmTitle;
    private static final int PICK_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        String fileName = "films.dat";
        File filmsFile = new File(getFilesDir(), fileName);
        FilmDataSource.Initialize(filmsFile);
        list = findViewById(R.id.listaPelis);
        miAdaptador = new FilmAdapter(this, R.layout.item_film, FilmDataSource.getAllFilms());
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
        int itemId = item.getItemId();

        if (itemId == R.id.menu_acercaDe) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (itemId == R.id.menu_anyadirPeli) {
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
            FilmDataSource.saveNewFilm(newFilm);
            miAdaptador.notifyDataSetChanged();
            return true;
        } else if (itemId == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (itemId == R.id.menu_mas_info) {
            startActivity(new Intent(this, MoreActivity.class));  // Abre la actividad de video
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        selectedFilmTitle = FilmDataSource.getFilm(position).getTitle();
        if (item.getItemId() == R.id.menu_delete) {
            FilmDataSource.removeFilmByPosition(position);
            miAdaptador.notifyDataSetChanged();
            showCustomToast("Película eliminada");
            return true;
        } else if (item.getItemId() == R.id.menu_share) {
            showShareDialog();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    //le pasamos nuestro tlf pero podramos pasar el que quisieramos de contactos
    private void showShareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recomendar Película")
                .setItems(new String[]{"Compartir por SMS", "Compartir por WhatsApp"}, (dialog, which) -> {
                    if (which == 0) {
                        seleccionarContacto();  // Llamamos al método para abrir contactos
                    } else {
                        enviarViaWhatsApp("660844952", "Te recomiendo la película: " + selectedFilmTitle);
                    }
                })
                .show();
    }


    private void seleccionarContacto() {
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Solo contactos con números
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }


    private void enviarViaWhatsApp(String phoneNumber, String message) {
        try {
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(whatsappIntent);
        } catch (ActivityNotFoundException e) {
            showCustomToast("WhatsApp no está instalado");
        }
    }

    private void enviarViaAppMensajes(String phoneNumber, String message) {
        Uri smsUri = Uri.parse("smsto:" + phoneNumber);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
        smsIntent.putExtra("sms_body", message);
        try {
            startActivity(smsIntent);
        } catch (ActivityNotFoundException e) {
            showCustomToast("No hay una aplicación de mensajería instalada");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        miAdaptador.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            if (contactUri != null) {
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                try (Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNumber = cursor.getString(numberIndex);
                        enviarViaAppMensajes(phoneNumber, "Te recomiendo la película: " + selectedFilmTitle);
                    }
                }
            }
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