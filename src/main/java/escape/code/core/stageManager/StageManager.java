package escape.code.core.stageManager;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Keeps the logic for loading scenes
 *
 */
public interface StageManager {

    /**
     * Loads a scene to a stage
     *
     * @param currentStage - application current stage
     * @param fxmlPath - path to FXML file that describes the scene to be loaded
     * @return - FXMLLoader
     */
    FXMLLoader loadSceneToStage(Stage currentStage, String fxmlPath);
}
