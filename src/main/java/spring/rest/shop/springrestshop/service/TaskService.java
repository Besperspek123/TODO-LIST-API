package spring.rest.shop.springrestshop.service;

import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.User;

import java.util.List;

@Service
public class TaskService {
    public void saveTask(TaskDTO taskDTO){
        User currentUser = SecurityContext.getCurrentUser();
        Task taskForSave = new Task(currentUser,taskDTO);

    }

    public void editTask(){

    }
//    public List<Task> getTaskByUser (){
//
//    }
}
