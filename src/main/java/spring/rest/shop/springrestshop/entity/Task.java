package spring.rest.shop.springrestshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "task_executor",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "executor_id")
    )
    private List<User> executors = new ArrayList<>();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskState status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


}
