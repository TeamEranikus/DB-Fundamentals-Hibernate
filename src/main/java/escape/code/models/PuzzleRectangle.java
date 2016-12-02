package escape.code.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Sets up puzzles rectangles database
 */
@Entity
@Table(name = "puzzles_rectangles")
public class PuzzleRectangle {

    private Long id;
    private String name;
    private Puzzle puzzle;
    private int level;

    public PuzzleRectangle() {
        super();
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id",nullable = false)
    public Puzzle getPuzzle() {
        return this.puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}
