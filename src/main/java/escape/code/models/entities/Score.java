package escape.code.models.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Sets up score database
 */
@Entity
@Table(name = "scores")
public class Score implements Serializable {

    private Long id;
    private Long finishTime;
    private User user;

    public Score() {
        super();
    }

    @Id
    @GenericGenerator(name = "incrementer", strategy = "increment")
    @GeneratedValue(generator = "incrementer")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "finish_time")
    public Long getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
