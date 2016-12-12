package escape.code.core.stageManager;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public interface StageManager {

    FXMLLoader loadSceneToPrimaryStage(Stage currentStage, String fxmlPath);
}
