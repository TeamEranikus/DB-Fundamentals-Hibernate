package escape.code.core;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TimeHandler implements EventHandler<ActionEvent> {

    public interface Listener {
        void onTimeChanged(long timeInSecs);
    }

    private long timeInSecs;
    private Listener listener;

    public TimeHandler(long timeInSecs, Listener listener) {
        this.timeInSecs = timeInSecs;
        this.listener = listener;
    }

    @Override
    public void handle(ActionEvent event) {
        this.listener.onTimeChanged(++this.timeInSecs);
    }
}
