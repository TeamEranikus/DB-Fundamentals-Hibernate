package escape.code.services.impls;

import com.google.inject.Inject;
import escape.code.daos.ScoreDao;
import escape.code.models.Score;
import escape.code.models.User;
import escape.code.services.ScoreService;
import escape.code.services.UserService;

import java.util.List;


public class ScoreServiceImpl implements ScoreService {

    private final ScoreDao scoreDao;
    private final UserService userService;

    @Inject
    public ScoreServiceImpl(ScoreDao scoreDao, UserService userService) {
        this.scoreDao = scoreDao;
        this.userService = userService;
    }

    @Override
    public void createScore(long finishTime, String username, String password) {
        User user = this.userService.getUser(username, password);
        Score score = new Score();
        score.setFinishTime(finishTime);
        score.setUser(user);
        this.scoreDao.persist(score);
    }

    @Override
    public void clearScores() {
        this.scoreDao.clear();
    }

    @Override
    public List<Score> getTopScores(int limit) {
        return this.scoreDao.getTopScores(limit);
    }

    @Override
    public List<Score> getTopTenScores() {
        return this.scoreDao.getTopScores(10);
    }
}
