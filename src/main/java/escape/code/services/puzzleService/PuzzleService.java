package escape.code.services.puzzleService;

import escape.code.models.Puzzle;
import java.util.List;

public interface PuzzleService {

    void createPuzzle(String... params);

    List<Puzzle> getAllByLevel(int level);

    Puzzle getOneById(long id);
}
