package escape.code.models.sprite;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

public interface Sprite {

    void updateSpriteCoordinates(HashMap<KeyCode, Boolean> keys, ArrayList<Rectangle> rectCollision);

    boolean checkForCol(Rectangle current);

    ImageView getImageView();
}
