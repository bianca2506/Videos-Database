package entertainment;

import Tema1.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    private final Map<User, Double> ratingUser;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.ratingUser = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    public Map<User, Double> getRatingUser() {
        return ratingUser;
    }

    /**
     * Calculate rating of a season
     */

    /**
     * Calculate rating of a movie
     */
    public double getSumRatings() {
        double s = 0;

        for (int i = 0; i < ratingUser.values().size(); i++) {
            s += i;
        }
        int size = ratingUser.size();
        if (size != 0) {
            return s / size;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

