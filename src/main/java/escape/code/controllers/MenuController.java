package escape.code.controllers;

import com.google.inject.Inject;
import escape.code.core.game.Game;
import escape.code.core.stageManager.StageManager;
import escape.code.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls fxml file for the game main menu scene
 */
public class MenuController {

    @FXML
    private Button howToPlayButton;

    @FXML
    private Button quitButton;

    private Stage currentStage;

    @Inject
    private static StageManager stageManager;

    @Inject
    private static Game game;

    /**
     * Sets up current game level scene
     */
    public void onNewGameClicked(ActionEvent event) throws IOException {
        game.run();
    }

    /**
     * Sets up how to play scene
     */
    public void onHowToPlayClicked(ActionEvent event) throws IOException {
        this.currentStage = (Stage) this.howToPlayButton.getScene().getWindow();
        stageManager.loadSceneToPrimaryStage(this.currentStage, Constants.HOW_TO_PLAY_FXML_PATH);
    }

    /**
     * Exits the game
     */
    public void onQuitClicked(ActionEvent event) {
        this.currentStage = (Stage) this.quitButton.getScene().getWindow();
        this.currentStage.close();
        System.exit(0);
    }

    public void onRankingClicked(ActionEvent event) {
        this.currentStage = (Stage) this.howToPlayButton.getScene().getWindow();
        stageManager.loadSceneToPrimaryStage(this.currentStage, Constants.RANKING_FXML_PATH);
    }
}
