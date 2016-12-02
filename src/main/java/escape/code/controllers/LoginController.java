package escape.code.controllers;

import com.google.inject.Inject;
import escape.code.core.Game;
import escape.code.models.User;
import escape.code.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controls the fxml file for the login scene
 */
public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label messageLabel;

    @Inject
    private static UserService userService;

    /**
     * Logged in the current user by given username and password
     *
     * @param actionEvent
     */
    public void login(ActionEvent actionEvent) {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        try {
            this.checkForEmptyString(username, password);
            User user = userService.getUser(username, password);
            Game.setUser(user);
            Game.loadMainMenu();
        } catch (IllegalArgumentException exception) {
            this.messageLabel.setText(exception.getMessage());
        }
    }

    /**
     * Register the new user by given username and password
     *
     * @param actionEvent
     */
    public void register(ActionEvent actionEvent) {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        try {
            this.checkForEmptyString(username, password);
            userService.createUser(username, password);
            this.login(actionEvent);
        } catch (IllegalArgumentException exception) {
            this.messageLabel.setText(exception.getMessage());
        }
    }

    /**
     * Validates user input
     *
     * @param username - tipped username
     * @param password - tipped password
     * @throws IllegalArgumentException when tipped input is empty or white space
     */
    private void checkForEmptyString(String username, String password) {
        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        if (password.trim().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
    }
}

