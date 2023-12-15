package spring.rest.shop.springrestshop.dto.user;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.comment.CommentDTO;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailsDTO {
    public UserDetailsDTO(User user){
        this.id = user.getId();
        this.email= user.getEmail();
        this.createdTasks = user.getCreatedTasks().stream().map(TaskDTO::new).collect(Collectors.toList());
        this.executedTasks = user.getExecutedTasks().stream().map(TaskDTO::new).collect(Collectors.toList());
        this.createdComments = user.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
    }



    private long id;
    private String email;
    private List<TaskDTO> createdTasks;
    private List<TaskDTO> executedTasks;
    private List<CommentDTO> createdComments;



}
