package es.pmdm.filmoteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FilmDataSource {
    private static ArrayList<Film> films;
    private static File filmsFile;

    public static void Initialize(File filmsFileIn) {
        filmsFile = filmsFileIn;
        if (!filmsFile.exists()) {
            films = new ArrayList<>();
            defaultData();
        } else {
            films = new ArrayList<>();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filmsFile))) {
                films = (ArrayList<Film>) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void saveFilms() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filmsFile, false))) {
            oos.writeObject(films);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Film> getAllFilms() {
        return films;
    }

    public static Film getFilm(int posicion) {
        return films.get(posicion);
    }

    public static void setFilm(int posicion, Film film) {
        films.set(posicion, film);
        saveFilms();
    }

    public static void saveNewFilm(Film newFilm) {
        films.add(0, newFilm);
        saveFilms();
    }

    public static void removeFilmByPosition(int position) {
        films.remove(position);
        saveFilms();
    }

    public static void resetFilmData() {
        films.clear();
        defaultData();
    }

    private static void defaultData() {
        films.add(new Film(R.drawable.el_padrino, "The Godfather", 1972, "Francis Coppola", Film.GENRE_DRAMA, Film.FORMAT_DVD, "https://www.imdb.com/title/tt0068646/", "El envejecido patriarca de una dinastía del crimen organizado en la ciudad de Nueva York de la posguerra transfiere el control de su imperio clandestino a su reacio hijo menor."));
        films.add(new Film(R.drawable.interestelar, "Interestelar", 2014, "Christopher Nolan", Film.GENRE_SCIFI, Film.FORMAT_DIGITAL, "https://www.imdb.com/title/tt0816692/", "Un equipo de exploradores viaja a través de un agujero de gusano en un intento por garantizar la supervivencia de la humanidad."));
        films.add(new Film(R.drawable.pulp_fiction, "Pulp Fiction", 1994, "Quentin Tarantino", Film.GENRE_ACTION, Film.FORMAT_BLURAY, "https://www.imdb.com/title/tt0110912/", "La vida de dos sicarios, un boxeador y otros personajes se entrelazan en una narrativa no lineal llena de violencia y humor negro."));
        films.add(new Film(R.drawable.inception, "Inception", 2010, "Christopher Nolan", Film.GENRE_SCIFI, Film.FORMAT_DIGITAL, "https://www.imdb.com/title/tt1375666/", "Un ladrón que roba secretos a través de sueños es contratado para plantar una idea en la mente de un empresario."));
        films.add(new Film(R.drawable.the_dark_knight, "The Dark Knight", 2008, "Christopher Nolan", Film.GENRE_ACTION, Film.FORMAT_BLURAY, "https://www.imdb.com/title/tt0468569/", "Batman se enfrenta al caos desatado por el Joker, un criminal demente que busca sumir a Gotham en la anarquía."));
        films.add(new Film(R.drawable.forrest_gump, "Forrest Gump", 1994, "Robert Zemeckis", Film.GENRE_DRAMA, Film.FORMAT_DVD, "https://www.imdb.com/title/tt0109830/", "La vida de un hombre con un coeficiente intelectual bajo que logra hazañas extraordinarias."));
        films.add(new Film(R.drawable.matrix, "The Matrix", 1999, "Lana Wachowski, Lilly Wachowski", Film.GENRE_SCIFI, Film.FORMAT_BLURAY, "https://www.imdb.com/title/tt0133093/", "Un hacker descubre que su mundo es una simulación creada por máquinas para esclavizar a la humanidad."));
        films.add(new Film(R.drawable.schindlers_list, "Schindler's List", 1993, "Steven Spielberg", Film.GENRE_DRAMA, Film.FORMAT_DVD, "https://www.imdb.com/title/tt0108052/", "La historia de Oskar Schindler, quien salvó a miles de judíos durante el Holocausto."));
        films.add(new Film(R.drawable.avatar, "Avatar", 2009, "James Cameron", Film.GENRE_SCIFI, Film.FORMAT_DIGITAL, "https://www.imdb.com/title/tt0499549/", "Un exmarine se involucra en la lucha de un pueblo alienígena por preservar su planeta."));
        films.add(new Film(R.drawable.the_shining, "The Shining", 1980, "Stanley Kubrick", Film.GENRE_HORROR, Film.FORMAT_DVD, "https://www.imdb.com/title/tt0081505/", "Un escritor comienza a perder la cordura mientras cuida de un hotel remoto en invierno."));
        films.add(new Film(R.drawable.titanic, "Titanic", 1997, "James Cameron", Film.GENRE_DRAMA, Film.FORMAT_DVD, "https://www.imdb.com/title/tt0120338/", "Un romance entre un joven artista y una aristócrata florece a bordo del trágico Titanic."));
        films.add(new Film(R.drawable.gladiator, "Gladiator", 2000, "Ridley Scott", Film.GENRE_ACTION, Film.FORMAT_BLURAY, "https://www.imdb.com/title/tt0172495/", "Un general romano es traicionado y se convierte en gladiador para vengar a su familia."));
        films.add(new Film(R.drawable.star_wars, "Star Wars: A New Hope", 1977, "George Lucas", Film.GENRE_SCIFI, Film.FORMAT_DVD, "https://www.imdb.com/title/tt0076759/", "Un joven granjero se une a una rebelión para enfrentarse a un imperio galáctico."));
        films.add(new Film(R.drawable.blade_runner, "Blade Runner", 1982, "Ridley Scott", Film.GENRE_SCIFI, Film.FORMAT_DVD, "https://www.imdb.com/title/tt0083658/", "En un futuro distópico, un cazador de androides busca a replicantes fugitivos."));
        films.add(new Film(R.drawable.fight_club, "Fight Club", 1999, "David Fincher", Film.GENRE_DRAMA, Film.FORMAT_BLURAY, "https://www.imdb.com/title/tt0137523/", "Un hombre insatisfecho con su vida inicia un club secreto de peleas."));
        films.add(new Film(R.drawable.the_lion_king, "The Lion King", 1994, "Roger Allers, Rob Minkoff", Film.GENRE_DRAMA, Film.FORMAT_DIGITAL, "https://www.imdb.com/title/tt0110357/", "La historia de un león joven que debe recuperar su lugar como rey tras la traición de su tío."));
        films.add(new Film(R.drawable.alien, "Alien", 1979, "Ridley Scott", Film.GENRE_HORROR, Film.FORMAT_BLURAY, "https://www.imdb.com/title/tt0078748/", "La tripulación de una nave espacial lucha por sobrevivir contra una criatura extraterrestre mortal."));
        saveFilms();
    }
}
