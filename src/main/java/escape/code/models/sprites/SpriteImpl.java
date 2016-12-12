package escape.code.models.sprites;

import escape.code.core.ResizableCanvas;
import escape.code.utils.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Keeps logic for player game object
 */
public class SpriteImpl implements Sprite {

    private final Image RIGHT_IMAGE_VIEW =
            new Image(this.getClass().getResource(Constants.SPRITE_IMAGE_RIGHT_PATH).toExternalForm());
    private final Image LEFT_IMAGE_VIEW =
            new Image(this.getClass().getResource(Constants.SPRITE_IMAGE_LEFT_PATH).toExternalForm());
    private static final int SPRITE_MOVE_STEP_INCREMENT = 2;
    private static final int SPRITE_MOVE_STEP_DECREMENT = -2;

    private ImageView imageView;
    private ResizableCanvas currentCanvas;

    public SpriteImpl() {
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setCurrentCanvas(ResizableCanvas currentCanvas) {
        this.currentCanvas = currentCanvas;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public void updateCoordinates(HashMap<KeyCode, Boolean> keys, ArrayList<Rectangle> rectCollision) {
        if (this.isPressed(KeyCode.UP, keys)) {
            this.moveY(SPRITE_MOVE_STEP_DECREMENT);
            this.checkBounds("U", rectCollision);
        } else if (this.isPressed(KeyCode.DOWN, keys)) {
            this.moveY(SPRITE_MOVE_STEP_INCREMENT);
            this.checkBounds("D", rectCollision);
        } else if (this.isPressed(KeyCode.RIGHT, keys)) {
            this.moveX(SPRITE_MOVE_STEP_INCREMENT);
            this.checkBounds("R", rectCollision);
        } else if (this.isPressed(KeyCode.LEFT, keys)) {
            this.moveX(SPRITE_MOVE_STEP_DECREMENT);
            this.checkBounds("L", rectCollision);
        }
    }

    public boolean checkForCol(Rectangle current) {
        if (current.isDisabled()) {
            return false;
        }
        return current.getBoundsInParent().intersects(this.getImageView().getBoundsInParent());
    }

    /**
     * Moves sprites right or left and sets current sprites
     * to the corresponding one (right or left)
     *
     * @param x - moved direction: right if is more than zero or left if is less
     */
    private void moveX(int x) {
        boolean right = x > 0;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                double rightBound = this.currentCanvas.getLayoutX() + this.currentCanvas.getWidth() -
                        (this.imageView.getX() + this.imageView.getFitWidth());
                this.imageView.setLayoutX(this.imageView.getLayoutX() + 1 > rightBound ?
                        rightBound : this.imageView.getLayoutX() + 1);
                this.imageView.setImage(this.RIGHT_IMAGE_VIEW);
            } else {
                double leftBound = this.currentCanvas.getLayoutX();
                this.imageView.setLayoutX(this.imageView.getLayoutX() - 1 < leftBound ?
                        leftBound : this.imageView.getLayoutX() - 1);
                this.imageView.setImage(this.LEFT_IMAGE_VIEW);
            }
        }
    }

    /**
     * Moves sprites up or down
     *
     * @param y - moved direction: down if is more than zero or up if is less
     */
    private void moveY(int y) {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) {
                double downBound = this.currentCanvas.getLayoutY() + this.currentCanvas.getHeight() -
                        (this.imageView.getY() + this.imageView.getFitHeight());
                this.imageView.setLayoutY(this.imageView.getLayoutY() + 1 > downBound ?
                        downBound : this.imageView.getLayoutY() + 1);
            } else {
                double upBound = this.currentCanvas.getLayoutY();
                this.imageView.setLayoutY(this.imageView.getLayoutY() - 1 < upBound ?
                        upBound : this.imageView.getLayoutY() - 1);
            }
        }
    }

    /**
     * Check if key was pressed
     *
     * @return whether key is pressed
     */
    private boolean isPressed(KeyCode key, HashMap<KeyCode, Boolean> keys) {
        return keys.getOrDefault(key, false);
    }

    /**
     * Check sprites position with level collision rectangles
     */
    private void checkBounds(String direction, ArrayList<Rectangle> rectCollision) {
        for (Rectangle rectangle : rectCollision) {
            if (this.checkForCol(rectangle)) {
                switch (direction) {
                    case "U":
                        this.getImageView().setLayoutY(this.getImageView().getLayoutY() + SPRITE_MOVE_STEP_INCREMENT);
                        break;
                    case "D":
                        this.getImageView().setLayoutY(this.getImageView().getLayoutY() + SPRITE_MOVE_STEP_DECREMENT);
                        break;
                    case "R":
                        this.getImageView().setLayoutX(this.getImageView().getLayoutX() + SPRITE_MOVE_STEP_DECREMENT);
                        break;
                    case "L":
                        this.getImageView().setLayoutX(this.getImageView().getLayoutX() + SPRITE_MOVE_STEP_INCREMENT);
                        break;
                }
            }
        }
    }
}
