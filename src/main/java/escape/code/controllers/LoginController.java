package escape.code.controllers;

import com.google.inject.Inject;
import escape.code.core.game.Game;
import escape.code.models.entities.User;
import escape.code.services.UserService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    @Inject
    private static Game game;

    /**
     * Logged in the current user by given username and password
     *
     * @param actionEvent
     */
    public void login(ActionEvent actionEvent) {
        String username = this.usernameField.getText();
        String password = this.encryptPassword(this.passwordField.getText());
        try {
            this.checkForEmptyString(username, password);
            User user = userService.getUser(username, password);
            game.setUser(user);
            game.loadMainMenu();
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
        String password = this.encryptPassword(this.passwordField.getText());
        try {
            this.checkForEmptyString(username, password);
            userService.createUser(username, password);
            this.login(actionEvent);
        } catch (IllegalArgumentException exception) {
            this.messageLabel.setText(exception.getMessage());
        }
    }

    private String encryptPassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
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

    public void onQuitClicked(ActionEvent actionEvent) {
        Stage currentStage = (Stage) this.usernameField.getScene().getWindow();
        currentStage.close();
        Platform.exit();
        System.exit(0);
    }
}
