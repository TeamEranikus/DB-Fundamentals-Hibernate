package escape.code.services;

import escape.code.models.PuzzleRectangle;

import java.util.List;

/**
 * Keeps logic for puzzle rectangle database
 * and puzzle rectangle DAO communication
 */
public interface PuzzleRectangleService {

    /**
     * Creates a new PuzzleRectangle and passes it for persistence to a DAO
     */
    void createPuzzleRectangle(String... params);

    /**
     * Retrieves the first fount PuzzleRectangle trough a DAO
     *
     * @return first PuzzleRectangle in DB
     */
    PuzzleRectangle getFirst();

    /**
     * Retrieves all PuzzleRectangles by level trough a DAO
     *
     * @param level level from which to retrieve PuzzleRectangles
     * @return all found PuzzleRectangles in a List
     */
    List<PuzzleRectangle> getAllByLevel(int level);

    /**
     * Retrieves a PuzzleRectangle specified by id trough a DAO
     *
     * @param id the id of the wanted PuzzleRectangle
     * @return the wanted PuzzleRectangle
     */
    PuzzleRectangle getOneById(long id);
}
