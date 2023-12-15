package spring.rest.shop.springrestshop.dto.task;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.comment.CommentDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.TaskState;

import java.util.List;

@Data
public class TaskDTO {

    private long id;
    private String title;
    private TaskState status;

    public TaskDTO(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.status = task.getStatus();
    }

    public TaskDTO(){

    }

}
