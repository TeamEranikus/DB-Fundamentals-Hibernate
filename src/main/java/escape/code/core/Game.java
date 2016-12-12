package escape.code.core;

import escape.code.models.entities.User;
import javafx.stage.Stage;

public interface Game {

    void initialize(Stage stage);

    void run();

    void setUser(User currentUser);

    void loadMainMenu();

}
