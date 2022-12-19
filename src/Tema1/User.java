package Tema1;

import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class User {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * Number of Ratings that are given by the user
     */
    private final Double numberofRating;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.numberofRating = 0.0;
    }


    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Double getRating() {
        return numberofRating;
    }

    /**
     * Metoda care intoarce un mesaj legat
     * de lista de videoclipuri favorite
     * @param title - titlul show-ului
     * @return un mesaj corespunzator
     */
    public String addFavorite(final String title) {
        String message;

        //daca filmul e vizionat
        if (getHistory().containsKey(title)) {
            //daca se afla deja in lista de filme favorite
            if (getFavoriteMovies().contains(title)) {
                message = "error -> " + title + " is already in favourite list";
                return message;
            } else {
                //adaug filmul in lista de favorite
                getFavoriteMovies().add(title);
                message = "success -> " + title + " was added as favourite";
                return message;
            }
        }
        message = "error -> " + title + " is not seen";
        return message;
    }

    /**
     * Metoda care intoarce un mesaj legat de
     * vizionarile unul videoclip
     * @param title - titlul show-ului
     * @return un mesaj corespunzator
     */
    public String addView(final String title) {
        String message;
        // verific daca filmul e vizionat
        Integer numberOfViews = history.get(title);

        // daca l-am mai vizionat
        if (numberOfViews != null) {
            // incrementez numarul de vizualizari
            numberOfViews++;
            history.replace(title, numberOfViews);
            message = "success -> " + title + " was viewed with total views of " + numberOfViews;
        } else {
            // daca nu a mai fost vizionat
            history.put(title, 1);
            message = "success -> " + title + " was viewed with total views of " + 1;
        }
        return message;
    }

    /**
     * Metoda care intoarce un mesaj legat de
     * rating-ul videoclipurilor
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @param rating - rating dat ca parametru
     * @param numberOfSeasons - numar de sezoane
     * @param title - titlul show-urilor
     * @return un mesaj corespunzator
     */
    public String addRating(final List<Movie> movies, final List<Serial> serials,
                            final double rating, final int numberOfSeasons, final String title) {
        String message;

        // daca video-ul e vizionat
        if (history.get(title) != null) {
            // daca e film
            if (numberOfSeasons == 0) {
                // parcurg lista filmelor
                for (int i = 0; i < movies.size(); i++) {
                    if (title.equals(movies.get(i).getTitle())) {
                        //daca nu a mai dat rating
                        if (!movies.get(i).getRating().containsKey(this)) {
                            movies.get(i).getRating().put(this, rating);
                            message = "success -> " + title + " was rated with " + rating
                                    + " by " + getUsername();
                            return message;
                        }
                    }
                }
            } else {
                // daca e serial
                for (int i = 0; i < serials.size(); i++) {
                    if (title.equals(serials.get(i).getTitle())) {
                        //daca nu a mai dat rating
                        if (!((serials.get(i).getSeasons()).get(numberOfSeasons - 1))
                                .getRatingUser().containsKey(this)) {
                            (((serials.get(i).getSeasons()).get(numberOfSeasons - 1))
                                    .getRatingUser()).put(this, rating);
                            message = "success -> " + title + " was rated with " + rating
                                    + " by " + getUsername();
                            return message;
                        }
                    }
                }
            }
            message = "error -> " + title + " has been already rated";
        } else {
            message = "error -> " + title + " is not seen";
        }
        return message;
    }

    /**
     * Metoda care intoarce primul video nevăzut de un utilizator
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @return un mesaj corespunzator
     */
    public String standard(final List<Movie> movies, final List<Serial> serials) {
        String message = "";

        // parcurg lista filmelor
        for (int i = 0; i < movies.size(); i++) {
            // daca utilizatorul nu a mai vizionat filmul
            if (!this.getHistory().containsKey(movies.get(i).getTitle())) {
                message = "StandardRecommendation result: " + movies.get(i).getTitle();
                return message;
            }
        }
        // parcurg lista serialelor
        for (int i = 0; i < serials.size(); i++) {
            // daca utilizatorul nu a mai vizionat serialul
            if (!this.getHistory().containsKey(serials.get(i).getTitle())) {
                message = "StandardRecommendation result: " + serials.get(i).getTitle();
                return message;
            }
        }
        message = "StandardRecommendation cannot be applied!";
        return message;
    }

    /**
     * Metoda care intoarce cel mai bun video nevizualizat
     * de un utilizator
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @return un mesaj corespunzator
     */
    public String bestUnseen(final List<Movie> movies, final List<Serial> serials) {
        String message = "";
        Map.Entry<String, Double> map = null;

        // parcurg lista filmelor
        for (int i = 0; i < movies.size(); i++) {
            // daca utilizatorul nu a mai vizionat filmul
            if (!this.getHistory().containsKey(movies.get(i).getTitle())) {
                // ordonez descrescator dupa suma rating-urilor
                if (map == null || movies.get(i).getSumRatings() > map.getValue()) {
                    map = Map.entry(movies.get(i).getTitle(), movies.get(i).getSumRatings());
                }
            }
        }
        // parcurg lista serialelor
        for (int i = 0; i < serials.size(); i++) {
            // daca utilizatorul nu a mai vizionat serialul
            if (!this.getHistory().containsKey(serials.get(i).getTitle())) {
                // ordonez descrescator dupa suma ratingurilor
                if (map == null || serials.get(i).getSumRatings() > map.getValue()) {
                    map = Map.entry(serials.get(i).getTitle(), serials.get(i).getSumRatings());
                }
            }
        }
        // daca map-ul este gol
        if (map == null) {
            message = "BestRatedUnseenRecommendation cannot be applied!";
            return message;
        }
        message = "BestRatedUnseenRecommendation result: " + map.getKey();
        return message;
    }

    /**
     * Metoda care intoarce primul video nevizualizat
     * din cel mai popular gen
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @return un mesaj corespunzator
     */
    public String popular(final List<Movie> movies, final List<Serial> serials) {
        String message = "";
        Map<String, Integer> genreMap = new HashMap<>();
        List<Map.Entry<String, Integer>> sortList = new ArrayList<>(genreMap.entrySet());

        // daca utilizatorul este premium
        if (this.getSubscriptionType().equals("PREMIUM")) {
            // parcurg lista de filme
            for (int i = 0; i < movies.size(); i++) {
                // sortez lista genurilor
                movies.get(i).getGenres().stream().
                        filter(genre -> {
                            return genreMap.computeIfPresent(genre, (k, v) -> v + 1) == null;
                        }).forEach(genre -> genreMap.put(genre, 1));
            }
            // parcurg lista de seriale
            for (int i = 0; i < serials.size(); i++) {
                // sortez lista genurilor
                serials.get(i).getGenres().stream().
                        filter(genre -> {
                            return genreMap.computeIfPresent(genre, (k, v) -> v + 1) == null;
                        }).forEach(genre -> genreMap.put(genre, 1));
            }
            sortList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // parcurg lista filmelor
            for (int i = 0; i < movies.size(); i++) {
                // daca utilizatorul nu a mai vizionat filmul
                if (!getHistory().containsKey(movies.get(i).getTitle())) {
                    message = "PopularRecommendation result: " + movies.get(i).getTitle();
                    return message;
                }
            }
            // parcurg lista serialelor
            for (int i = 0; i < serials.size(); i++) {
                // daca utilizatorul nu a mai vizionat serialul
                if (!getHistory().containsKey(serials.get(i).getTitle())) {
                    message = "PopularRecommendation result: " + serials.get(i).getTitle();
                    return message;
                }
            }
            message = "PopularRecommendation cannot be applied!";
        } else {
            // daca utilizatorul este basic
            message = "PopularRecommendation cannot be applied!";
        }
        return message;
    }

    /**
     * Metoda care intoarce toate videoclipurile nevăzute de user
     * dintr-un anumit gen
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @param action - actiune data la input
     * @return un mesaj corespunzator
     */
    public String search(final List<Movie> movies, final List<Serial> serials,
                         final ActionInputData action) {
        String message = "";
        List<String> list = new ArrayList<>();

        // fac o copie listelor de filme si de seriale
        List<Movie> copyMovies = new ArrayList<>(movies);
        List<Serial> copySerials = new ArrayList<>(serials);

        List<Show> showList = new ArrayList<>();
        List<Show> copyShowList;

        // scot din listele de copii toate video-urile care nu au genul corespunzator
        copyMovies.removeIf(movie -> !movie.getGenres().contains(action.getGenre()));
        copySerials.removeIf(serial -> !serial.getGenres().contains(action.getGenre()));

        // adaug in lista de show-uri filmele filtrate
        for (int i = 0; i < copyMovies.size(); i++) {
            showList.add(copyMovies.get(i));
        }

        // adaug in lista de show-uri serialele filtrate
        for (int i = 0; i < copySerials.size(); i++) {
            showList.add(copySerials.get(i));
        }
        // sortez lista show-urilor dupa titlu si apoi dupa media rating-urilor
        copyShowList = showList.stream().sorted(Comparator.comparing(Show::getTitle))
                .collect(Collectors.toList()).stream().
                sorted(Comparator.comparingDouble(Show::getAverageRating))
                .collect(Collectors.toList());

        // daca utilizatorul este premium
        if (this.getSubscriptionType().equals("PREMIUM")) {
            // parcurg lista show-urilor sortata
            for (Show show : copyShowList) {
                //daca utilizatorul nu a mai vazut show-ul
                if (!this.getHistory().containsKey(show.getTitle())) {
                    //adaug show-ul in lista
                    list.add(show.getTitle());
                }
            }
            //daca lista nu este goala afisez rezultatul
            if (!list.isEmpty()) {
                message = "SearchRecommendation result: " + list;
                return message;
            } else {
                //daca lista este goala
                message = "SearchRecommendation cannot be applied!";
                return message;
            }
        } else {
            //daca utilizatorul este basic
            message = "SearchRecommendation cannot be applied!";
        }
        return message;
    }

    /**
     * Metoda care intoarce întoarce videoclipul care e cel
     * mai des intalnit in lista de favorite
     * @param movies - lista de filme
     * @param serial - lista de seriale
     * @return un mesaj corespunzator
     */
    public String favorite(final List<Movie> movies, final List<Serial> serial) {
        String message = "";
        List<Show> showList = new ArrayList<>();
        List<Show> copyShowList;

        // adaug in lista show-urilor toate filmele
        for (int i = 0; i < movies.size(); i++) {
            showList.add(movies.get(i));
        }

        // adaug in lista show-urilor toate serialele
        for (int i = 0; i < serial.size(); i++) {
            showList.add(movies.get(i));
        }
        // parcurg lista de filme favorite
        for (String favorites : this.getFavoriteMovies()) {
            // parcurg lista cu show-uri
            for (Show show : showList) {
                // daca gasesc un show favorit
                if (favorites.equals(show.getTitle())) {
                    // incrementez totalul show-urilor favorite
                    Integer numberFavorites = show.getTotalFavorites() + 1;
                    show.setTotalFavorites(numberFavorites);
                }
            }
        }
        // sortez lista show-urilor
        copyShowList = showList.stream().sorted(Comparator.comparingInt(
                Show::getTotalFavorites).reversed()).collect(Collectors.toList());

        // daca utilizatorul este premium
        if (this.getSubscriptionType().equals("PREMIUM")) {
            // parcurg lista sortata
            for (Show show : copyShowList) {
                // daca nu a mai vazut show-ul
                if (!this.getHistory().containsKey(show.getTitle())) {
                    message = "FavoriteRecommendation result: " + show.getTitle();
                    return message;
                }
            }
        }
        message = "FavoriteRecommendation cannot be applied!";
        return message;
    }

    @Override
    public String toString() {
        return "User{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + ", rating="
                + numberofRating + '}';
    }
}
