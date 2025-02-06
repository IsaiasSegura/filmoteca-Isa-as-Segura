package es.pmdm.filmoteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "peliculas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "peliculas";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE_RES_ID = "imageResId";
    private static final String COLUMN_TITLE = "tittle";
    private static final String COLUMN_DIRECTOR = "director";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_FORMAT = "format";
    private static final String COLUMN_IMDB_URL = "imdbUrl";
    private static final String COLUMN_COMMENTS = "comments";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_IMAGE_RES_ID + " INTEGER NOT NULL," + COLUMN_TITLE + " TEXT NOT NULL ," +
                COLUMN_DIRECTOR + " TEXT NOT NULL," +
                COLUMN_YEAR + " INTEGER NOT NULL," +
                COLUMN_GENRE + " INTEGER NOT NULL," +
                COLUMN_FORMAT + " INTEGER NOT NULL," +
                COLUMN_IMDB_URL + " TEXT NOT NULL," +
                COLUMN_COMMENTS + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor mostrarDatos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    //mostrar datos preferences
    public Cursor mostrarDatosPreferences(String orden) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_TITLE + " " + orden);
    }

    public void borrarDatos(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //insertarDatos objeto

    public long insertarDatos(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_RES_ID, film.getImageResId());
        values.put(COLUMN_TITLE, film.getTitle());
        values.put(COLUMN_DIRECTOR, film.getDirector());
        values.put(COLUMN_YEAR, film.getYear());
        values.put(COLUMN_GENRE, film.getGenre());
        values.put(COLUMN_FORMAT, film.getFormat());
        values.put(COLUMN_IMDB_URL, film.getImdbUrl());
        values.put(COLUMN_COMMENTS, film.getComments());
        return db.insert(TABLE_NAME, null, values);
    }

    //actualizarDatos objeto
    public boolean actualizarDatos(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_RES_ID, film.getImageResId());
        values.put(COLUMN_TITLE, film.getTitle());
        values.put(COLUMN_DIRECTOR, film.getDirector());
        values.put(COLUMN_YEAR, film.getYear());
        values.put(COLUMN_GENRE, film.getGenre());
        values.put(COLUMN_FORMAT, film.getFormat());
        values.put(COLUMN_IMDB_URL, film.getImdbUrl());
        values.put(COLUMN_COMMENTS, film.getComments());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(film.getId())}) > 0;
    }

    // Obtener el número total de PELICULAS
    public int obtenerTotalJuegos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }




}