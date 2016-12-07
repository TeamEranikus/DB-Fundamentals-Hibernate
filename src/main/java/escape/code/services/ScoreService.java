package escape.code.services;

import escape.code.models.Score;

import java.util.List;

/**
 * Keeps logic for score database and score DAO communication
 */
public interface ScoreService {

    /**
     * Creates a new Score and passes it for persistence to a DAO
     *
     * @param finishTime - total time of score
     * @param username - of user who made the score
     * @param password - of user who made the score
     */
    void createScore(long finishTime, String username, String password);

    /**
     * Deletes all scores from DB trough a DAO
     */
    void clearScores();

    /**
     * Retrieves top N scores from DB trough a DAO
     *
     * @param limit - limit for how much scores to get
     * @return - retrieved scores
     */
    List<Score> getTopScores(int limit);

    /**
     * Retrieves top 10 scores from DB trough a DAO
     *
     * @return - retrieved scores
     */
    List<Score> getTopTenScores();
}
