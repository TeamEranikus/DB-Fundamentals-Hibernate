package escape.code;

import com.google.inject.Guice;
import com.google.inject.Injector;
import escape.code.configurations.InjectionModule;
import escape.code.controllers.LoginController;
import escape.code.core.game.Game;
import escape.code.core.parser.JSONParser;
import escape.code.enums.Level;
import escape.code.models.dtos.PuzzleDto;
import escape.code.models.dtos.PuzzleRectangleDto;
import escape.code.services.PuzzleRectangleService;
import escape.code.services.PuzzleService;
import escape.code.utils.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Game entry class
 */
public class AppRun extends Application {

    /**
     * Escape code start point
     *
     * @param primaryStage main stage for the game
     */
    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new InjectionModule());
        this.seedData(injector);
        injector.injectMembers(LoginController.class);
        Game game = injector.getInstance(Game.class);
        game.initialize(primaryStage);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    /**
     * Main entry point
     *
     * @param args arguments for the method
     */
    public static void main(String[] args) {
        AppRun.launch(args);
    }

    private void seedData(Injector injector) {
        PuzzleService puzzleService = injector.getInstance(PuzzleService.class);
        PuzzleRectangleService puzzleRectangleService = injector.getInstance(PuzzleRectangleService.class);
        boolean shouldSkipSeed = !this.isFirstRun(puzzleService, puzzleRectangleService);
        if (shouldSkipSeed) {
            return;
        }
        JSONParser jsonParser = injector.getInstance(JSONParser.class);

        PuzzleDto[] puzzles = jsonParser
                .read(PuzzleDto[].class, Constants.PATH_TO_PUZZLES_JSON);

        Arrays.stream(puzzles).forEach(puzzleService::createPuzzle);

        PuzzleRectangleDto[] rectangles = jsonParser
                .read(PuzzleRectangleDto[].class, Constants.PATH_TO_PUZZLE_RECTANGLES_JSON);

        Arrays.stream(rectangles).forEach(puzzleRectangleService::createPuzzleRectangle);
    }

    @SuppressWarnings("unchecked")
    private boolean isFirstRun(PuzzleService puzzleService, PuzzleRectangleService puzzleRectangleService) {
        return puzzleService.getAllByLevel(Level.ZERO.ordinal()).size() == 0 &&
                puzzleRectangleService.getAllByLevel(Level.ZERO.ordinal()).size() == 0;
    }
}