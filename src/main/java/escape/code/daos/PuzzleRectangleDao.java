package escape.code.daos;

import escape.code.models.PuzzleRectangle;

import java.util.List;

/**
 * Data access object responsible for connection with puzzle rectangle database
 */
public interface PuzzleRectangleDao {

    /**
     * Persists puzzle rectangles in DB
     *
     * @param currentRectangle - PuzzleRectangle to be persisted in DB
     */
    void createPuzzleRectangle(PuzzleRectangle currentRectangle);

    /**
     * Gets first puzzle rectangle
     *
     * @return first found PuzzleRectangle
     */
    PuzzleRectangle getFirst();

    /**
     * Gets all puzzle rectangles for current level
     *
     * @param level - current level
     * @return all puzzle rectangles for current level
     */
    List<PuzzleRectangle> getAllPuzzleRectangleByLevel(int level);

    /**
     * Gets puzzle rectangle by given id
     *
     * @param id - puzzle rectangle id
     * @return - corresponding puzzle rectangle
     */
    PuzzleRectangle getOneById(long id);
}
