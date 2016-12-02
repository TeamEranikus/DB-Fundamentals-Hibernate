package escape.code.services;

import escape.code.models.User;

/**
 * Keeps logic for user database and user DAO communication
 */
public interface UserService {

    /**
     * Creates a new User and passes it for persistence to a DAO
     */
    void createUser(String username, String password);

    /**
     * Updates a specified user trough a DAO
     *
     * @param user - the user to be updated
     */
    void updateUser(User user);

    /**
     * Retrieves a user trough a DAO
     *
     * @param username of wanted User
     * @param password of wanted User
     * @return the specified User
     */
    User getUser(String username, String password);
}
