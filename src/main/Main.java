package main;

import Tema1.Movie;
import Tema1.Serial;
import Tema1.User;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //creez lista pentru utilizatori
        List<User> userList = new ArrayList<>();
        input.getUsers().forEach(userInputData -> {
            User user = new User(userInputData.getUsername(), userInputData.getSubscriptionType(),
                    userInputData.getHistory(), userInputData.getFavoriteMovies());
            userList.add(user);
        });

        //creez lista pentru filme
        List<Movie> movieList = new ArrayList<>();
        input.getMovies().forEach(movieInputData -> {
            Movie movie = new Movie(movieInputData.getTitle(), movieInputData.getCast(),
                    movieInputData.getGenres(), movieInputData.getYear(),
                    movieInputData.getDuration());
            movieList.add(movie);
        });

        //creez lista pentru seriale
        List<Serial> serialList = new ArrayList<>();
        input.getSerials().forEach(serialInputData -> {
            Serial serial = new Serial(serialInputData.getTitle(), serialInputData.getCast(),
                    serialInputData.getGenres(), serialInputData.getNumberSeason(),
                    serialInputData.getSeasons(), serialInputData.getYear());
            serialList.add(serial);
        });

        List<ActionInputData> actionInputDataList = input.getCommands();

        // parcurg lista cu actiuni
        for (ActionInputData action: actionInputDataList) {
            String message = "";
            if (action.getActionType().equals("command")) {
                if (action.getType().equals("favorite")) {
                    // parcurg lista cu utilizatori
                    for (User user : userList) {
                        // gasesc utilizatorul
                        if (action.getUsername().equals(user.getUsername())) {
                            message = user.addFavorite(action.getTitle());
                        }
                    }
                } else if (action.getType().equals("view")) {
                    // parcurg lista cu utilizatori
                    for (User user : userList) {
                        // gasesc utilizatorul
                        if (action.getUsername().equals(user.getUsername())) {
                            message = user.addView(action.getTitle());
                        }
                    }
                } else if (action.getType().equals("rating")) {
                    // parcurg lista cu utilizatori
                    for (User user : userList) {
                        // gasesc utilizatorul
                        if (action.getUsername().equals(user.getUsername())) {
                            message = user.addRating(movieList, serialList,
                                    action.getGrade(), action.getSeasonNumber(), action.getTitle());
                        }
                    }
                }
            } else if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard")) {
                    // parcurg lista cu utilizatori
                    for (User user : userList) {
                        // gasesc utilizatorul
                        if (action.getUsername().equals(user.getUsername())) {
                            message = user.standard(movieList, serialList);
                        }
                    }
                } else if (action.getType().equals("best_unseen")) {
                    // parcurg lista cu utilizatori
                    for (User user : userList) {
                        // gasesc utilizatorul
                        if (action.getUsername().equals(user.getUsername())) {
                            message = user.bestUnseen(movieList, serialList);
                        }
                    }
                } else if (action.getType().equals("popular")) {
                    // parcurg lista cu utilizatori
                    for (User user : userList) {
                        // gasesc utilizatorul
                        if (action.getUsername().equals(user.getUsername())) {
                            message = user.popular(movieList, serialList);
                        }
                    }
               } else if (action.getType().equals("search")) {
                    // parcurg lista cu utilizatori
                    for (User user : userList) {
                        // gasesc utilizatorul
                        if (action.getUsername().equals(user.getUsername())) {
                            message = user.search(movieList, serialList, action);
                        }
                    }
                } else if (action.getType().equals("favorite")) {
                    // parcurg lista cu utilizatori
                    for (User user : userList) {
                        // gasesc utilizatorul
                        if (action.getUsername().equals(user.getUsername())) {
                            message = user.favorite(movieList, serialList);
                        }
                    }
                }
            }
            JSONObject jsonObject = fileWriter.writeFile(action.getActionId(), "null", message);
            arrayResult.add(jsonObject);
        }
        fileWriter.closeJSON(arrayResult);
    }
}
