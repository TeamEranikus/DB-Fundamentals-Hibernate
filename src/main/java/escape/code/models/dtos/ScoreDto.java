package escape.code.models.dtos;

import java.io.Serializable;

/**
 * Created by Todor Ilchev on 2016-12-11.
 */
public class ScoreDto implements Serializable {

    private int postion;
    private String username;
    private String time;

    public ScoreDto() {
        super();
    }

    public ScoreDto(int postion, String username, String time) {
        this.setPostion(postion);
        this.setUsername(username);
        this.setTime(time);
    }

    public int getPostion() {
        return this.postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
