package escape.code.services.puzzleService;

import com.google.inject.Inject;
import escape.code.daos.puzzleDAO.PuzzleDao;
import escape.code.enums.Item;
import escape.code.models.Puzzle;
import java.util.List;

/**
 * Keeps logic for puzzle database and puzzle DAO communication
 */
public class PuzzleServiceImpl implements PuzzleService {
    private static final int PUZZLE_QUESTION_INDEX = 0;
    private static final int PUZZLE_CORRECT_ANSWER_INDEX = 1;
    private static final int PUZZLE_HINT_INDEX = 2;
    private static final int PUZZLE_NEXT_CLUE_INDEX = 3;
    private static final int PUZZLE_IMAGE_PATH_INDEX = 4;
    private static final int PUZZLE_CORRESPONDING_LEVEL_INDEX = 5;
    private static final int PUZZLE_CORRESPONDING_ITEM_INDEX = 6;

    private PuzzleDao puzzleDao;

    @Inject
    public PuzzleServiceImpl(PuzzleDao puzzleDao){
        this.puzzleDao = puzzleDao;
    }

    @Override
    public void createPuzzle(String... params) {
        Puzzle puzzle = new Puzzle();
        puzzle.setQuestion(params[PUZZLE_QUESTION_INDEX]);
        puzzle.setCorrectAnswer(params[PUZZLE_CORRECT_ANSWER_INDEX]);
        puzzle.setHint(params[PUZZLE_HINT_INDEX]);
        puzzle.setNextClue(params[PUZZLE_NEXT_CLUE_INDEX]);
        puzzle.setImagePath(params[PUZZLE_IMAGE_PATH_INDEX]);
        puzzle.setLevel(Integer.parseInt(params[PUZZLE_CORRESPONDING_LEVEL_INDEX]));
        puzzle.setItem(Item.valueOf(params[PUZZLE_CORRESPONDING_ITEM_INDEX].toUpperCase()));
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
