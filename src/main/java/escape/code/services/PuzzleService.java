package escape.code.services;

import escape.code.models.entities.Puzzle;
import java.util.List;

/**
 * Keeps logic for puzzle database and puzzle DAO communication
 */
public interface PuzzleService {

    /**
     * Creates a new Puzzle and passes it for persistence to a DAO
     */
    void createPuzzle(String... params);

    /**
     * Retrieves all Puzzles by level trough a DAO
     *
     * @param level level from which to retrieve Puzzles
     * @return all found Puzzles in a List
     */
    List<Puzzle> getAllByLevel(int level);

    /**
     * Retrieves a Puzzle specified by id trough a DAO
     *
     * @param id the id of the wanted Puzzle
     * @return the wanted Puzzle
     */
    Puzzle getOneById(long id);
}
