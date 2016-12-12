package escape.code.core.game;

import escape.code.models.entities.User;
import javafx.stage.Stage;

public interface Game {

    void initialize(Stage stage);

    void run();

    void setUser(User currentUser);

    void loadMainMenu();

}
