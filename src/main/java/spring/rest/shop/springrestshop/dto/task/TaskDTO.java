package spring.rest.shop.springrestshop.dto.task;

import lombok.Data;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.TaskState;

@Data
public class TaskDTO {
    private String title;
    private TaskState status;

}
