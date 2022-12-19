package Tema1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Movie extends Show {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    private Map<User, Double> rating;

    public Movie(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {

        super(title, year, cast, genres);
        this.duration = duration;
        this.rating = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    public Map<User, Double> getRating() {
        return rating;
    }

    public void setRating(final Map<User, Double> rating) {
        this.rating = rating;
    }

    /**
     * @return suma rating-urilor unui film
     */
    public double getSumRatings() {
        // initializez suma la 0
        double s = 0;

        //parcurg lista de rating-uri
        for (int i = 0; i < getRating().values().size(); i++) {
            s += i;
        }
        int size = getRating().size();

        // daca lista are size-ul 0
        if (size == 0) {
            return 0;
        } else {
            // daca size-ul e diferit de 0 returnez suma impratita la size
            return s / size;
        }
    }

    @Override
    public String toString() {
        return "Movie{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
