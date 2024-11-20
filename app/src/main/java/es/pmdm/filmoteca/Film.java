package es.pmdm.filmoteca;

public class Film {
    // Tipos de formatos
    // Almacena el índice en el que se encuentra el valor de cada formato en el recurso arrarys.
    public final static int FORMAT_DVD = 0;
    public final static int FORMAT_BLURAY = 1;
    public final static int FORMAT_DIGITAL = 2;
    // Tipos de géneros
    // Almacena el índice en el que se encuentra el valor de cada formato en el recurso arrarys.
    public final static int GENRE_ACTION = 0;
    public final static int GENRE_COMEDY = 1;
    public final static int GENRE_DRAMA = 2;
    public final static int GENRE_SCIFI = 3;
    public final static int GENRE_HORROR = 4;
    // Propiedades de la clase
    private int imageResId;
    private String title;
    private String director;
    private int year;
    private int genre;
    private int format;
    private String imdbUrl;
    private String comments;
// Crea los constructores, getters y setters. Y todos aquellos métodos que consideres.

    public Film() {}

    public Film(int imageResId, String title, int year, String director, int genre, int format, String imdbUrl, String comments) {
        this.imageResId = imageResId;
        this.title = title;
        this.year = year;
        this.director = director;
        this.genre = genre;
        this.format = format;
        this.imdbUrl = imdbUrl;
        this.comments = comments;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public String getImdbUrl() {
        return imdbUrl;
    }

    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String dataGeneroFormato() {
        StringBuilder sb = new StringBuilder();
        if (getFormat() == 0) {
            sb.append("DVD");
        } else if (getFormat() == 1) {
            sb.append("BLURAY");
        } else {
            sb.append("DIGITAL");
        }
        sb.append(", ");
        if (getGenre() == 0) {
            sb.append("ACTION");
        } else if (getGenre() == 1) {
            sb.append("COMEDY");
        } else if (getGenre() == 2) {
            sb.append("DRAMA");
        } else if (getGenre() == 3) {
            sb.append("SCIFI");
        } else {
            sb.append("HORROR");
        }

        return sb.toString();
    }
}
