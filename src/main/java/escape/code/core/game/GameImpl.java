package escape.code.core.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import escape.code.core.animationTimer.GameRender;
import escape.code.core.engine.Engine;
import escape.code.core.stageManager.StageManager;
import escape.code.enums.Level;
import escape.code.models.entities.User;
import escape.code.services.UserService;
import escape.code.utils.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * Keeps the main logic for running the game
 */
@Singleton
public class GameImpl implements Game, GameRender.Listener {

    private static final int LEVEL_INCREMENTER = 1;
    private static final int NUMBER_OF_LEVELS = Level.values().length;

    private StageManager stageManager;
    private UserService userService;
    private Engine engine;
    private final GameRender timeline;

    private User user;
    private Stage currentStage;
    private MediaPlayer mediaPlayer;
    private FXMLLoader fxmlLoader;

    @Inject
    public GameImpl(StageManager stageManager, UserService userService, Engine engine, GameRender timeline) {
        this.stageManager = stageManager;
        this.userService = userService;
        this.engine = engine;
        this.timeline = timeline;
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
        this.timeline.setListener(this);
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

    @Override
    public void onHandle(long now) {
        try {
            this.engine.play();
        } catch (IllegalStateException e) {
            this.gameProcessInitialize();
        } catch (NullPointerException ex) {
            this.timeline.stop();
        }
    }
}
