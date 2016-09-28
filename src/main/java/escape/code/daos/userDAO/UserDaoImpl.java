package escape.code.daos.userDAO;

import com.google.inject.Inject;
import escape.code.models.User;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Data access object responsible for connection with user database
 */
public class UserDaoImpl implements UserDao{

    @Inject
    private EntityManager entityManager;

    /**
     * Gets current logged in user by given username and password
     * @param username - tipped username
     * @param password - tipped password
     * @throws IllegalArgumentException when username is null or password is incorrect
     * @return - corresponding user
     */
    @Override
    public User getLogedUser(String username, String password) {
        User user = this.getUserByName(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found!");
        } else if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Password is incorrect!");
        }
        return user;
    }

    /**
     * Creates new registered user
     * @throws IllegalArgumentException when username is already taken
     * @param user - registered user
     */
    public void create(User user) {
        User currentUser = this.getUserByName(user.getName());
        if(currentUser != null){
            throw new IllegalArgumentException("User already exist!");
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();
    }

    /**
     * Updates user's status
     * @param user - current logged in user
     */
    @Override
    public void updateUser(User user) {
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(user);
        this.entityManager.getTransaction().commit();
    }

    /**
     * Gets user from database by given username
     * @param userName - given username as string
     * @return - corresponding user or null if there in no registered user with that username
     */
    @SuppressWarnings("unchecked")
    private User getUserByName(String userName) {
        Query query = this.entityManager.createQuery("SELECT user FROM User AS user WHERE user.name=:name");
        query.setParameter("name", userName);
        List<User> users = query.getResultList();
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }
}
