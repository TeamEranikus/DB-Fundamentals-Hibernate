package escape.code.core.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import escape.code.core.stageManager.StageManager;
import escape.code.core.engine.Engine;
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
@Singleton
public class GameImpl implements Game {

    private static final int LEVEL_INCREMENTER = 1;
    private static final int NUMBER_OF_LEVELS = Level.values().length;

    private StageManager stageManager;
    private UserService userService;
    private Engine engine;

    private User user;
    private AnimationTimer timeline;
    private Stage currentStage;
    private MediaPlayer mediaPlayer;
    private FXMLLoader fxmlLoader;

    @Inject
    public GameImpl(StageManager stageManager, UserService userService, Engine engine) {
        this.stageManager = stageManager;
        this.userService = userService;
        this.engine = engine;
    }

    /**
     * Initialize current stage
     *
     * @param stage - stage to be initialized
     */
    public void initialize(Stage stage) {
        this.currentStage = stage;
        this.login();
    }

    /**
     * Runs the game:
     * Initialize engine for current stage
     * Runs audio
     * Set up animation timer
     */
    public void run() {
        this.fxmlLoader = this.stageManager
                .loadSceneToStage(this.currentStage, Level.getByNum(this.user.getLevel()).getPath());
        this.engine.setLoader(this.fxmlLoader);
        this.engine.setUser(this.user);
        this.engine.initialize();
        this.playAudio();
        this.timeline = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    GameImpl.this.engine.play();
                } catch (IllegalStateException e) {
                    GameImpl.this.gameProcessInitialize();
                } catch (NullPointerException ex) {
                    GameImpl.this.timeline.stop();
                }
            }
        };
        this.timeline.start();
    }

    private void gameProcessInitialize() {
        GameImpl.this.mediaPlayer.stop();
        GameImpl.this.user.setLevel((GameImpl.this.user.getLevel() + LEVEL_INCREMENTER) % NUMBER_OF_LEVELS);
        GameImpl.this.fxmlLoader = GameImpl.this.stageManager
                .loadSceneToStage(GameImpl.this.currentStage, Level.getByNum(GameImpl.this.user.getLevel())
                .getPath());
        GameImpl.this.userService.updateUser(GameImpl.this.user);
        GameImpl.this.engine.setLoader(GameImpl.this.fxmlLoader);
        GameImpl.this.engine.setUser(GameImpl.this.user);
        GameImpl.this.engine.initialize();
        GameImpl.this.mediaPlayer.play();
    }

    /**
     * Set current logged in user
     *
     * @param currentUser - current logged in user
     */
    public void setUser(User currentUser) {
        this.user = currentUser;
    }

    /**
     * Loads game menu stage
     */
    public void loadMainMenu() {
        this.fxmlLoader = this.stageManager.loadSceneToStage(this.currentStage, Constants.MENU_FXML_PATH);
    }

    /**
     * Loads game login stage
     */
    private void login() {
       this.fxmlLoader = this.stageManager.loadSceneToStage(this.currentStage, Constants.LOGIN_FXML_PATH);
    }

    /**
     * Plays game audio file
     */
    private void playAudio() {
        File file = new File(Constants.SOUNDS_PATH);
        String mediaUrl = file.toURI().toString();
        Media media = new Media(mediaUrl);
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.play();
    }
}
