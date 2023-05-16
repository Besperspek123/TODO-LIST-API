package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;

@Entity
@Data
@Table(name = "notification")
public class Notification {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @JsonIgnore
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "message")
    private String message;

    @JsonIgnore
    @Column(name = "isRead")
    boolean isRead;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User recipientUser;

}
