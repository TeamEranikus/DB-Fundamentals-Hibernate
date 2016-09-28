package escape.code.daos.puzzleDAO;

import escape.code.models.Puzzle;

import java.util.List;

public interface PuzzleDao {

    List<Puzzle> getAllByLevel(int level);

    void createPuzzle(Puzzle puzzle);

    Puzzle getOneById(long id);
}
