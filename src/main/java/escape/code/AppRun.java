package escape.code;

import com.google.inject.Guice;
import com.google.inject.Injector;
import escape.code.configurations.InjectionModule;
import escape.code.controllers.LoginController;
import escape.code.core.game.Game;
import escape.code.enums.Level;
import escape.code.services.PuzzleRectangleService;
import escape.code.services.PuzzleService;
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
        boolean shoudlSkipSeed = !this.isFirstRun(puzzleService, puzzleRectangleService);
        if (shoudlSkipSeed) {
            return;
        }
        String[][] taskParams = {
                {"-What the following code will print?", "piano", "Do you really need the numbers?", "What could it be??", "/pictures/ComputerTaskWhite.png", "0", "NONE"},
                {"-One piano button is stuck!", "13", "How will you EXit?", "You are one step away from the exit.", "/pictures/PianoTask.jpg", "0", "BOOK"},
                {"-The way to unlock the door is to know the secret number.", "111", "Do you see something unusual?", "The door is open now.", "/pictures/LibraryWithJoker.jpg", "0", "KEY"},
                {"Success pass the level", "", "Success pass the level", "", "/pictures/LibraryWithJoker.jpg", "0", "NONE"}, //// TODO: 7/17/2016
                {"-Does the chessboard reminds you of something?", "matrix", "[][]", "Oh, look it's currently playing!", "/pictures/ChessBoard.jpg", "1", "NONE"},
                {"", "coffee", "It's hot", "What could it be??", "/pictures/TvPuzzle.png", "1", "BOOK"},
                {"Who collects the garbage?", "garbage collector", "Be like Java!! Collect your own garbage.", "The second door is open now.", "/pictures/Bin.png", "1", "KEY"},
                {"Success pass the level", "", "", "", "/pictures/LibraryWithJoker.jpg", "0", "NONE"}, //// TODO: 7/17/2016
                {"-Does this reminds you of something?", "binary tree", "The elements are ordered using their natural ordering.", "You get one unknown item. Try figure it out what is it.", "/pictures/TabletTask.png", "2", "NONE"},
                {"-What is the equivalent of experimenting with test-tubes\n in programming?", "unit testing", "You should check if everything works fine with your code. What will you do?", "Now put the test-tupe in the microscope", "/pictures/TestingTask.jpg", "2", "BOOK"},
                {"-All number must be even.", "4812", "All numbers are divisible by 4.", "It seems like there is no door.Try to put the key somewhere else and escape \nfrom the mystery place forever.", "/pictures/MicroscopeTask.png", "2", "KEY"},
                {"Success finished the game", "", "", "", "/pictures/LibraryWithJoker.jpg", "2", "NONE"}
        };
        Arrays.stream(taskParams).forEach(puzzleService::createPuzzle);
        String[][] taskParamsRect = {
                {"computer", "0", "1"},
                {"piano", "0", "2"},
                {"library", "0", "3"},
                {"door", "0", "4"},
                {"chess", "1", "5"},
                {"tv", "1", "6"},
                {"kitchen", "1", "7"},
                {"door", "1", "8"},
                {"tablet", "2", "9"},
                {"testing", "2", "10"},
                {"microscope", "2", "11"},
                {"exit", "2", "12"}
        };
        Arrays.stream(taskParamsRect).forEach(puzzleRectangleService::createPuzzleRectangle);
    }

    private boolean isFirstRun(PuzzleService puzzleService, PuzzleRectangleService puzzleRectangleService) {
        boolean result = puzzleService.getAllByLevel(Level.ZERO.ordinal()).size() == 0 &&
                puzzleRectangleService.getAllByLevel(Level.ZERO.ordinal()).size() == 0;
        return result;
    }
}