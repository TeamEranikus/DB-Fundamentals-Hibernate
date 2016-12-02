package escape.code.enums;

import escape.code.utils.Constants;

import java.util.Arrays;

/**
 * Levels available in the game
 */
public enum Level {

    ZERO(0, Constants.DEMO_LEVEL_FXML_PATH),
    ONE(1, Constants.FIRST_LEVEL_FXML_PATH),
    TWO(2, Constants.SECOND_LEVEL_FXML_PATH);

    private int num;
    private String path;

    Level(int num, String constPath) {
        this.num = num;
        this.path = constPath;
    }

    /**
     * Gets current level id number
     * @return - id number
     */
    private int getNum() {
        return this.num;
    }

    /**
     * Gets current level fxml path
     * @return - corresponding fxml path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Gets corresponding level by given id number
     * @param num - level id number
     * @return - corresponding level
     */
    public static Level getByNum(int num){
      return Arrays.stream(Level.values())
              .filter(level -> level.getNum() == num)
              .findFirst()
              .orElse(null);
    }
}
