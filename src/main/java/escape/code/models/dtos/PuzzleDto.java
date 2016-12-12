package escape.code.models.dtos;

import java.io.Serializable;

/**
 * Created by Todor Ilchev on 2016-12-12.
 */
public class PuzzleDto implements Serializable {

    private String question;
    private String correctAnswer;
    private String hint;
    private String nextClue;
    private String imagePath;
    private Integer level;
    private String item;

    public PuzzleDto() {
        super();
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getNextClue() {
        return this.nextClue;
    }

    public void setNextClue(String nextClue) {
        this.nextClue = nextClue;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
