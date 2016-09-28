package escape.code.models;

import escape.code.enums.Item;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Sets up puzzle database
 */
@Entity
@Table(name = "puzzles")
public class Puzzle implements Serializable {

    private Long id;
    private String question;
    private String correctAnswer;
    private String hint;
    private String imagePath;
    private String nextClue;
    private int level;
    private Item item;
    private PuzzleRectangle rectangle;
    private boolean isAnswerGiven;

    public Puzzle() {
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    @Id
    @GenericGenerator(name="incrementer" , strategy="increment")
    @GeneratedValue(generator="incrementer")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean checkCorrectAnswer(String answer) {
        if (answer.equals(getCorrectAnswer())) {
            return true;
        }
        return false;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "puzzle")
    public PuzzleRectangle getRectangle() {
        return this.rectangle;
    }

    public void setRectangle(PuzzleRectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Transient
    public boolean isAnswerGiven() {
        return this.isAnswerGiven;
    }

    public void setAnswerGiven(boolean answerGiven) {
        isAnswerGiven = answerGiven;
    }
}

