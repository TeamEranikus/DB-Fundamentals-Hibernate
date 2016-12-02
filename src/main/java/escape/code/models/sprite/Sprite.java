package escape.code.models.sprite;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Keeps logic for player game object
 */
public interface Sprite {

    /**
     * Update sprite coordinates on movement
     *
     * @param keys          - pressed keys
     * @param rectCollision - level rectangle collisions
     */
    void updateCoordinates(HashMap<KeyCode, Boolean> keys, ArrayList<Rectangle> rectCollision);

    /**
     * Checks for puzzle collision with detected rectangle
     *
     * @param current - current rectangle
     * @return - if collision is detected
     */
    boolean checkForCol(Rectangle current);

    /**
     * Gets sprite image view
     *
     * @return - image view
     */
    ImageView getImageView();
}
