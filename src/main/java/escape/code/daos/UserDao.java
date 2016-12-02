package escape.code.daos;

import escape.code.models.User;

/**
 * Data access object responsible for connection with user database
 */
public interface UserDao {

    /**
     * Gets current logged in user by given username and password
     *
     * @param username - tipped username
     * @param password - tipped password
     * @return - corresponding user
     * @throws IllegalArgumentException when username is null or password is incorrect
     */
    User getLogedUser(String username, String password);

    /**
     * Persists new registered user in DB
     *
     * @param user - registered user
     * @throws IllegalArgumentException when username is already taken
     */
    void create(User user);

    /**
     * Updates user's status
     *
     * @param user - current logged in user
     */
    void updateUser(User user);
}
