package escape.code.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Sets up user database
 */
@Entity
@Table(name = "users")
public class User implements Serializable{

    private String name;
    private String password;
    private Long id;
    private int level;
    private PuzzleRectangle puzzleRectangle;

    public User(){
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_rectangle", nullable = true)
    public PuzzleRectangle getPuzzleRectangle() {
        return this.puzzleRectangle;
    }

    public void setPuzzleRectangle(PuzzleRectangle puzzleRectangle) {
        this.puzzleRectangle = puzzleRectangle;
    }
}
