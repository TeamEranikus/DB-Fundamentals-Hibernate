package escape.code.models.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Sets up user database
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private String name;
    private String password;
    private Long id;
    private int level;
    private PuzzleRectangle puzzleRectangle;
    private long currentTime = 0;
    private Set<Score> scores;

    public User() {
        super();
    }

    @Id
    @GenericGenerator(name = "incrementer", strategy = "increment")
    @GeneratedValue(generator = "incrementer")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "level", nullable = false)
    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_rectangle_id", nullable = true)
    public PuzzleRectangle getPuzzleRectangle() {
        return this.puzzleRectangle;
    }

    public void setPuzzleRectangle(PuzzleRectangle puzzleRectangle) {
        this.puzzleRectangle = puzzleRectangle;
    }

    @Basic
//    @Column(name = "current_time", columnDefinition = "bigint default '0'")
    public long getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",
            fetch = FetchType.LAZY, targetEntity = Score.class)
    public Set<Score> getScores() {
        if (this.scores == null) {
            this.setScores(new HashSet<>());
        }
        return this.scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public void addScore(Score score) {
        this.getScores().add(score);
    }
}
