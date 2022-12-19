package Tema1;

import java.util.ArrayList;

public abstract class Show {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    private Integer totalFavorites;
    private final Double averageRating;

    public Show(final String title, final int year,
                final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.totalFavorites = 0;
        this.averageRating = 0.0;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @return numarul total de show-uri favorite
     */
    public Integer getTotalFavorites() { return totalFavorites; }

    /**
     * @param totalFavorites - numarul total de show-uri favorite
     */
    public void setTotalFavorites(final Integer totalFavorites) {
        this.totalFavorites = totalFavorites;
    }

    /**
     * @return media rating-urilor
     */
    public Double getAverageRating() {
        return averageRating;
    }
}
