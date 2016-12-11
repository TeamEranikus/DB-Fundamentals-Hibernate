package escape.code.core;

import com.google.inject.Inject;
import escape.code.core.engine.Engine;
import escape.code.core.engine.EngineImpl;
import escape.code.enums.Level;
import escape.code.models.entities.User;
import escape.code.services.UserService;
import escape.code.utils.Constants;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * Keeps the main logic for running the game
 */
public class Game {

    private static final int LEVEL_INCREMENTER = 1;
    private static final int NUMBER_OF_LEVELS = Level.values().length;

    @Inject
    private static StageManager stageManager;

    @Inject
    private static UserService userService;

    private static User user;
    private static Engine engine;
    private static FXMLLoader fxmlLoader;
    private static AnimationTimer timeline;
    private static Stage currentStage;
    private static MediaPlayer mediaPlayer;

    /**
     * Initialize current stage
     *
     * @param stage - stage to be initialized
     */
    public static void initialize(Stage stage) {
        currentStage = stage;
        login();
    }

    /**
     * Runs the game:
     * Initialize engine for current stage
     * Runs audio
     * Set up animation timer
     */
    public static void run() {
        fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Level.getByNum(user.getLevel()).getPath());
        engine = new EngineImpl(fxmlLoader, user, userService, stageManager);
        playAudio();
        timeline = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    engine.play();
                } catch (IllegalStateException e) {
                    mediaPlayer.stop();
                    user.setLevel((user.getLevel() + LEVEL_INCREMENTER) % NUMBER_OF_LEVELS);
                    fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Level.getByNum(user.getLevel())
                            .getPath());
                    userService.updateUser(user);
                    engine = new EngineImpl(fxmlLoader, user, userService, stageManager);
                    mediaPlayer.play();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    timeline.stop();
                }
            }
        };
        timeline.start();
    }

    /**
     * Set current logged in user
     *
     * @param currentUser - current logged in user
     */
    public static void setUser(User currentUser) {
        user = currentUser;
    }

    /**
     * Loads game menu stage
     */
    public static void loadMainMenu() {
        fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Constants.MENU_FXML_PATH);
    }

    /**
     * Loads game login stage
     */
    private static void login() {
        fxmlLoader = stageManager.loadSceneToPrimaryStage(currentStage, Constants.LOGIN_FXML_PATH);
    }

    /**
     * Plays game audio file
     */
    private static void playAudio() {
        File file = new File(Constants.SOUNDS_PATH);
        String mediaUrl = file.toURI().toString();
        Media media = new Media(mediaUrl);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
