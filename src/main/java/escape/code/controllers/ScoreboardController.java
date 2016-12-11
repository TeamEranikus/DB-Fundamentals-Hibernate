package escape.code.controllers;

import com.google.inject.Inject;
import escape.code.core.StageManager;
import escape.code.models.dtos.ScoreDto;
import escape.code.services.ScoreService;
import escape.code.utils.Constants;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls fxml file for the how to play scene
 */
public class ScoreboardController implements Initializable {

    @FXML
    private TableView<ScoreDto> scoreboardTable;

    @FXML
    private TableColumn<ScoreDto, Integer> numberColumn;

    @FXML
    private TableColumn<ScoreDto, String> usernameColumn;

    @FXML
    private TableColumn<ScoreDto, String> timeColumn;

    @FXML
    private Button backToGame;

    @Inject
    private static StageManager stageManager;

    @Inject
    private static ScoreService scoreService;

    /**
     * Returns the scene to the main menu
     *
     * @param event
     * @throws IOException
     */
    public void backToMenu(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) this.backToGame.getScene().getWindow();
        stageManager.loadSceneToPrimaryStage(currentStage, Constants.MENU_FXML_PATH);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<ScoreDto> scores = scoreService.getTopTenScores();
        this.numberColumn.setCellValueFactory(new PropertyValueFactory<ScoreDto, Integer>("postion"));
        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<ScoreDto, String>("username"));
        this.timeColumn.setCellValueFactory(new PropertyValueFactory<ScoreDto, String>("time"));
        this.scoreboardTable.setItems(scores);
    }
}
