package escape.code.core.animationTimer;

public interface GameRender {

    interface Listener{
       void onHandle(long now);
    }

    void handle(long now);

    void start();

    void stop();

    void setListener(Listener listener);
}
