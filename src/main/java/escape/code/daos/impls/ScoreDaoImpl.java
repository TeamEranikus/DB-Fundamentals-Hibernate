package escape.code.daos.impls;

import com.google.inject.Inject;
import escape.code.daos.ScoreDao;
import escape.code.models.Score;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Data access object responsible for connection with score database
 */
public class ScoreDaoImpl implements ScoreDao {

    @Inject
    private EntityManager entityManager;

    /**
     * Persists new score in DB
     *
     * @param score - new score
     */
    @Override
    public void persist(Score score) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(score);
        this.entityManager.getTransaction().commit();
    }

    /**
     * Deletes all scores from DB
     */
    @Override
    public void clear() {
        this.entityManager.getTransaction().begin();
        this.entityManager
                .createQuery("DELETE FROM Score")
                .executeUpdate();
        this.entityManager.getTransaction().commit();
    }

    /**
     * Retrieves top N scores from DB
     *
     * @param limit - limit for how much scores to get
     * @return - retrieved scores
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Score> getTopScores(int limit) {
        return this.entityManager
                .createQuery("SELECT s FROM Score AS s")
                .setMaxResults(limit)
                .getResultList();
    }
}
