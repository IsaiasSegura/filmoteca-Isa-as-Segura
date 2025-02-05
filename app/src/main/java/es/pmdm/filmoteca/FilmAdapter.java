package es.pmdm.filmoteca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.List;

public class FilmAdapter extends ArrayAdapter<Film> {
    private final int mResource;
    private final List<Film> misFilms;

    public FilmAdapter(@NonNull Context context, int resource, @NonNull List<Film> objects) {
        super(context, resource, objects);
        mResource = resource;
        misFilms = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.portada = convertView.findViewById(R.id.imageFilm);
            holder.titulo = convertView.findViewById(R.id.textTitle);
            holder.autor = convertView.findViewById(R.id.textDirector);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Film film = misFilms.get(position);
        holder.portada.setImageResource(film.getImageResId());
        holder.titulo.setText(film.getTitle());
        holder.autor.setText(film.getDirector());

        return convertView;
    }

    static class ViewHolder {
        ImageView portada;
        TextView titulo;
        TextView autor;
    }
}