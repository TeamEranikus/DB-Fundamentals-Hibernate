package escape.code.models.dtos;

import java.io.Serializable;

/**
 * Created by Todor Ilchev on 2016-12-12.
 */
public class PuzzleRectangleDto implements Serializable {

    private String name;
    private Integer level;
    private Long puzzleId;

    public PuzzleRectangleDto() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPuzzleId() {
        return this.puzzleId;
    }

    public void setPuzzleId(Long puzzleId) {
        this.puzzleId = puzzleId;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
