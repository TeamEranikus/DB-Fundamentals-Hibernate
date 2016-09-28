package escape.code.daos.puzzleRectangleDAO;

import escape.code.models.PuzzleRectangle;

import java.util.List;

public interface PuzzleRectangleDao {

    void createPuzzleRectangle(PuzzleRectangle currentRectangle);

    PuzzleRectangle getFirst();

    List<PuzzleRectangle> getAllPuzzleRectangleByLevel(int level);

    PuzzleRectangle getOneById(long id);
}
