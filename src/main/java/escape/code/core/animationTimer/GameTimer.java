package escape.code.core.animationTimer;

import com.google.inject.Singleton;
import javafx.animation.AnimationTimer;

@Singleton
public class GameTimer extends AnimationTimer implements GameRender {

    private GameRender.Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void handle(long now) {
       this.listener.onHandle(now);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
