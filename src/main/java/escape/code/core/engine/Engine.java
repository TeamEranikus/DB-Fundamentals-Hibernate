package escape.code.core.engine;

import escape.code.models.entities.User;
import javafx.fxml.FXMLLoader;

/**
 * Keeps the logic for the running level scene
 * Run by Game class
 */
public interface Engine {

    void play() throws IllegalStateException;

    void setLoader(FXMLLoader loader);

    void initialize();

    void setUser(User user);
}
