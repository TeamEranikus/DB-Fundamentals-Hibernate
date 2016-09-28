package escape.code.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Resize the game canvas
 */
public class ResizableCanvas extends Canvas {

    /**
     * Adds width and height property event listeners
     */
    public ResizableCanvas() {
        this.widthProperty().addListener(evt -> draw());
        this.heightProperty().addListener(evt -> draw());
    }

    /**
     * Redraw the canvas when width or height property get changed
     */
    private void draw() {
        double width = this.getWidth();
        double height = this.getHeight();
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, width, height);
    }

    /**
     * Check if the canvas can be resized
     * @return if the canvas is resizable
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**
     * Gets canvas current width
     * @return canvas current width
     */
    @Override
    public double prefWidth(double height) {
        return this.getWidth();
    }

    /**
     * Gets canvas current height
     * @return canvas current height
     */
    @Override
    public double prefHeight(double width) {
        return this.getHeight();
    }
}

