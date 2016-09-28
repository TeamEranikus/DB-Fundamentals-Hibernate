package escape.code.daos.userDAO;

import escape.code.models.User;

public interface UserDao {

    User getLogedUser(String username, String password);

    void create(User user);

    void updateUser(User user);
}
