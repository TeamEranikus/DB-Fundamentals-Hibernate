package escape.code.daos.puzzleDAO;

import com.google.inject.Inject;
import escape.code.models.Puzzle;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Data access object responsible for connection with puzzle database
 */
public class PuzzleDaoImpl implements PuzzleDao {

    @Inject
    private EntityManager entityManager;

    /**
     * Gets all puzzles in current level
     * @param level - current game level
     * @return - current level puzzle list
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Puzzle> getAllByLevel(int level) {
      return this.entityManager
                .createQuery("SELECT puzzle FROM Puzzle AS puzzle WHERE puzzle.level=:level")
                .setParameter("level",level)
                .getResultList();
    }

    /**
     * Creates current puzzle
     * @param puzzle
     */
    @Override
    public void createPuzzle(Puzzle puzzle) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(puzzle);
        this.entityManager.getTransaction().commit();
    }

    /**
     * Gets puzzle from database by given id
     * @param id - current puzzle id
     * @return - corresponding puzzle
     */
    @Override
    @SuppressWarnings("unchecked")
    public Puzzle getOneById(long id) {
        List<Puzzle> allPuzzle = this.entityManager
                .createQuery("SELECT puzzle FROM Puzzle AS puzzle WHERE puzzle.id=:id")
                .setParameter("id",id)
                .getResultList();
        return allPuzzle.get(0);
    }
}
