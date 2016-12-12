package escape.code.core.stageManager;

import com.google.inject.Singleton;
import escape.code.utils.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Manages and loads game stages
 */
@Singleton
public class StageManagerImpl implements StageManager {

    /**
     * Loads next scene to primary stage by given stage fxml path as string
     *
     * @param currentStage - game current stage
     * @param fxmlPath     - next scene fxml path
     * @return fxml loader with next scene game objects
     */
    public FXMLLoader loadSceneToPrimaryStage(Stage currentStage, String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlPath));
        try {
            Region region = fxmlLoader.load();
            double origW = Constants.FULL_HD_WIDTH;
            double origH = Constants.FULL_HD_HIGH;

            if (region.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
                region.setPrefWidth(origW);
            } else {
                origW = region.getPrefWidth();
            }

            if (region.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
                region.setPrefHeight(origH);
            } else {
                origH = region.getPrefHeight();
            }
            Group group = new Group(region);
            StackPane rootPane = new StackPane();
            rootPane.getChildren().add(group);
            Scene scene = new Scene(rootPane, origW, origH);
            group.scaleXProperty().bind(scene.widthProperty().divide(origW));
            group.scaleYProperty().bind(scene.heightProperty().divide(origH));
            currentStage.setTitle(Constants.TITLE);
            currentStage.setScene(scene);
            scene.getRoot().requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentStage.show();
        currentStage.centerOnScreen();
        return fxmlLoader;
    }
}

