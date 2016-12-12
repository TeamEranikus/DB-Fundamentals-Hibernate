package escape.code.services.impls;

import com.google.inject.Inject;
import escape.code.daos.PuzzleRectangleDao;
import escape.code.models.dtos.PuzzleRectangleDto;
import escape.code.models.entities.Puzzle;
import escape.code.models.entities.PuzzleRectangle;
import escape.code.services.PuzzleRectangleService;
import escape.code.services.PuzzleService;

import java.util.List;

public class PuzzleRectangleServiceImpl implements PuzzleRectangleService {

    private final PuzzleRectangleDao puzzleRectangleDao;
    private final PuzzleService puzzleService;

    @Inject
    public PuzzleRectangleServiceImpl(PuzzleRectangleDao puzzleRectangleDao, PuzzleService puzzleService) {
        this.puzzleRectangleDao = puzzleRectangleDao;
        this.puzzleService = puzzleService;
    }

    @Override
    public void createPuzzleRectangle(PuzzleRectangleDto puzzleRectangleDto) {
        PuzzleRectangle puzzleRectangle = new PuzzleRectangle();
        puzzleRectangle.setName(puzzleRectangleDto.getName());
        puzzleRectangle.setLevel(puzzleRectangleDto.getLevel());
        Puzzle puzzle = this.puzzleService.getOneById(puzzleRectangleDto.getPuzzleId());
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
