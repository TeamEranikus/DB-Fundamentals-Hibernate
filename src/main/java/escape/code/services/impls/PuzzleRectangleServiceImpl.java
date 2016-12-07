package escape.code.services.impls;

import com.google.inject.Inject;
import escape.code.daos.PuzzleRectangleDao;
import escape.code.models.Puzzle;
import escape.code.models.PuzzleRectangle;
import escape.code.services.PuzzleRectangleService;
import escape.code.services.PuzzleService;

import java.util.List;

public class PuzzleRectangleServiceImpl implements PuzzleRectangleService {

    private static final int PUZZLE_RECTANGLE_NAME_INDEX = 0;
    private static final int PUZZLE_RECTANGLE_LEVEL_INDEX = 1;
    private static final int PUZZLE_RECTANGLE_CORESPONDING_PUZZLE_INDEX = 2;

    private final PuzzleRectangleDao puzzleRectangleDao;
    private final PuzzleService puzzleService;

    @Inject
    public PuzzleRectangleServiceImpl(PuzzleRectangleDao puzzleRectangleDao, PuzzleService puzzleService) {
        this.puzzleRectangleDao = puzzleRectangleDao;
        this.puzzleService = puzzleService;
    }

    @Override
    public void createPuzzleRectangle(String... params) {
        PuzzleRectangle puzzleRectangle = new PuzzleRectangle();
        puzzleRectangle.setName(params[PUZZLE_RECTANGLE_NAME_INDEX]);
        puzzleRectangle.setLevel(Integer.parseInt(params[PUZZLE_RECTANGLE_LEVEL_INDEX]));
        Puzzle puzzle = this.puzzleService
                .getOneById(Long.parseLong(params[PUZZLE_RECTANGLE_CORESPONDING_PUZZLE_INDEX]));
        puzzleRectangle.setPuzzle(puzzle);
        this.puzzleRectangleDao.createPuzzleRectangle(puzzleRectangle);
    }

    @Override
    public PuzzleRectangle getFirst() {
        return this.puzzleRectangleDao.getFirst();
    }

    @Override
    public List<PuzzleRectangle> getAllByLevel(int level) {
        return this.puzzleRectangleDao.getAllPuzzleRectangleByLevel(level);
    }

    @Override
    public PuzzleRectangle getOneById(long id) {
        return this.puzzleRectangleDao.getOneById(id);
    }
}
