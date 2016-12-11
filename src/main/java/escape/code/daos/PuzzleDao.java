package escape.code.daos;

import escape.code.models.entities.Puzzle;

import java.util.List;

/**
 * Data access object responsible for connection with puzzle database
 */
public interface PuzzleDao {

    /**
     * Gets all puzzles in current level
     *
     * @param level - current game level
     * @return - current level puzzle list
     */
    List<Puzzle> getAllByLevel(int level);

    /**
     * Persists current Puzzle in DB
     *
     * @param puzzle Puzzle to be persisted in DB
     */
    void createPuzzle(Puzzle puzzle);

    /**
     * Gets puzzle from database by given id
     *
     * @param id - current puzzle id
     * @return - corresponding puzzle
     */
    Puzzle getOneById(long id);
}
