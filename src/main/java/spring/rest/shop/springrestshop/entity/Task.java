package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    public Task(){
    }

    public Task(String title,User creator,TaskState status){
        this.title = title;
        this.creator = creator;
        this.status = status;
    }
    public Task(User creator, TaskDTO taskDTO){
        this.title = taskDTO.getTitle();
        this.creator = creator;
        this.executor = executor;
        this.status = taskDTO.getStatus();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @Column(name = "status")
    private TaskState status;
}
