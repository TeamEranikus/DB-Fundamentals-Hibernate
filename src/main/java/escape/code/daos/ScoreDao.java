package escape.code.daos;

import escape.code.models.Score;

import java.util.List;

/**
 * Data access object responsible for connection with score database
 */
public interface ScoreDao {

    /**
     * Persists new score in DB
     *
     * @param score - new score
     */
    void persist(Score score);

    /**
     * Deletes all scores from DB
     */
    void clear();

    /**
     * Retrieves top N scores from DB
     *
     * @param limit - limit for how much scores to get
     * @return - retrieved scores
     */
    List<Score> getTopScores(int limit);
}
