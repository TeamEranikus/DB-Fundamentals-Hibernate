package escape.code.controllers;

import com.google.inject.Inject;
import escape.code.core.stageManager.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controls fxml file for the finish game scene
 */
public class GameFinishedController {

    @FXML
    private Button quit;

    @Inject
    private static StageManager stageManager;

    /**
     * Returns the scene to the main menu
     */
    public void quit(ActionEvent event) {
        Stage currentStage = (Stage) this.quit.getScene().getWindow();
        currentStage.close();
        Platform.exit();
        System.exit(0);
    }
}
