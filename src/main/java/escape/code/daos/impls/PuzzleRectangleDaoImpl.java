package escape.code.daos.impls;

import com.google.inject.Inject;
import escape.code.daos.PuzzleRectangleDao;
import escape.code.models.entities.PuzzleRectangle;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Data access object responsible for connection with puzzle rectangle database
 */
public class PuzzleRectangleDaoImpl implements PuzzleRectangleDao {

    @Inject
    private EntityManager entityManager;

    /**
     * Persists puzzle rectangles in DB
     *
     * @param currentRectangle - PuzzleRectangle to be persisted in DB
     */
    @Override
    public void createPuzzleRectangle(PuzzleRectangle currentRectangle) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(currentRectangle);
        this.entityManager.getTransaction().commit();
    }

    /**
     * Gets first puzzle rectangle
     *
     * @return first found PuzzleRectangle
     */
    @Override
    @SuppressWarnings("unchecked")
    public PuzzleRectangle getFirst() {
        List<PuzzleRectangle> rectangles = this.entityManager
                .createQuery("SELECT rectangle FROM PuzzleRectangle AS rectangle WHERE rectangle.id=:id")
                .setParameter("id", 1L)
                .getResultList();
        return rectangles.get(0);
    }

    /**
     * Gets all puzzle rectangles for current level
     *
     * @param level - current level
     * @return all puzzle rectangles for current level
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PuzzleRectangle> getAllPuzzleRectangleByLevel(int level) {
        List<PuzzleRectangle> rectangles = this.entityManager
                .createQuery("SELECT rectangle FROM PuzzleRectangle AS rectangle WHERE rectangle.level=:level")
                .setParameter("level", level)
                .getResultList();
        return rectangles;
    }

    /**
     * Gets puzzle rectangle by given id
     *
     * @param id - puzzle rectangle id
     * @return - corresponding puzzle rectangle
     */
    @Override
    @SuppressWarnings("unchecked")
    public PuzzleRectangle getOneById(long id) {
        List<PuzzleRectangle> rectangles = this.entityManager
                .createQuery("SELECT rectangle FROM PuzzleRectangle AS rectangle WHERE rectangle.id=:id")
                .setParameter("id", id)
                .getResultList();
        return rectangles.get(0);
    }
}
