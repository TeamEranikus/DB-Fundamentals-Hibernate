package escape.code.models.sprites;

import escape.code.core.ResizableCanvas;
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
     * Update sprites coordinates on movement
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
     * Gets sprites image view
     *
     * @return - image view
     */
    ImageView getImageView();

    /**
     * Set player image
     * @param imageView
     */
    void setImageView(ImageView imageView);

    /**
     * Set current player canvas
     * @param currentCanvas
     */
    void setCurrentCanvas(ResizableCanvas currentCanvas);
}
