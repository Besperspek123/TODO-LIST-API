package spring.rest.shop.springrestshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import spring.rest.shop.springrestshop.aspect.SecurityContext;
import spring.rest.shop.springrestshop.dto.task.TaskDTO;
import spring.rest.shop.springrestshop.dto.user.UserDTO;
import spring.rest.shop.springrestshop.entity.Task;
import spring.rest.shop.springrestshop.entity.TaskState;
import spring.rest.shop.springrestshop.entity.User;
import spring.rest.shop.springrestshop.exception.EmptyFieldException;
import spring.rest.shop.springrestshop.exception.EntityNotFoundException;
import spring.rest.shop.springrestshop.exception.UserAlreadyHasThisTaskInHisTaskListException;
import spring.rest.shop.springrestshop.exception.UserNotFoundException;
import spring.rest.shop.springrestshop.repository.TaskRepository;
import spring.rest.shop.springrestshop.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class TaskService {


    private final TaskRepository taskRepository;
    private final UserService userService;
    @Autowired
    public TaskService(TaskRepository taskRepository,UserService userService){
        this.taskRepository = taskRepository;
        this.userService = userService;
    }
    public void saveTask(TaskDTO taskDTO){
        if (taskDTO.getTitle().isEmpty() || taskDTO.getTitle() == null){
            throw new EmptyFieldException("Title cant be empty or null");
        }
        User currentUser = SecurityContext.getCurrentUser();
        Task taskForSave = new Task(currentUser,taskDTO);
        taskForSave.setStatus(TaskState.WAITING);
        taskRepository.save(taskForSave);


    }

    public void chooseExecutor(long taskId, UserDTO executorDTO){
        User currentUser = SecurityContext.getCurrentUser();
        Task task = taskRepository.findById(taskId);

        if(task == null){
            throw new EntityNotFoundException("Task with this id not found");
        }
        if(task.getCreator() != currentUser){
            throw new AccessDeniedException("You can choose executor into not your task");
        }
        User executor = new User();
        if(executorDTO == null){
            throw new EmptyFieldException("Executor cant be empty");
        }
        if(executorDTO.getEmail() == null){
            executor = userService.getUserById(executorDTO.getId());
        }
        else executor = userService.getUserByEmail(executorDTO.getEmail());
        if(task.getExecutors().contains(executor)){
            throw new UserAlreadyHasThisTaskInHisTaskListException("This executor already exist in this task");
        }
        if(executor == null){
            throw new UserNotFoundException("User with these email or id not found");
        }
        task.getExecutors().add(executor);
        taskRepository.save(task);
    }

    public void editTask(long taskId,TaskDTO taskDTO){
        Task task = taskRepository.findById(taskId);
        User currentUser = SecurityContext.getCurrentUser();
        if (task == null){
            throw new EntityNotFoundException("task not found");
        }
        if(task.getCreator() !=currentUser){
            throw new AccessDeniedException("You cant edit not your task");
        }
        if(taskDTO == null){
            throw new EmptyFieldException("task cant be empty");

        }
        if(taskDTO.getTitle() != null){
            task.setTitle(taskDTO.getTitle());
        }
        taskRepository.save(task);

    }
//    public List<Task> getTaskByUser (){
//
//    }
}
