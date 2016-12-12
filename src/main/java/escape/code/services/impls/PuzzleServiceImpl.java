package escape.code.services.impls;

import com.google.inject.Inject;
import escape.code.daos.PuzzleDao;
import escape.code.enums.Item;
import escape.code.models.dtos.PuzzleDto;
import escape.code.models.entities.Puzzle;
import escape.code.services.PuzzleService;

import java.util.List;

public class PuzzleServiceImpl implements PuzzleService {

    private final PuzzleDao puzzleDao;

    @Inject
    public PuzzleServiceImpl(PuzzleDao puzzleDao) {
        this.puzzleDao = puzzleDao;
    }

    @Override
    public void createPuzzle(PuzzleDto puzzleDto) {
        Puzzle puzzle = new Puzzle();
        puzzle.setQuestion(puzzleDto.getQuestion());
        puzzle.setCorrectAnswer(puzzleDto.getCorrectAnswer());
        puzzle.setHint(puzzleDto.getHint());
        puzzle.setNextClue(puzzleDto.getNextClue());
        puzzle.setImagePath(puzzleDto.getImagePath());
        puzzle.setLevel(puzzleDto.getLevel());
        puzzle.setItem(Item.valueOf(puzzleDto.getItem().toUpperCase()));
        this.puzzleDao.createPuzzle(puzzle);
    }

    @Override
    public List<Puzzle> getAllByLevel(int level) {
        return this.puzzleDao.getAllByLevel(level);
    }

    @Override
    public Puzzle getOneById(long id) {
        return this.puzzleDao.getOneById(id);
    }
}
