package spring.rest.shop.springrestshop.dto.task;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.comment.CommentDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.TaskState;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskDetailsDTO {
    private UserDTO creator;
    private List<UserDTO> executors;
    private List<CommentDTO> comments;
    private String title;
    private TaskState status;

    public TaskDetailsDTO(){

    }
    public TaskDetailsDTO(Task task){
        this.creator = new UserDTO(task.getCreator());
        this.executors = task.getExecutors().stream().map(UserDTO::new).collect(Collectors.toList());
        this.comments = task.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
        this.title = task.getTitle();
        this.status = task.getStatus();
    }

}
