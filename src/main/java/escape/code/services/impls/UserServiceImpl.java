package escape.code.services.impls;

import com.google.inject.Inject;
import escape.code.daos.UserDao;
import escape.code.models.entities.PuzzleRectangle;
import escape.code.models.entities.User;
import escape.code.services.PuzzleRectangleService;
import escape.code.services.UserService;

public class UserServiceImpl implements UserService {

    private static final int DEFAULT_START_LEVEL = 0;

    private final UserDao userDao;
    private final PuzzleRectangleService puzzleRectangleService;

    @Inject
    public UserServiceImpl(UserDao userDao, PuzzleRectangleService puzzleRectangleService) {
        this.userDao = userDao;
        this.puzzleRectangleService = puzzleRectangleService;
    }

    @Override
    public void createUser(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setLevel(DEFAULT_START_LEVEL);
        PuzzleRectangle puzzleRectangle = this.puzzleRectangleService.getFirst();
        user.setPuzzleRectangle(puzzleRectangle);
        this.userDao.create(user);
    }

    @Override
    public void resetUser(User user) {
        user.setPuzzleRectangle(this.puzzleRectangleService.getFirst());
        user.setCurrentTime(DEFAULT_START_LEVEL);
        user.setLevel(DEFAULT_START_LEVEL);
        this.userDao.updateUser(user);
    }

    @Override
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @Override
    public User getUser(String username, String password) {
        return this.userDao.getLogedUser(username, password);
    }
}
