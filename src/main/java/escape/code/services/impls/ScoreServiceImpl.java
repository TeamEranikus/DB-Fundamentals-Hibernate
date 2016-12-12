package escape.code.services.impls;

import com.google.inject.Inject;
import escape.code.daos.ScoreDao;
import escape.code.models.dtos.ScoreDto;
import escape.code.models.entities.Score;
import escape.code.models.entities.User;
import escape.code.services.ScoreService;
import escape.code.services.UserService;
import escape.code.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    public ObservableList<ScoreDto> getTopScores(int limit) {
        ObservableList<ScoreDto> reslut = FXCollections.observableArrayList();
        List<Score> scores = this.scoreDao.getTopScores(limit);
        for (int i = 0; i < scores.size(); i++) {
            ScoreDto scoreDto = this.convertScoreToDto(scores.get(i), i + 1);
            reslut.add(scoreDto);
        }
        return reslut;
    }

    @Override
    public ObservableList<ScoreDto> getTopTenScores() {
        return this.getTopScores(10);
    }

    private ScoreDto convertScoreToDto(Score score, int position) {
        ScoreDto scoreDto = new ScoreDto();
        long timeInSecs = score.getFinishTime();
        int hours = (int) (timeInSecs / Constants.SECONDS_IN_HOUR);
        timeInSecs %= Constants.SECONDS_IN_HOUR;
        int mins = (int) (timeInSecs / Constants.SECONDS_IN_MINUTE);
        int secs = (int) (timeInSecs % Constants.SECONDS_IN_MINUTE);
        String time = String.format("%02d:%02d:%02d", hours, mins, secs);
        scoreDto.setTime(time);
        scoreDto.setUsername(score.getUser().getName());
        scoreDto.setPostion(position);
        return scoreDto;
    }
}
