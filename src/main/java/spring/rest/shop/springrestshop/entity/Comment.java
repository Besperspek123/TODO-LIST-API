package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "creator_id") // Ссылка на столбец в таблице комментариев, который содержит ID пользователя
    private User creator;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
