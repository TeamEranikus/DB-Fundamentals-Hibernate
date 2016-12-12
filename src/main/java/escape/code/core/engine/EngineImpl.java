package escape.code.core.engine;

import com.google.inject.Inject;
import escape.code.controllers.PuzzleController;
import escape.code.core.ResizableCanvas;
import escape.code.core.stageManager.StageManager;
import escape.code.core.TimeHandler;
import escape.code.enums.Item;
import escape.code.models.entities.Puzzle;
import escape.code.models.entities.PuzzleRectangle;
import escape.code.models.entities.Score;
import escape.code.models.entities.User;
import escape.code.models.sprites.Sprite;
import escape.code.services.PuzzleRectangleService;
import escape.code.services.UserService;
import escape.code.utils.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Keeps the logic for the running level scene
 * Run by Game class
 */
public class EngineImpl implements Engine, TimeHandler.Listener {

    private static final String STOP_TIMER = "stop";
    private static final String CANVAS_ID = "mainCanvas";
    private static final String END_GAME_RECTANGLE_ID = "exit";
    private static final String END_LEVEL_RECTANGLE_ID = "door";
    private static final String IMAGE_PLAYER_ID = "imagePlayer";
    private static final int PUZZLE_INCREMENTER = 1;
    private static final int DEFAULT_SPRITE_X_POSITION = 480;
    private static final int DEFAULT_SPRITE_Y_POSITION = 300;
    private static final String TIME_LABEL_ID = "timeLabel";

    private final PuzzleRectangleService puzzleRectangleService;
    private final UserService userService;
    private final StageManager stageManager;
    private final Sprite sprite;

    private HashMap<KeyCode, Boolean> keys;
    private ObservableMap<String, Object> objectsInCurrentScene;
    private ArrayList<Rectangle> rectCollision;
    private Rectangle currentPuzzleRectangle;
    private boolean hasToSetPuzzle;
    private Stage currentLoadedStage;
    private User user;
    private FXMLLoader loader;
    private Timeline timer;

    /**
     * Set up the current scene engine
     * @param puzzleRectangleService
     * @param userService  - service responsible for connection with user database
     * @param stageManager - manager for the current stage
     * @param sprite
     */
    @Inject
    public EngineImpl(PuzzleRectangleService puzzleRectangleService, UserService userService, StageManager stageManager, Sprite sprite) {
        this.puzzleRectangleService = puzzleRectangleService;
        this.userService = userService;
        this.stageManager = stageManager;
        this.sprite = sprite;
        this.hasToSetPuzzle = true;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set up current scene game play
     *
     * @throws IllegalStateException
     */
    public void play() throws IllegalStateException {
        this.sprite.updateCoordinates(this.keys, this.rectCollision);
        boolean hasCollision = this.sprite.checkForCol(this.currentPuzzleRectangle);
        Puzzle currentPuzzle = this.user.getPuzzleRectangle().getPuzzle();
        if (hasCollision) {
            this.setCurrentPuzzle();
            this.sprite.getImageView().setLayoutX(DEFAULT_SPRITE_X_POSITION);
            this.sprite.getImageView().setLayoutY(DEFAULT_SPRITE_Y_POSITION);
        }

        if (currentPuzzle.isAnswerGiven()) { // TODO: related to not resetting user properly at end of game!
            this.currentPuzzleRectangle.setDisable(true);
            long puzzleRectangleId = this.user.getPuzzleRectangle().getId();
            PuzzleRectangle puzzle = this.puzzleRectangleService.getOneById(puzzleRectangleId + PUZZLE_INCREMENTER);
            this.user.setPuzzleRectangle(puzzle);
            this.currentPuzzleRectangle = this.getCurrentPuzzleRectangle();
            this.userService.updateUser(this.user);
            this.hasToSetPuzzle = true;
            this.setItem(currentPuzzle.getItem());
        }
    }

    @Override
    public void onTimeChanged(long timeInSecs) {
        int hours = (int) (timeInSecs / Constants.SECONDS_IN_HOUR);
        timeInSecs %= Constants.SECONDS_IN_HOUR;
        int mins = (int) (timeInSecs / Constants.SECONDS_IN_MINUTE);
        int secs = (int) (timeInSecs % Constants.SECONDS_IN_MINUTE);
        String time = String.format("%02d:%02d:%02d", hours, mins, secs);

        this.user.setCurrentTime(timeInSecs);
        Label timeLabel = (Label) this.objectsInCurrentScene.get(TIME_LABEL_ID);
        timeLabel.setText(time);
    }

    public void initialize() {
        this.keys = new HashMap<>();
        this.rectCollision = new ArrayList<>();
        Scene scene = ((Pane) this.loader.getRoot()).getScene();
        this.currentLoadedStage = (Stage) (scene.getWindow());
        this.objectsInCurrentScene = this.loader.getNamespace();
        ImageView playerImage = (ImageView) this.objectsInCurrentScene.get(IMAGE_PLAYER_ID);
        ResizableCanvas canvas = (ResizableCanvas) this.objectsInCurrentScene.get(CANVAS_ID);
        this.sprite.setImageView(playerImage);
        this.sprite.setCurrentCanvas(canvas);
        this.currentPuzzleRectangle = this.getCurrentPuzzleRectangle();
        this.loadRectanglesCollision();
        this.updateItems();
        scene.setOnKeyPressed(event -> {
            event.consume();
            this.keys.put(event.getCode(), true);
        });
        scene.setOnKeyReleased(event -> {
            event.consume();
            this.keys.put(event.getCode(), false);
        });
        if (this.timer == null) {
            this.timer = this.setupTimer();
        }
        this.timer.play();
    }

    private Timeline setupTimer() {
        long timeInSecs = this.user.getCurrentTime();
        TimeHandler timeHandler = new TimeHandler(timeInSecs, this);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1d), timeHandler);
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.onTimeChanged(timeInSecs);
        return timeline;
    }

    private void setCurrentPuzzle() throws IllegalStateException {
        String currentPuzzleRectangleId = this.currentPuzzleRectangle.getId();
        this.keys.clear();
        if (!currentPuzzleRectangleId.contains(END_LEVEL_RECTANGLE_ID)
                && !currentPuzzleRectangleId.contains(END_GAME_RECTANGLE_ID)) {
            if (this.hasToSetPuzzle) {
                this.hasToSetPuzzle = false;
                Puzzle currentPuzzle = this.user.getPuzzleRectangle().getPuzzle();
                PuzzleController.setPuzzle(currentPuzzle);
            }
            this.stageManager.loadSceneToStage(new Stage(), Constants.PUZZLE_FXML_PATH);
        } else if (currentPuzzleRectangleId.contains(END_GAME_RECTANGLE_ID)) {
            if (this.user.getPuzzleRectangle().getId() == Constants.LAST_PUZZLE_ID) {
                Score score = new Score();
                score.setFinishTime(this.user.getCurrentTime());
                score.setUser(this.user);
                this.user.addScore(score);
                this.userService.resetUser(this.user);
            }
            this.userService.updateUser(this.user);
            this.stageManager.loadSceneToStage(this.currentLoadedStage, Constants.GAME_FINISHED_FXML_PATH);
        } else {
            this.currentLoadedStage.close();
            PuzzleRectangle puzzle = this.user.getPuzzleRectangle();
            long puzzleId = puzzle.getId();
            this.user.setPuzzleRectangle(this.puzzleRectangleService.getOneById(puzzleId + PUZZLE_INCREMENTER));
            throw new IllegalStateException(STOP_TIMER);
        }
    }


    private void loadRectanglesCollision() {
        this.objectsInCurrentScene.keySet().stream().filter(id -> id.endsWith("Col")).forEach(id -> {
            Rectangle current = (Rectangle) this.objectsInCurrentScene.get(id);
            this.rectCollision.add(current);
        });
    }

    /**
     * Gets current collision rectangle
     *
     * @return collision rectangle for the current puzzle
     */
    private Rectangle getCurrentPuzzleRectangle() {
        PuzzleRectangle puzzleRectangle = this.user.getPuzzleRectangle();
        Rectangle current = (Rectangle) this.objectsInCurrentScene.get(puzzleRectangle.getName());
        if(current != null) {
            current.setVisible(false);
        }
        return current;
    }

    private void setItem(Item currentItem) {
        if (!currentItem.name().equals(Item.NONE.name())) {
            Node node = (Node) this.objectsInCurrentScene.get(currentItem.name());
            node.setVisible(currentItem.hasItem());
        }
    }

    private void updateItems() {
        List<PuzzleRectangle> allRectanglesForCurrentLevel =
                this.puzzleRectangleService.getAllByLevel(this.user.getLevel());
        allRectanglesForCurrentLevel.stream()
                .filter(rectangle -> rectangle.getId() < this.user.getPuzzleRectangle().getId())
                .forEach(rectangle -> {
                    Item item = rectangle.getPuzzle().getItem();
                    this.setItem(item);
                });
    }
}
