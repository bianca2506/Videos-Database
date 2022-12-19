package Tema1;

import entertainment.Season;

import java.util.ArrayList;

public final class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public Serial(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * @return suma rating-urilor unui serial
     */
    public double getSumRatings() {
        // initializez suma la 0
        double s = 0;

        // parcurg lista cu sezoane
        for (int i = 0; i < getSeasons().size(); i++) {
            // adaug in s suma rating-urilor
            s += getSeasons().get(i).getSumRatings();
        }
        // returnez suma impartita la numarul de sezoane
        return s / getNumberSeason();
    }
    @Override
    public String toString() {
        return "Serial{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + getNumberSeason()
                + ", seasons=" + getNumberSeason() + "\n\n" + '}';
    }
}
