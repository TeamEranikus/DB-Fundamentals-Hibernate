package escape.code.controllers;

import com.google.inject.Inject;
import escape.code.core.stageManager.StageManager;
import escape.code.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controls fxml file for the finish game scene
 */
public class GameFinishedController {

    @FXML
    private Button backToGame;

    @Inject
    private static StageManager stageManager;

    /**
     * Returns the scene to the main menu
     */
    public void backToMenu(ActionEvent event) {
        Stage currentStage = (Stage) this.backToGame.getScene().getWindow();
        stageManager.loadSceneToPrimaryStage(currentStage, Constants.MENU_FXML_PATH);
    }
}
