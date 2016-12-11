package escape.code.controllers;

import escape.code.models.entities.Puzzle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls fxml file for current puzzle
 */
public class PuzzleController implements Initializable {

    private static final double IMAGE_RESIZE_COEFFICIENT = 2D;

    @FXML
    private Button hintButton;

    @FXML
    private Label hintText;

    @FXML
    private Label description;

    @FXML
    private Label nextClue;

    @FXML
    private ImageView puzzleImage;

    @FXML
    private TextField userAnswer;

    @FXML
    private Button answerButton;

    private static Puzzle puzzle;

    /**
     * Sets executing puzzle
     *
     * @param puzzleToSet current puzzle
     */
    public static void setPuzzle(Puzzle puzzleToSet) {
        puzzle = puzzleToSet;
    }

    /**
     * Initialize current puzzle params
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.puzzleImage.setImage(new Image(puzzle.getImagePath()));
        this.centerImage();
        this.hintText.setText(puzzle.getHint());
        this.description.setText(puzzle.getQuestion());
        this.nextClue.setText("Incorrect answer!");
    }

    /**
     * Sets puzzle hint to visible
     *
     * @param actionEvent
     */
    public void giveHint(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.hintButton) {
            this.hintText.setVisible(true);
        }
    }

    /**
     * Checks the given answer and sets next puzzle clue to visible if answer is correct
     *
     * @param actionEvent
     */
    public void checkAnswer(ActionEvent actionEvent) {
        String userAnswerString = this.userAnswer.getText().toLowerCase().trim();
        if (puzzle.checkCorrectAnswer(userAnswerString)) {
            this.nextClue.setText(puzzle.getNextClue());
            this.nextClue.setVisible(true);
            puzzle.setAnswerGiven(true);
            this.answerButton.setDisable(true);
        } else {
            this.nextClue.setVisible(true);
        }
    }

    /**
     * Centers current puzzle image
     */
    private void centerImage() {
        Image image = this.puzzleImage.getImage();

        if (image != null) {
            double width = 0D;
            double height = 0D;

            double ratioX = this.puzzleImage.getFitWidth() / image.getWidth();
            double ratioY = this.puzzleImage.getFitHeight() / image.getHeight();

            double reduceCoeff = 0D;
            if (ratioX >= ratioY) {
                reduceCoeff = ratioY;
            } else {
                reduceCoeff = ratioX;
            }

            width = image.getWidth() * reduceCoeff;
            height = image.getHeight() * reduceCoeff;

            this.puzzleImage.setX((this.puzzleImage.getFitWidth() - width) / IMAGE_RESIZE_COEFFICIENT);
            this.puzzleImage.setY((this.puzzleImage.getFitHeight() - height) / IMAGE_RESIZE_COEFFICIENT);
        }
    }
}
